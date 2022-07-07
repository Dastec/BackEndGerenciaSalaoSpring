package br.com.dastec.gerenciasalao.controllers.mapper

import br.com.dastec.gerenciasalao.controllers.requests.user.CreateUserRequest
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.models.UserModel
import br.com.dastec.gerenciasalao.services.UserService
import org.springframework.stereotype.Component

@Component
class UserMapper(userService: UserService) {

    fun toUserModel(createUserRequest: CreateUserRequest, person: BeautySalonModel): UserModel {
        return UserModel(
            userName = createUserRequest.userName,
            password = createUserRequest.password,
            email = createUserRequest.email.lowercase(),
            phoneNumber = createUserRequest.phoneNumber,
            beautySalon = person,
        )
    }
}
