package br.com.dastec.gerenciasalao.controllers.requests.payments

import br.com.dastec.gerenciasalao.validation.annotation.IsCurrency
import com.fasterxml.jackson.annotation.JsonAlias
import javax.validation.constraints.NotNull


data class PostPaymentServiceRequest(

    @JsonAlias("form_payment")
    @field:NotNull(message ="O campo id da forma de pagamento não pode ser null!")
    var formOfPayment: Long,

    @JsonAlias("customer_service")
    @field:NotNull(message ="O campo id do atendimento não pode ser null!")
    var customerService: Long,

    @JsonAlias("value_payment")
    @IsCurrency
    var valuePayment: Double,
)
