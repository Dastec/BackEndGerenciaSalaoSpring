package br.com.dastec.gerenciasalao.controllers.requests.payments

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal


data class PostPaymentServiceRequest(

    @JsonAlias("form_payment")
    var formOfPayment: Long,

    @JsonAlias("customer_service")
    var customerService: Long,

    @JsonAlias("value_payment")
    var valuePayment: Double,
)
