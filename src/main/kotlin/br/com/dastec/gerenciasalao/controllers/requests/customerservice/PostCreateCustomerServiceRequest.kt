package br.com.dastec.gerenciasalao.controllers.requests.customerservice

import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.models.ServiceModel
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PostCreateCustomerServiceRequest(

    @field:NotNull(message ="O campo id do cliente não pode ser null!")
    var customer: Long,
)
