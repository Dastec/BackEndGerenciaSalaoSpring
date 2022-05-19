package br.com.dastec.gerenciasalao.controllers.mapper

import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PostStartCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PutUpdateCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.responses.CustomerServiceResponse
import br.com.dastec.gerenciasalao.controllers.responses.FinalizeCustomerServiceResponse
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
    private val pendencyService: PendencyService,
    private val formOfPaymentService: FormOfPaymentService,
    private val customerMapper: CustomerMapper
) {

    fun postStartRequestToModel(postStartCustomerServiceRequest: PostStartCustomerServiceRequest): CustomerServiceModel {
        val customer = customerService.findById(postStartCustomerServiceRequest.customer)
        var services = serviceModelService.findByIds(postStartCustomerServiceRequest.services)

        return CustomerServiceModel(
            endTime = null,
            totalValue = services.sumOf { it.price!! },
            customer = customer,
            services = services,
            observation = postStartCustomerServiceRequest.observation
        )
    }

    fun putUpdateRequestToModel(
        putUpdateCustomerServiceRequest: PutUpdateCustomerServiceRequest,
        previouCustomerService: CustomerServiceModel
    ): CustomerServiceModel {
        val customer = customerService.findById(putUpdateCustomerServiceRequest.customer)
        var services = serviceModelService.findByIds(putUpdateCustomerServiceRequest.services)

        return CustomerServiceModel(
            idCustomerService = previouCustomerService.idCustomerService,
            dateCustomerService = previouCustomerService.dateCustomerService,
            startTime = previouCustomerService.startTime,
            endTime = previouCustomerService.endTime,
            totalValue = services.sumOf { it.price!! },
            paidValue = previouCustomerService.paidValue,
            customer = customer,
            services = services,
            observation = putUpdateCustomerServiceRequest.observation,
            statusCustomerService = previouCustomerService.statusCustomerService
        )
    }

    fun putFinalizeRequestToModel(previousCustomerService: CustomerServiceModel): CustomerServiceModel {
        val payments =
            paymentService.findPaymentsByCustomerWithCustomerServiceWithStatusAberto(previousCustomerService.idCustomerService!!)
        val paidValue = payments.sumOf { it.valuePayment }

        if (paidValue < previousCustomerService.totalValue!!) {
            pendencyService.createPendency(
                PendencyModel(
                    customerService = previousCustomerService,
                    valuePendency = previousCustomerService.totalValue!! - paidValue
                )
            )
            previousCustomerService.statusCustomerService = CustomerServiceStatus.FINALIZADOCOMPENDENCIA
        } else {
            previousCustomerService.statusCustomerService = CustomerServiceStatus.FINALIZADO
        }

        for (payment in payments) {
            paymentService.updateStatusLancado(payment)
        }

        return CustomerServiceModel(
            idCustomerService = previousCustomerService.idCustomerService,
            dateCustomerService = previousCustomerService.dateCustomerService,
            startTime = previousCustomerService.startTime,
            endTime = LocalTime.now(),
            totalValue = previousCustomerService.totalValue,
            paidValue = paidValue,
            customer = previousCustomerService.customer,
            services = previousCustomerService.services,
            observation = previousCustomerService.observation,
            statusCustomerService = previousCustomerService.statusCustomerService
        )
    }

    fun putCancelRequestToModel(
        previousCustomerService: CustomerServiceModel
    ): CustomerServiceModel {

        return CustomerServiceModel(
            idCustomerService = previousCustomerService.idCustomerService,
            dateCustomerService = previousCustomerService.dateCustomerService,
            startTime = previousCustomerService.startTime,
            endTime = previousCustomerService.endTime,
            totalValue = previousCustomerService.totalValue,
            paidValue = previousCustomerService.paidValue,
            customer = previousCustomerService.customer,
            services = previousCustomerService.services,
            observation = previousCustomerService.observation,
            statusCustomerService = CustomerServiceStatus.CANCELADO
        )
    }

    fun toCustomerServiceResponse(customerService: CustomerServiceModel): CustomerServiceResponse {
        return CustomerServiceResponse(
            idCustomerService = customerService.idCustomerService,
            dateCustomerService = customerService.dateCustomerService,
            startTime = customerService.startTime,
            endTime = customerService.endTime,
            totalValue = customerService.totalValue,
            paidValue = customerService.paidValue,
            customer = customerMapper.toCustomerResponse(customerService.customer),
            services = customerService.services,
            observation = customerService.observation,
            statusCustomerService = customerService.statusCustomerService

        )
    }

    fun toListCustomerServiceResponse(customerServices: List<CustomerServiceModel>): MutableList<CustomerServiceResponse> {
        val customerServicesResponse: MutableList<CustomerServiceResponse> = mutableListOf()

        for (customerService in customerServices) {
            val customerServiceResponse = CustomerServiceResponse(
                idCustomerService = customerService.idCustomerService,
                dateCustomerService = customerService.dateCustomerService,
                startTime = customerService.startTime,
                endTime = customerService.endTime,
                totalValue = customerService.totalValue,
                paidValue = customerService.paidValue,
                customer = customerMapper.toCustomerResponse(customerService.customer),
                services = customerService.services,
                observation = customerService.observation,
                statusCustomerService = customerService.statusCustomerService

            )
            customerServicesResponse.add(customerServiceResponse)
        }
        return customerServicesResponse
    }

    fun toFinalizeCustomerServiceResponse(customerService: CustomerServiceModel): FinalizeCustomerServiceResponse{
        return FinalizeCustomerServiceResponse(
            statusCustomerService = customerService.statusCustomerService,
            idCustomerService = customerService.idCustomerService,
            customer = customerService.customer.alias,
            totalValue = customerService.totalValue,
            paidValue = customerService.paidValue,
            pendingValue = (customerService.totalValue!! - customerService.paidValue!!),
        )
    }
}