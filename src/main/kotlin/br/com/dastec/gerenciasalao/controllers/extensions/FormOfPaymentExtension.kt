package br.com.dastec.gerenciasalao.controllers.extensions

import br.com.dastec.gerenciasalao.controllers.requests.formofpayment.PostFormPaymentRequest
import br.com.dastec.gerenciasalao.controllers.requests.formofpayment.PutFormPaymentRequest
import br.com.dastec.gerenciasalao.models.FormOfPaymentModel

fun PostFormPaymentRequest.toFormOfPayment(): FormOfPaymentModel {
    return FormOfPaymentModel(
        nameFormPayment = this.nameFormPayment
    )
}

fun PutFormPaymentRequest.toFormOfPayment(previouFormOfPaymen: FormOfPaymentModel): FormOfPaymentModel {
    return FormOfPaymentModel(
        idFormPayment = previouFormOfPaymen.idFormPayment,
        nameFormPayment = this.nameFormPayment
    )
}