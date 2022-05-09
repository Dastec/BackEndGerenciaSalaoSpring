package br.com.dastec.gerenciasalao.controllers.mapper

import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PostStartCustomerServiceRequest
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.services.*
import org.springframework.stereotype.Component

@Component
class CustomerServiceMapper(
    private val customerServiceModelService: CustomerServiceModelService,
    private val serviceModelService: ServiceModelService,
    private val customerService: CustomerService,
    private val paymentService: PaymentService,
    private val pendencyService: PendencyService
) {

    fun toModel(postStartCustomerServiceRequest: PostStartCustomerServiceRequest): CustomerServiceModel{
        val customer = customerService.findById(postStartCustomerServiceRequest.customer)
        var services = serviceModelService.findByIds(postStartCustomerServiceRequest.services)

        return CustomerServiceModel(
            endTime = null,
            totalValue = services.sumOf { it.price!! },
            paidValue = null,
            customer = customer,
            services = services,
            observation = postStartCustomerServiceRequest.observation,
        )
    }
}