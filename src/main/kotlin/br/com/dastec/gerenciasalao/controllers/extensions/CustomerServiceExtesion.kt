package br.com.dastec.gerenciasalao.controllers.extensions

import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PostStartCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PutFinalizeCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PutUpdateCustomerServiceRequest
import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.ServiceModel
import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import java.time.LocalDate
import java.time.LocalTime

fun PostStartCustomerServiceRequest.toCustomerService(services: List<ServiceModel>, customer: CustomerModel): CustomerServiceModel{
    var totalValue = 0.0
    for (service in services){
        totalValue+=service.price!!
    }
    return CustomerServiceModel(
        endTime = null,
        totalValue = totalValue,
        paidValue = null,
        customer = customer,
        services = services,
        observation = this.observation,
        statusCustomerService = CustomerServiceStatus.ABERTO
    )
}

fun PutUpdateCustomerServiceRequest.toCustomerService(previuoCustomerService: CustomerServiceModel, services: List<ServiceModel>, customer: CustomerModel): CustomerServiceModel{
    var totalValue = 0.0
    for (service in services){
        totalValue+=service.price!!
    }
    return CustomerServiceModel(
        idCustomerService = previuoCustomerService.idCustomerService,
        dateCustomerService = previuoCustomerService.dateCustomerService,
        startTime = previuoCustomerService.startTime,
        endTime = null,
        totalValue = totalValue,
        paidValue = null,
        customer = customer,
        services = services,
        observation = this.observation,
        statusCustomerService = previuoCustomerService.statusCustomerService
    )
}

fun PutFinalizeCustomerServiceRequest.toCustomerService(previuoCustomerService: CustomerServiceModel, paidValue: Double): CustomerServiceModel{

    return CustomerServiceModel(
        idCustomerService = previuoCustomerService.idCustomerService,
        dateCustomerService = previuoCustomerService.dateCustomerService,
        startTime = previuoCustomerService.startTime,
        endTime = LocalTime.now(),
        totalValue = previuoCustomerService.totalValue,
        paidValue = null,
        customer = previuoCustomerService.customer,
        services = previuoCustomerService.services,
        observation = previuoCustomerService.observation,
        statusCustomerService = CustomerServiceStatus.FINALIZADO
    )
}