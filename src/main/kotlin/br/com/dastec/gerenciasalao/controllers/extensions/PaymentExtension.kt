package br.com.dastec.gerenciasalao.controllers.extensions

import br.com.dastec.gerenciasalao.controllers.requests.payments.PostPaymentServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.payments.PutPaymentServiceRequest
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.FormOfPaymentModel
import br.com.dastec.gerenciasalao.models.PaymentModel
import java.time.LocalDate

fun PostPaymentServiceRequest.toPaymentModel(formOfPayment: FormOfPaymentModel, customerService: CustomerServiceModel): PaymentModel{
    return PaymentModel(
        formOfPayment = formOfPayment,
        customerService = customerService,
        valuePayment = 30.0
    )
}

fun PutPaymentServiceRequest.toPaymentModel(formOfPayment: FormOfPaymentModel, payment: PaymentModel): PaymentModel{
    return PaymentModel(
        idPayment = payment.idPayment,
        formOfPayment = formOfPayment,
        customerService = payment.customerService,
        valuePayment = this.valuePayment,
        datePayment =  payment.datePayment
    )
}