package br.com.dastec.gerenciasalao.controllers.mapper

import br.com.dastec.gerenciasalao.controllers.requests.payments.PostPaymentServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.payments.PutPaymentServiceRequest
import br.com.dastec.gerenciasalao.exceptions.CustomerServiceHasNoPendingException
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.FormOfPaymentModel
import br.com.dastec.gerenciasalao.models.PaymentModel
import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import br.com.dastec.gerenciasalao.services.CustomerServiceModelService
import br.com.dastec.gerenciasalao.services.FormOfPaymentService
import br.com.dastec.gerenciasalao.services.PaymentService
import org.springframework.stereotype.Component

@Component
class PaymentMapper(
    private val paymentService: PaymentService,
    private val customerServiceModelService: CustomerServiceModelService,
    private val formOfPaymentService: FormOfPaymentService
) {

    fun postPaymentServiceRequestToPaymentModel(postPaymentServiceRequest: PostPaymentServiceRequest): PaymentModel {
        val customerService = customerServiceModelService.findById(postPaymentServiceRequest.customerService)
        val formOfPayment = formOfPaymentService.findById(postPaymentServiceRequest.formOfPayment)
        return PaymentModel(
            formOfPayment = formOfPayment,
            customerService = customerService,
            valuePayment = postPaymentServiceRequest.valuePayment
        )
    }

    fun postPaymentPendencyServiceRequestToPaymentModel(postPaymentServiceRequest: PostPaymentServiceRequest): PaymentModel {
        val customerService = customerServiceModelService.findById(postPaymentServiceRequest.customerService)

        val formOfPayment = formOfPaymentService.findById(postPaymentServiceRequest.formOfPayment)
        return PaymentModel(
            formOfPayment = formOfPayment,
            customerService = customerService,
            valuePayment = postPaymentServiceRequest.valuePayment
        )
    }

    fun putPaymentServiceRequestToPaymentModel(putPaymentServiceRequest: PutPaymentServiceRequest, previouPayment: PaymentModel): PaymentModel {
        val formOfPayment = formOfPaymentService.findById(putPaymentServiceRequest.formOfPayment)

        return PaymentModel(
            idPayment = previouPayment.idPayment,
            formOfPayment = formOfPayment,
            customerService = previouPayment.customerService,
            valuePayment = putPaymentServiceRequest.valuePayment,
            datePayment =  previouPayment.datePayment
        )
    }
}