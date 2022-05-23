package br.com.dastec.gerenciasalao.controllers.requests.customerservice

import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.models.ServiceModel
import com.fasterxml.jackson.annotation.JsonAlias

data class PostStartCustomerServiceRequest(

    @JsonAlias("customer_service")
    val customerService: Long,

)
