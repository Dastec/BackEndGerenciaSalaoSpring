package br.com.dastec.gerenciasalao.controllers.requests.salon

import com.fasterxml.jackson.annotation.JsonAlias

data class RegisterSalonRequest(

    var name: String,

    @JsonAlias("fiscal_identification")
    var fiscalIdentification: String,
)
