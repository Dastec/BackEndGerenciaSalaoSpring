package br.com.dastec.gerenciasalao.controllers.mapper

import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PostStartCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PutUpdateCustomerServiceRequest
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.PendencyModel
import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import br.com.dastec.gerenciasalao.services.*
import org.springframework.stereotype.Component
import java.time.LocalTime

@Component
class CustomerServiceMapper(
    private val customerServiceModelService: CustomerServiceModelService,
    private val serviceModelService: ServiceModelService,
    private val customerService: CustomerService,
    private val paymentService: PaymentService,
    private val pendencyService: PendencyService
) {

    fun postStartRequestToModel(postStartCustomerServiceRequest: PostStartCustomerServiceRequest): CustomerServiceModel{
        val customer = customerService.findById(postStartCustomerServiceRequest.customer)
        var services = serviceModelService.findByIds(postStartCustomerServiceRequest.services)

        return CustomerServiceModel(
            endTime = null,
            totalValue = services.sumOf { it.price!! },
            paidValue = null,
            customer = customer,
            services = services,
            observation = postStartCustomerServiceRequest.observation
        )
    }

    fun putUpdateRequestToModel(putUpdateCustomerServiceRequest: PutUpdateCustomerServiceRequest, previuoCustomerService: CustomerServiceModel): CustomerServiceModel{
        val customer = customerService.findById(putUpdateCustomerServiceRequest.customer)
        var services = serviceModelService.findByIds(putUpdateCustomerServiceRequest.services)

        return CustomerServiceModel(
            idCustomerService = previuoCustomerService.idCustomerService,
            dateCustomerService = previuoCustomerService.dateCustomerService,
            startTime = previuoCustomerService.startTime,
            endTime = null,
            totalValue = services.sumOf { it.price!! },
            paidValue = null,
            customer = customer,
            services = services,
            observation = putUpdateCustomerServiceRequest.observation,
            statusCustomerService = previuoCustomerService.statusCustomerService
        )
    }

    fun putFinalizeRequestToModel(previuoCustomerService: CustomerServiceModel): CustomerServiceModel{
        val payments = paymentService.findPaymentsWithCustomerServiceWithStatusAberto(previuoCustomerService.idCustomerService!!)
        val paidValue = payments.sumOf { it.valuePayment}

        if (previuoCustomerService.totalValue!! > paidValue){
            pendencyService.create(
                PendencyModel(
                customerService = previuoCustomerService,
                valuePendency = previuoCustomerService.totalValue!! - paidValue
                )
            )
            previuoCustomerService.statusCustomerService = CustomerServiceStatus.FINALIZADOCOMPENDENCIA
        }else{
            previuoCustomerService.statusCustomerService = CustomerServiceStatus.FINALIZADO
        }

        for (payment in payments){
            paymentService.updateStatusLancado(payment)
        }

        return CustomerServiceModel(
            idCustomerService = previuoCustomerService.idCustomerService,
            dateCustomerService = previuoCustomerService.dateCustomerService,
            startTime = previuoCustomerService.startTime,
            endTime = LocalTime.now(),
            totalValue = previuoCustomerService.totalValue,
            paidValue = paidValue,
            customer = previuoCustomerService.customer,
            services = previuoCustomerService.services,
            observation = previuoCustomerService.observation,
            statusCustomerService = previuoCustomerService.statusCustomerService
        )
    }
}

