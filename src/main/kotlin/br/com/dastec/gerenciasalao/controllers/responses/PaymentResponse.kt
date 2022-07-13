package br.com.dastec.gerenciasalao.controllers.responses

import br.com.dastec.gerenciasalao.models.FormOfPaymentModel
import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import java.time.LocalDate

data class PaymentResponse(
    val idPayment: Long? = null,

    @JsonAlias("form_payment_id")
    val formOfPayment: FormOfPaymentResponse,

    @JsonAlias("value_payment")
    val valuePayment: BigDecimal = BigDecimal.ZERO,


    @JsonAlias("date_payment")
    val datePayment: LocalDate,
)
