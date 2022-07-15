package br.com.dastec.gerenciasalao.controllers.requests.payments

import br.com.dastec.gerenciasalao.validation.annotation.IsCurrency
import com.fasterxml.jackson.annotation.JsonAlias
import javax.validation.constraints.NotBlank
import java.math.BigDecimal



data class PutPendecyServiceRequest(

    @JsonAlias("form_payment")
    @field:NotBlank(message ="O id da forma de pagamento não pode ser null!")
    var formOfPayment: Long,


    @JsonAlias("value_payment")
    @IsCurrency
    var valuePayment: BigDecimal,
)
