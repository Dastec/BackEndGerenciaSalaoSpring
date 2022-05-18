package br.com.dastec.gerenciasalao.controllers.extensions

import br.com.dastec.gerenciasalao.controllers.requests.payments.PutPendecyServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.pendency.PostAddPendencyRequest
import br.com.dastec.gerenciasalao.controllers.requests.pendency.PutFinishPendencyRequest
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.PendencyModel
import br.com.dastec.gerenciasalao.models.enums.PendencyStatus

fun PostAddPendencyRequest.toPendencyModel(customerService: CustomerServiceModel): PendencyModel {
    return PendencyModel(
        customerService = customerService,
        valuePendency = this.valuePendency,
        status = PendencyStatus.ABERTO
    )
}

fun PutPendecyServiceRequest.toPendencyModel(previouPendecy: PendencyModel): PendencyModel {
    return PendencyModel(
        idPendencyModel = previouPendecy.idPendencyModel,
        customerService = previouPendecy.customerService,
        valuePendency = this.valuePayment,
        status = previouPendecy.status
    )
}

fun PutFinishPendencyRequest.toPendencyModel(previouPendecy: PendencyModel): PendencyModel {
    return PendencyModel(
        idPendencyModel = previouPendecy.idPendencyModel,
        customerService = previouPendecy.customerService,
        valuePendency = 0.0,
        status = PendencyStatus.PAGO
    )
}