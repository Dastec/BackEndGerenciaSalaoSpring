package br.com.dastec.gerenciasalao.events.listeners.payment

import br.com.dastec.gerenciasalao.events.PaymentEventFinalizePendency
import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import br.com.dastec.gerenciasalao.models.enums.PendencyStatus
import br.com.dastec.gerenciasalao.services.CustomerServiceModelService
import br.com.dastec.gerenciasalao.services.PendencyService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class FinalizePendencyInTheCustomerServiceByPaymentListener(
    val customerServiceModelService: CustomerServiceModelService,
    val pendencyService: PendencyService
    ) {

    @EventListener
    fun listener(paymentEvent: PaymentEventFinalizePendency){
        val customerService = paymentEvent.paymentModel.customerService.copy(
            paidValue = paymentEvent.paymentModel.customerService.paidValue!! + paymentEvent.paymentModel.valuePayment,
            statusCustomerService = CustomerServiceStatus.FINALIZADO
            )
        customerServiceModelService.finalizeCustomerServicePendencyStatus(customerService)

        val pendency = pendencyService.findByCustomerService(paymentEvent.paymentModel.customerService)
        pendency.valuePendency = pendency.valuePendency - paymentEvent.paymentModel.valuePayment
        pendency.status = PendencyStatus.PAGO
        pendencyService.finalizePendency(pendency)
    }
}