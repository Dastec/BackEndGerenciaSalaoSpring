package br.com.dastec.gerenciasalao.controllers.requests.payments

import com.fasterxml.jackson.annotation.JsonAlias


data class PostPaymentServiceWithPendencyRequest(

    @JsonAlias("payments")
    var paymentObject: List<paymentObject>,

    @JsonAlias("customer_service")
    var customerServices: Set<Long>,
)

data class paymentObject(
    @JsonAlias("form_payment")
    var formOfPayment: Long,

    @JsonAlias("value_payment")
    var valuePayment: Double,
)
