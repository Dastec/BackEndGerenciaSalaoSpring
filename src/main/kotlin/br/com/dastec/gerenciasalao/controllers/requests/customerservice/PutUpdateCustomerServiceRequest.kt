package br.com.dastec.gerenciasalao.controllers.requests.customerservice

data class PutUpdateCustomerServiceRequest(

    var customer: Long,

    var services: List<Long>,

    var observation: String?
)
