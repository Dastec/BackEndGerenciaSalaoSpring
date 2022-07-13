package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.mapper.UserMapper
import br.com.dastec.gerenciasalao.controllers.requests.user.CreateUserRequest
import br.com.dastec.gerenciasalao.controllers.requests.user.UpdateInformationUserRequest
import br.com.dastec.gerenciasalao.controllers.requests.user.UpdatePasswordRequest
import br.com.dastec.gerenciasalao.controllers.responses.MessageResponse
import br.com.dastec.gerenciasalao.exceptions.BadRequestException
import br.com.dastec.gerenciasalao.exceptions.PasswordInvalidException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.enums.Role
import br.com.dastec.gerenciasalao.models.enums.UserStatus
import br.com.dastec.gerenciasalao.security.JwtUtil
import br.com.dastec.gerenciasalao.services.SalonService
import br.com.dastec.gerenciasalao.services.UserService
import br.com.dastec.gerenciasalao.utils.SpringUtil
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService,
    private val userMapper: UserMapper,
    private val salonService: SalonService,
    private val springUtil: SpringUtil,
    private val jwtUtil: JwtUtil
) {

    @PostMapping("/create/useradmin")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUserAdmin(@RequestBody createUserRequest: CreateUserRequest):MessageResponse{
        if (createUserRequest.password != createUserRequest.passwordConfirmation){
            throw PasswordInvalidException(Errors.GS1001.message, Errors.GS1001.internalCode)
        }
        val person = salonService.findById(createUserRequest.idSalon)
        userService.createUserAdmin(userMapper.toUserModel(createUserRequest, person))

        return MessageResponse(message = "Usuário administrador ${createUserRequest.userName} criado com sucesso!")
    }

    @PostMapping("/create/user")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody createUserRequest: CreateUserRequest): MessageResponse {
        if (createUserRequest.password != createUserRequest.passwordConfirmation){
            throw PasswordInvalidException(Errors.GS1001.message, Errors.GS1001.internalCode)
        }
        val person = salonService.findById(createUserRequest.idSalon)

        userService.createUser(userMapper.toUserModel(createUserRequest, person))

        return MessageResponse(message = "Usuário criado ${createUserRequest.userName} com sucesso!")
    }

    @PutMapping("/update/information/{id}")
    fun updateInformation(@PathVariable id: Long, updateInformationUserRequest: UpdateInformationUserRequest, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val user = userService.findById(salon, id)
        userService.updateInformation(userMapper.UpdateInformationToUserModel(updateInformationUserRequest, user))

        return MessageResponse(message = "Usuário ${user.userName} atualizado com sucesso!")
    }

    @PutMapping("/update/password/{id}")
    fun updatePassword(@PathVariable id: Long, updatePasswordRequest: UpdatePasswordRequest, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        if (updatePasswordRequest.password != updatePasswordRequest.passwordConfirmation){
            throw PasswordInvalidException(Errors.GS1001.message, Errors.GS1001.internalCode)
        }
        val salon = springUtil.getSalon(token.split(" ")[1])
        val user = userService.findById(salon, id)
        userService.updatePassword(userMapper.UpdatePasswordToUserModel(updatePasswordRequest, user))
        return MessageResponse(message = "Senha atualizada com sucesso!")
    }

    @PutMapping("/admin/suspend/{id}")
    fun suspendUser(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val userToken = jwtUtil.getUserInformation(token.split(" ")[1])
        val user = userService.findById(salon, id)

        if (userToken.idUser == id){
            throw BadRequestException(Errors.GSL007.message.format(user.idUser), Errors.GSL007.internalCode)
        }

        when(user.status){
            UserStatus.SUSPENDED -> throw BadRequestException(Errors.GSL004.message.format(user.idUser), Errors.GSL004.internalCode)
            UserStatus.EXCLUDED -> throw BadRequestException(Errors.GSL005.message.format(user.idUser), Errors.GSL005.internalCode)
        }

        userService.suspendUser(userMapper.suspendUserToUserModel(user))
        return MessageResponse(message = "Usuário ${user.userName} suspenso com sucesso!")
    }

    @DeleteMapping("/admin/{id}")
    fun deleteUser(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val userToken = jwtUtil.getUserInformation(token.split(" ")[1])
        val user = userService.findById(salon, id)

        if (userToken.idUser == id){
            throw BadRequestException(Errors.GSL007.message.format(user.idUser), Errors.GSL007.internalCode)
        }

        if(user.status == UserStatus.EXCLUDED){
             throw BadRequestException(Errors.GSL005.message.format(user.idUser), Errors.GSL005.internalCode)
        }
        userService.deleteUser(userMapper.deleteUserToUserModel(user))
        return MessageResponse(message = "Usuário ${user.userName} excluído com sucesso!")
    }

    @PutMapping("/admin/activate/{id}")
    fun activateUser(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val userToken = jwtUtil.getUserInformation(token.split(" ")[1])
        val user = userService.findById(salon, id)

        if (userToken.idUser == id){
            throw BadRequestException(Errors.GSL011.message.format(user.idUser), Errors.GSL011.internalCode)
        }

        if (user.status == UserStatus.ACTIVE) {
            throw BadRequestException(Errors.GSL006.message.format(user.idUser), Errors.GSL006.internalCode)
        }
        userService.activateUser(userMapper.activateUserToUserModel(user))
        return MessageResponse(message = "Usuário ${user.userName} ativado com sucesso!")
    }

    @PutMapping("/admin/addadmin/{id}")
    fun addAdmin(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val userToken = jwtUtil.getUserInformation(token.split(" ")[1])
        val user = userService.findById(salon, id)

        if (userToken.idUser == id){
            throw BadRequestException(Errors.GSL012.message.format(user.idUser), Errors.GSL012.internalCode)
        }

        if (user.roles.contains(Role.ADMIN)){
            throw BadRequestException(Errors.GSL008.message.format(user.idUser), Errors.GSL008.internalCode)
        }

        userService.addAdmin(user)
        return MessageResponse(message = "Usuário ${user.userName} agora é um administrador!")
    }

    @PutMapping("/admin/removeadmin/{id}")
    fun removeAdmin(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val userToken = jwtUtil.getUserInformation(token.split(" ")[1])
        val user = userService.findById(salon, id)

        if (userToken.idUser == id){
            throw BadRequestException(Errors.GSL010.message.format(user.idUser), Errors.GSL010.internalCode)
        }

        if (!user.roles.contains(Role.ADMIN)){
            throw BadRequestException(Errors.GSL009.message.format(user.idUser), Errors.GSL009.internalCode)
        }
        userService.removeAdmin(user)
        return MessageResponse(message = "Usuário ${user.userName} agora não é mais um administrador!")
    }
}