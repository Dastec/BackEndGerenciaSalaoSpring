package br.com.dastec.gerenciasalao.controllers.requests.formofpayment

import com.fasterxml.jackson.annotation.JsonAlias

data class PutFormPaymentRequest(
    @JsonAlias("name_form_payment")
    var nameFormPayment: String
)