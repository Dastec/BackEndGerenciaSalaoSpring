package br.com.dastec.gerenciasalao.controllers.requests.user

import com.fasterxml.jackson.annotation.JsonAlias

data class UpdatePasswordRequest(

    val password: String,

    @JsonAlias("password_confirmation")
    val passwordConfirmation: String,

)


