package br.com.dastec.gerenciasalao.controllers.responses

import com.fasterxml.jackson.annotation.JsonAlias
import jdk.jfr.Category

data class FormOfPaymentResponse(
    @JsonAlias("id_form_payment")
    val idFormPayment: Long,

    @JsonAlias("name_form_payment")
    val nameFormPayment: String
)
