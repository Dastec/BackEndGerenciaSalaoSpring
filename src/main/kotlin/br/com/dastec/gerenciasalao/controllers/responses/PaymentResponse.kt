package br.com.dastec.gerenciasalao.controllers.responses

import br.com.dastec.gerenciasalao.models.FormOfPaymentModel
import com.fasterxml.jackson.annotation.JsonAlias
import java.time.LocalDate

data class PaymentResponse(
    var idPayment: Long? = null,

    @JsonAlias("form_payment_id")
    var formOfPayment: FormOfPaymentModel,

    @JsonAlias("value_payment")
    var valuePayment: Double = 0.0,


    @JsonAlias("date_payment")
    var datePayment: LocalDate,
)
