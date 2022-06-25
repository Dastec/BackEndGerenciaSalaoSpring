package br.com.dastec.gerenciasalao.controllers.requests.person

import com.fasterxml.jackson.annotation.JsonAlias

data class RegisterPersonRequest(

    var name: String,

    @JsonAlias("fiscal_identification")
    var fiscalIdentification: String,
)
