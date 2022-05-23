package br.com.dastec.gerenciasalao.controllers.requests.customerservice

import com.fasterxml.jackson.annotation.JsonAlias

data class PutUpdateCustomerServiceRequest(

    val customer: Long,

    @JsonAlias("customer_service")
    val customerService: Long,

    )
