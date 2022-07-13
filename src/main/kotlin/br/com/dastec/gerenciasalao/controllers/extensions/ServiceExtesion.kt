package br.com.dastec.gerenciasalao.controllers.extensions

import br.com.dastec.gerenciasalao.controllers.requests.PostServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.PutServiceRequest
import br.com.dastec.gerenciasalao.controllers.responses.ServiceResponse
import br.com.dastec.gerenciasalao.models.CategoryModel
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.models.ServiceModel

fun PostServiceRequest.toServiceModel(categoryModel: CategoryModel, beautySalonModel: BeautySalonModel): ServiceModel {
    return ServiceModel(
        nameService = this.nameService,
        category = categoryModel,
        price = this.price,
        beautySalon = beautySalonModel
    )
}

fun PutServiceRequest.toServiceModel(serviceModel: ServiceModel, categoryModel: CategoryModel): ServiceModel {
    return ServiceModel(
        idService = serviceModel.idService,
        nameService = this.nameService ?: serviceModel.nameService,
        category = categoryModel,
        price = this.price,
        beautySalon = serviceModel.beautySalon
    )
}

fun ServiceModel.toServiceResponse(): ServiceResponse{
    return ServiceResponse(
        idService = this.idService!!,
        nameService = this.nameService
    )
}

fun List<ServiceModel>.toListServiceResponse(): List<ServiceResponse>{
    val servicesResponse = mutableListOf<ServiceResponse>()
    for(service in this){
        servicesResponse.add(service.toServiceResponse())
    }
    return servicesResponse
}

