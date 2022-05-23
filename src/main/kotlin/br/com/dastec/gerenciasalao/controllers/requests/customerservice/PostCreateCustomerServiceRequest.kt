package br.com.dastec.gerenciasalao.controllers.requests.customerservice

import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.models.ServiceModel

data class PostCreateCustomerServiceRequest(
    var customer: Long,
)
