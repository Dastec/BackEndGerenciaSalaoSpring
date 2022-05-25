package br.com.dastec.gerenciasalao.controllers.mapper

import br.com.dastec.gerenciasalao.controllers.requests.payments.PostPaymentServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.payments.PostPaymentServiceWithPendencyRequest
import br.com.dastec.gerenciasalao.controllers.requests.payments.PutPendecyServiceRequest
import br.com.dastec.gerenciasalao.controllers.responses.PaymentResponse
import br.com.dastec.gerenciasalao.exceptions.BadRequestException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.PaymentModel
import br.com.dastec.gerenciasalao.services.CustomerServiceModelService
import br.com.dastec.gerenciasalao.services.FormOfPaymentService
import br.com.dastec.gerenciasalao.services.PaymentService
import br.com.dastec.gerenciasalao.services.PendencyService
import org.springframework.stereotype.Component

@Component
class PaymentMapper(
    private val paymentService: PaymentService,
    private val customerServiceModelService: CustomerServiceModelService,
    private val formOfPaymentService: FormOfPaymentService,
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

    fun putPaymentServiceRequestToPaymentModel(
        putPaymentServiceRequest: PutPendecyServiceRequest,
        previousPayment: PaymentModel
    ): PaymentModel {
        val formOfPayment = formOfPaymentService.findById(putPaymentServiceRequest.formOfPayment)

        return PaymentModel(
            idPayment = previousPayment.idPayment,
            formOfPayment = formOfPayment,
            customerService = previousPayment.customerService,
            valuePayment = putPaymentServiceRequest.valuePayment,
            datePayment = previousPayment.datePayment
        )
    }

    fun postPayPendencyRequestToPaymentModel(postPaymentServiceWithPendencyRequest: PostPaymentServiceWithPendencyRequest) {
        //Lista de atendimentos
        var customerServices = customerServiceModelService.findAllByIds(postPaymentServiceWithPendencyRequest.customerServices).sortedBy { it.idCustomerService }

        //valores ordenados dec
        var paymentsObjects = postPaymentServiceWithPendencyRequest.paymentObject.sortedBy { it.valuePayment.dec() }.toMutableList()

        forCustomerService@ for (customerService in customerServices) {
            val customerServiceCurrent = customerServiceModelService.findById(customerService.idCustomerService!!)
            forPaymentObject@ for (paymentsObject in paymentsObjects) {
                if (paymentsObject.valuePayment + customerServiceCurrent.paidValue!! < customerServiceCurrent.totalValue!!) {
                    paymentService.payServiceWithPendency(
                        PaymentModel(
                            formOfPayment = formOfPaymentService.findById(paymentsObject.formOfPayment),
                            customerService = customerServiceCurrent,
                            valuePayment = paymentsObject.valuePayment
                        )
                    )
                    paymentsObject.valuePayment = 0.0
                } else if (paymentsObject.valuePayment + customerServiceCurrent.paidValue!! == customerServiceCurrent.totalValue!!) {
                    paymentService.payServiceWithPendency(
                        PaymentModel(
                            formOfPayment = formOfPaymentService.findById(paymentsObject.formOfPayment),
                            customerService = customerServiceCurrent,
                            valuePayment = paymentsObject.valuePayment
                        )
                    )
                    paymentsObject.valuePayment = 0.0
                    break@forPaymentObject
                } else if (paymentsObject.valuePayment + customerServiceCurrent.paidValue!! > customerServiceCurrent.totalValue!!) {
                    val difference = customerServiceCurrent.totalValue!! - customerServiceCurrent.paidValue!!
                    paymentService.payServiceWithPendency(
                        PaymentModel(
                            formOfPayment = formOfPaymentService.findById(paymentsObject.formOfPayment),
                            customerService = customerServiceCurrent,
                            valuePayment = customerServiceCurrent.totalValue!! - customerServiceCurrent.paidValue!!
                        )
                    )
                    paymentsObject.valuePayment =
                        paymentsObject.valuePayment - difference
                    break@forPaymentObject
                }
            }
            paymentsObjects.removeIf { it.valuePayment == 0.0 }
        }
    }

    //Response
    fun toPaymentResponse(payment: PaymentModel): PaymentResponse{
        return PaymentResponse(
            idPayment = payment.idPayment,
            formOfPayment = payment.formOfPayment,
            valuePayment = payment.valuePayment,
            datePayment = payment.datePayment
        )

    }

    fun toListPaymentResponse(payments: List<PaymentModel>): MutableList<PaymentResponse>{
        var paymentResponses: MutableList<PaymentResponse> = mutableListOf()
        for (payment in payments){
            paymentResponses.add(
                PaymentResponse(
                    idPayment = payment.idPayment,
                    formOfPayment = payment.formOfPayment,
                    valuePayment = payment.valuePayment,
                    datePayment = payment.datePayment
                )
            )
        }
        return paymentResponses
    }
}