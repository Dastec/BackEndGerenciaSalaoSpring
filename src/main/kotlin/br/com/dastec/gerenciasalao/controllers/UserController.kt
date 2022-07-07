package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.mapper.UserMapper
import br.com.dastec.gerenciasalao.controllers.requests.user.CreateUserRequest
import br.com.dastec.gerenciasalao.exceptions.PasswordInvalidException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.services.SalonService
import br.com.dastec.gerenciasalao.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService,
    private val userMapper: UserMapper,
    private val salonService: SalonService,
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody createUserRequest: CreateUserRequest){
        if (createUserRequest.password != createUserRequest.passwordConfirmation){
            throw PasswordInvalidException(Errors.GS1001.message, Errors.GS1001.internalCode)
        }
        val person = salonService.findById(createUserRequest.idSalon)

        userService.createUser(userMapper.toUserModel(createUserRequest, person))
    }
}