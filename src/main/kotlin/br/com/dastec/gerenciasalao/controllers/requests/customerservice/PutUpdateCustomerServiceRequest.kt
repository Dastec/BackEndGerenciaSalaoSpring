package br.com.dastec.gerenciasalao.controllers.requests.customerservice

data class PutUpdateCustomerServiceRequest(

    var customer: Long,

    var services: Set<Long>,

    var observation: String?
)
