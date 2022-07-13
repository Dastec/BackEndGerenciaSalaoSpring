package br.com.dastec.gerenciasalao.controllers.mapper

import br.com.dastec.gerenciasalao.controllers.requests.user.CreateUserRequest
import br.com.dastec.gerenciasalao.controllers.requests.user.UpdateInformationUserRequest
import br.com.dastec.gerenciasalao.controllers.requests.user.UpdatePasswordRequest
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.models.UserModel
import br.com.dastec.gerenciasalao.models.enums.UserStatus
import br.com.dastec.gerenciasalao.services.UserService
import org.springframework.stereotype.Component

@Component
class UserMapper(private val userService: UserService) {

    fun toUserModel(createUserRequest: CreateUserRequest, salon: BeautySalonModel): UserModel {
        return UserModel(
            userName = createUserRequest.userName,
            password = createUserRequest.password,
            email = createUserRequest.email.lowercase(),
            phoneNumber = createUserRequest.phoneNumber,
            beautySalon = salon,
        )
    }

    fun UpdateInformationToUserModel(updateInformationUserRequest: UpdateInformationUserRequest, user: UserModel): UserModel {

        return UserModel(
            idUser = user.idUser,
            userName = user.userName,
            password = user.password,
            email = updateInformationUserRequest.email.lowercase(),
            phoneNumber = updateInformationUserRequest.phoneNumber,
            beautySalon = user.beautySalon,
            roles = user.roles
        )
    }

    fun UpdatePasswordToUserModel(updatePasswordRequest: UpdatePasswordRequest, user: UserModel): UserModel {
        return UserModel(
            idUser = user.idUser,
            userName = user.userName,
            password = updatePasswordRequest.password,
            email = user.email,
            phoneNumber = user.phoneNumber,
            beautySalon = user.beautySalon,
            roles = user.roles
        )
    }

    fun suspendUserToUserModel(user: UserModel): UserModel{
        return UserModel(
            idUser = user.idUser,
            userName = user.userName,
            password = user.password,
            email = user.email,
            phoneNumber = user.phoneNumber,
            beautySalon = user.beautySalon,
            status = UserStatus.SUSPENDED,
            roles = user.roles
        )
    }

    fun deleteUserToUserModel(user: UserModel): UserModel{
        return UserModel(
            idUser = user.idUser,
            userName = user.userName,
            password = user.password,
            email = user.email,
            phoneNumber = user.phoneNumber,
            beautySalon = user.beautySalon,
            status = UserStatus.EXCLUDED,
            roles = user.roles
        )
    }

    fun activateUserToUserModel(user: UserModel): UserModel{
        return UserModel(
            idUser = user.idUser,
            userName = user.userName,
            password = user.password,
            email = user.email,
            phoneNumber = user.phoneNumber,
            beautySalon = user.beautySalon,
            status = UserStatus.ACTIVE,
            roles = user.roles
        )
    }
}
