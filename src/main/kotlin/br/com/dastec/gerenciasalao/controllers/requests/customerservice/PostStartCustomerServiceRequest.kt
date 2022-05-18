package br.com.dastec.gerenciasalao.controllers.requests.customerservice

import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.models.ServiceModel

data class PostStartCustomerServiceRequest(

    var customer: Long,

    val services: Set<Long>,

    val observation: String?
)
