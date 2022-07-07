package br.com.dastec.gerenciasalao.controllers.responses

import com.fasterxml.jackson.annotation.JsonAlias

data class LoginResponse(

    @JsonAlias("access_token")
    val accessToken: String,

    @JsonAlias("expires_in")
    val expiresIn: String,

    @JsonAlias("due_date_time")
    val dueDateTime: String,

    @JsonAlias("token_type")
    val tokenType: String
)