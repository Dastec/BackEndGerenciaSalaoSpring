package br.com.dastec.gerenciasalao.events.listeners.payment

import br.com.dastec.gerenciasalao.events.PaymentEventUpdatePendency
import br.com.dastec.gerenciasalao.models.enums.PendencyStatus
import br.com.dastec.gerenciasalao.services.CustomerServiceModelService
import br.com.dastec.gerenciasalao.services.PendencyService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UpdatePendencyInTheCustomerServiceByPaymentListener(
    val pendencyService: PendencyService,
    val customerServiceModelService: CustomerServiceModelService
    ) {

    @EventListener
    fun listener(paymentEvent: PaymentEventUpdatePendency){
        val customerService = paymentEvent.paymentModel.customerService.copy(
            paidValue = paymentEvent.paymentModel.customerService.paidValue!! + paymentEvent.paymentModel.valuePayment
            )
        customerServiceModelService.finalizeCustomerServicePendencyStatus(customerService)

        val pendency = pendencyService.findByCustomerService(paymentEvent.paymentModel.customerService)
        pendency.valuePendency = pendency.valuePendency - paymentEvent.paymentModel.valuePayment
        pendencyService.updatePendency(pendency)
    }
}