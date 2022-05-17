package br.com.dastec.gerenciasalao.controllers.mapper

import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PostStartCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PutUpdateCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.payments.PostPaymentServiceWithPendencyRequest
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
    private val formOfPaymentService: FormOfPaymentService
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
        val payments = paymentService.findPaymentsByCustomerWithCustomerServiceWithStatusAberto(previousCustomerService.idCustomerService!!)
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
}


//fun putFinalizeRequestWithPendencyToModel(previousCustomerService: CustomerServiceModel): CustomerServiceModel{
//        val payments = paymentService.findPaymentsByCustomerWithCustomerServiceWithStatusAberto(previousCustomerService.idCustomerService!!)
//        val paidValue = payments.sumOf { it.valuePayment}
//
//        if (previousCustomerService.totalValue!! > paidValue){
//            pendencyService.create(
//                PendencyModel(
//                    customerService = previousCustomerService,
//                    valuePendency = previousCustomerService.totalValue!! - paidValue
//                )
//            )
//            previousCustomerService.statusCustomerService = CustomerServiceStatus.FINALIZADOCOMPENDENCIA
//        }else{
//            previousCustomerService.statusCustomerService = CustomerServiceStatus.FINALIZADO
//        }
//
//        for (payment in payments){
//            paymentService.updateStatusLancado(payment)
//        }
//
//        return CustomerServiceModel(
//            idCustomerService = previousCustomerService.idCustomerService,
//            dateCustomerService = previousCustomerService.dateCustomerService,
//            startTime = previousCustomerService.startTime,
//            endTime = previousCustomerService.endTime,
//            totalValue = previousCustomerService.totalValue,
//            paidValue = paidValue,
//            customer = previousCustomerService.customer,
//            services = previousCustomerService.services,
//            observation = previousCustomerService.observation,
//            statusCustomerService = previousCustomerService.statusCustomerService
//        )
//    }

