package br.com.dastec.gerenciasalao.controllers.extensions

import br.com.dastec.gerenciasalao.controllers.requests.PostServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.PutServiceRequest
import br.com.dastec.gerenciasalao.models.CategoryModel
import br.com.dastec.gerenciasalao.models.ServiceModel

fun PostServiceRequest.toServiceModel(categoryModel: CategoryModel): ServiceModel {
    return ServiceModel(
        nameService = this.nameService,
        category = categoryModel,
        price = this.price,
    )
}

fun PutServiceRequest.toServiceModel(serviceModel: ServiceModel, categoryModel: CategoryModel): ServiceModel {
    return ServiceModel(
        idService = serviceModel.idService,
        nameService = this.nameService ?: serviceModel.nameService,
        category = categoryModel,
        price = this.price,
    )
}

