package br.com.dastec.gerenciasalao.controllers.requests

import com.fasterxml.jackson.annotation.JsonAlias

data class PostCreateSaleServiceRequest(

    @JsonAlias("customer_service_id")
    val customerService: Long,

    @JsonAlias("service_id")
    val service: Long,

    val price: Double
)