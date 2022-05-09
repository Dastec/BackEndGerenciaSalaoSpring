package br.com.dastec.gerenciasalao.controllers.requests.customerservice

data class PutUpdateCustomerServiceRequest(

    var customer: Long,

    var services: MutableList<Long>,

    var observation: String?
)
