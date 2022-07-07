package br.com.dastec.gerenciasalao.controllers.requests.user

import com.fasterxml.jackson.annotation.JsonAlias

data class CreateUserRequest(

    @JsonAlias("user_name")
    val userName: String,

    val password: String,

    @JsonAlias("password_confirmation")
    val passwordConfirmation: String,

    val email: String,

    @JsonAlias("phone_number")
    val phoneNumber: String,

    @JsonAlias("id_salon")
    val idSalon: Long
)


