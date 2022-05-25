package br.com.dastec.gerenciasalao.controllers.requests.customerservice

import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.models.ServiceModel
import com.fasterxml.jackson.annotation.JsonAlias
import javax.validation.constraints.NotNull

data class PostStartCustomerServiceRequest(

    @JsonAlias("customer_service")
    @field:NotNull(message ="O campo id do cliente não pode ser null!")
    val customerService: Long,

)
