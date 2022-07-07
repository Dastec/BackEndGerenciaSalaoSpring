package br.com.dastec.gerenciasalao.controllers.requests.customerservice

import javax.validation.constraints.NotNull

data class PostCreateCustomerServiceRequest(

    @field:NotNull(message ="O campo id do cliente não pode ser null!")
    var customer: Long,
)
