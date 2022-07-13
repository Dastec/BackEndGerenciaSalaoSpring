package br.com.dastec.gerenciasalao.controllers.responses

import com.fasterxml.jackson.annotation.JsonAlias
import jdk.jfr.Category

data class ServiceResponse(
    @JsonAlias("id_service")
    val idService: Long,

    @JsonAlias("name_service")
    val nameService: String
)
