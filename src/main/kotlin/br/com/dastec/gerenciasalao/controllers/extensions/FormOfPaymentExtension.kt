package br.com.dastec.gerenciasalao.controllers.extensions

import br.com.dastec.gerenciasalao.controllers.requests.formofpayment.PostFormPaymentRequest
import br.com.dastec.gerenciasalao.controllers.requests.formofpayment.PutFormPaymentRequest
import br.com.dastec.gerenciasalao.controllers.responses.FormOfPaymentResponse
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

fun FormOfPaymentModel.toFormOfPaymentReponse(): FormOfPaymentResponse{
    return FormOfPaymentResponse(
        idFormPayment = this.idFormPayment!!,
        nameFormPayment = this.nameFormPayment
    )
}

fun List<FormOfPaymentModel>.toListFormOfPayment(): List<FormOfPaymentResponse>{
    val formsPayment = mutableListOf<FormOfPaymentResponse>()

    for (form in this){
        formsPayment.add(form.toFormOfPaymentReponse())
    }
    return formsPayment
}