package br.com.dastec.gerenciasalao.controllers.extensions

import br.com.dastec.gerenciasalao.controllers.requests.PostCreateSaleServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.PutUpdateSaleServiceRequest
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.SaleServiceModel
import br.com.dastec.gerenciasalao.models.ServiceModel

fun PostCreateSaleServiceRequest.toSalesServiceModel(service: ServiceModel, customerService: CustomerServiceModel): SaleServiceModel {
    return SaleServiceModel(
        customerService = customerService,
        service = service,
        price = this.price,
    )
}

fun PutUpdateSaleServiceRequest.toSalesServiceModel(previousSaleServiceModel: SaleServiceModel): SaleServiceModel {
    return SaleServiceModel(
        idSaleService = previousSaleServiceModel.idSaleService,
        service = previousSaleServiceModel.service,
        customerService = previousSaleServiceModel.customerService,
        price = this.price
    )
}

