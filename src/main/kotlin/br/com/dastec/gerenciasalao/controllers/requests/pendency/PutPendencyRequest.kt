package br.com.dastec.gerenciasalao.controllers.requests.pendency

import com.fasterxml.jackson.annotation.JsonAlias

data class PutPendencyRequest(

    @JsonAlias("value_pendency")
    var valuePendency: Double,
)
