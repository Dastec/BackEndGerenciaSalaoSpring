package br.com.dastec.gerenciasalao.controllers.extensions

import br.com.dastec.gerenciasalao.controllers.requests.payments.PostPaymentServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.payments.PutPaymentServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.pendency.PutFinishPendencyRequest
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.PendencyModel
import br.com.dastec.gerenciasalao.models.enums.PendencyStatus

fun PostPaymentServiceRequest.toPaymentModel(customerService: CustomerServiceModel): PendencyModel{
    return PendencyModel(
        customerService = customerService,
        valuePendency = this.valuePayment,
        status = PendencyStatus.ABERTO
    )
}

fun PutPaymentServiceRequest.toPaymentModel(previouPendecy: PendencyModel): PendencyModel{
    return PendencyModel(
        idPendencyModel = previouPendecy.idPendencyModel,
        customerService = previouPendecy.customerService,
        valuePendency = this.valuePayment,
        status = previouPendecy.status
    )
}

fun PutFinishPendencyRequest.toPaymentModel(previouPendecy: PendencyModel): PendencyModel{
    return PendencyModel(
        idPendencyModel = previouPendecy.idPendencyModel,
        customerService = previouPendecy.customerService,
        valuePendency = 0.0,
        status = PendencyStatus.PAGO
    )
}