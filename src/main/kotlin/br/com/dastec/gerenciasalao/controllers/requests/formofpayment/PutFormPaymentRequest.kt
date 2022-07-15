package br.com.dastec.gerenciasalao.controllers.requests.formofpayment

import com.fasterxml.jackson.annotation.JsonAlias
import javax.validation.constraints.NotBlank


data class PutFormPaymentRequest(
    @JsonAlias("name_form_payment")
    @field:NotBlank(message ="o campo forma de pagamento não pode ser vazio")
    var nameFormPayment: String
)