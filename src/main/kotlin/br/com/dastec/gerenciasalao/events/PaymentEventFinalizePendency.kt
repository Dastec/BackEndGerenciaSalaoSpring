package br.com.dastec.gerenciasalao.events

import br.com.dastec.gerenciasalao.models.PaymentModel
import org.springframework.context.ApplicationEvent

class PaymentEventFinalizePendency(
    source: Any,
    val paymentModel: PaymentModel
): ApplicationEvent(source) {
}