package br.com.dastec.gerenciasalao.controllers.requests.security

import com.fasterxml.jackson.annotation.JsonAlias

data class LoginRequest(
    @JsonAlias("user_name")
    val userName: String,
    val password: String
)