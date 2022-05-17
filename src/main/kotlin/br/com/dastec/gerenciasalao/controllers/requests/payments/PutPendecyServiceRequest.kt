package br.com.dastec.gerenciasalao.controllers.requests.payments

import com.fasterxml.jackson.annotation.JsonAlias


data class PutPendecyServiceRequest(

    @JsonAlias("form_payment")
    var formOfPayment: Long,

    @JsonAlias("value_payment")
    var valuePayment: Double,
)
