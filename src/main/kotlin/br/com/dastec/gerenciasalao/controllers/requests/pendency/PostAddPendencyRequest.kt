package br.com.dastec.gerenciasalao.controllers.requests.pendency

import com.fasterxml.jackson.annotation.JsonAlias

data class PostAddPendencyRequest(
    @JsonAlias("customer_service_id")
    var customerService: Long,

    @JsonAlias("value_pendency")
    var valuePendency: Double
)
