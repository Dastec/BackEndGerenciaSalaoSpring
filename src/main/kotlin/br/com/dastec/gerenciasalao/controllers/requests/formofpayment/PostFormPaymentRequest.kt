package br.com.dastec.gerenciasalao.controllers.requests.formofpayment

import com.fasterxml.jackson.annotation.JsonAlias
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class PostFormPaymentRequest(
    @JsonAlias("name_form_payment")
    @field:NotBlank(message ="o campo forma de pagamento não pode ser vazio")
    var nameFormPayment: String
)