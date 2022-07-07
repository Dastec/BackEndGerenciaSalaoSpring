package br.com.dastec.gerenciasalao.controllers.extensions

import br.com.dastec.gerenciasalao.controllers.requests.formofpayment.PostFormPaymentRequest
import br.com.dastec.gerenciasalao.controllers.requests.formofpayment.PutFormPaymentRequest
import br.com.dastec.gerenciasalao.models.FormOfPaymentModel
import br.com.dastec.gerenciasalao.models.BeautySalonModel

fun PostFormPaymentRequest.toFormOfPayment(beautySalonModel: BeautySalonModel): FormOfPaymentModel {
    return FormOfPaymentModel(
        nameFormPayment = this.nameFormPayment,
        beautySalon = beautySalonModel
    )
}

fun PutFormPaymentRequest.toFormOfPayment(previouFormOfPaymen: FormOfPaymentModel): FormOfPaymentModel {
    return FormOfPaymentModel(
        idFormPayment = previouFormOfPaymen.idFormPayment,
        nameFormPayment = this.nameFormPayment,
        beautySalon = previouFormOfPaymen.beautySalon
    )
}