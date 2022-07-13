package br.com.dastec.gerenciasalao.controllers.requests.user

import com.fasterxml.jackson.annotation.JsonAlias

data class UpdateInformationUserRequest(

    val email: String,

    @JsonAlias("phone_number")
    val phoneNumber: String,

)


