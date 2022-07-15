package br.com.dastec.gerenciasalao.controllers.requests.payments

import br.com.dastec.gerenciasalao.validation.annotation.IsCurrency
import com.fasterxml.jackson.annotation.JsonAlias
import javax.validation.constraints.NotNull
import java.math.BigDecimal



data class PostPaymentServiceWithPendencyRequest(

    @JsonAlias("payments")
    @field:NotNull(message ="O id da forma de pagamento não pode ser null!")
    var paymentObject: List<paymentObject>,

    @JsonAlias("customer_service")
    @field:NotNull(message = "É necessário colocar no mínimo um atendimento!")
    var customerServices: Set<Long>,
)

data class paymentObject(
    @JsonAlias("form_payment")
    @field:NotNull(message ="O id da forma de pagamento não pode ser null!")
    var formOfPayment: Long,

    @JsonAlias("value_payment")
    @IsCurrency
    var valuePayment: BigDecimal,
)
