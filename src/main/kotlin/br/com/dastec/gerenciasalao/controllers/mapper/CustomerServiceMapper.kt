package br.com.dastec.gerenciasalao.controllers.mapper

import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PostCreateCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PostStartCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PutFinalizeCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PutUpdateCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.responses.CustomerServiceResponse
import br.com.dastec.gerenciasalao.controllers.responses.CustomerServiceWithPendencyResponse
import br.com.dastec.gerenciasalao.controllers.responses.FinalizeCustomerServiceResponse
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.PendencyModel
import br.com.dastec.gerenciasalao.models.UserModel
import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import br.com.dastec.gerenciasalao.services.*
import org.springframework.stereotype.Component
import java.time.LocalTime

@Component
class CustomerServiceMapper(
    private val customerServiceModelService: CustomerServiceModelService,
    private val customerService: CustomerService,
    private val paymentService: PaymentService,
    private val pendencyService: PendencyService,
    private val saleServiceModelService: SaleServiceModelService,
    private val saleServiceMapper: SaleServiceMapper,
    private val paymentMapper: PaymentMapper,
    private val pendencyMapper: PendencyMapper
) {

    fun createCustomerModel(postCreateCustomerServiceRequest: PostCreateCustomerServiceRequest, salonModel: BeautySalonModel): CustomerServiceModel {
        val customer = customerService.findById(postCreateCustomerServiceRequest.customer)
        return CustomerServiceModel(
            endTime = null,
            totalValue = null,
            customer = customer,
            observation = null,
            beautySalon = salonModel
        )
    }

    fun postStartRequestToModel(postStartCustomerServiceRequest: PostStartCustomerServiceRequest): CustomerServiceModel {
        val customerService = customerServiceModelService.findById(postStartCustomerServiceRequest.customerService)
        var saleServices = saleServiceModelService.findByCustomerService(customerService)

        return CustomerServiceModel(
            idCustomerService = customerService.idCustomerService,
            endTime = null,
            totalValue = saleServices.sumOf { it.price },
            customer = customerService.customer,
            saleServices = saleServices,
            statusCustomerService = CustomerServiceStatus.OPEN,
            observation = null,
            beautySalon = customerService.beautySalon
        )
    }

    fun putUpdateRequestToModel(
        putUpdateCustomerServiceRequest: PutUpdateCustomerServiceRequest,
    ): CustomerServiceModel {
        val customer = customerService.findById(putUpdateCustomerServiceRequest.customer)
        val previousCustomerService = customerServiceModelService.findById(putUpdateCustomerServiceRequest.customerService)
        var saleServices = saleServiceModelService.findByCustomerService(previousCustomerService)

        return CustomerServiceModel(
            idCustomerService = previousCustomerService.idCustomerService,
            startTime = previousCustomerService.startTime,
            endTime = null,
            totalValue = saleServices.sumOf { it.price },
            customer = customer,
            saleServices = saleServices,
            statusCustomerService = previousCustomerService.statusCustomerService,
            observation = null,
            beautySalon = previousCustomerService.beautySalon
        )
    }

    fun putFinalizeRequestToModel(previousCustomerService: CustomerServiceModel, putFinalizeCustomerServiceRequest: PutFinalizeCustomerServiceRequest): CustomerServiceModel {
        val payments =
            paymentService.findPaymentsByCustomerWithCustomerServiceWithStatusOpen(previousCustomerService.idCustomerService!!)
        val paidValue = payments.sumOf { it.valuePayment }

        if (paidValue < previousCustomerService.totalValue!!) {
            pendencyService.createPendency(
                PendencyModel(
                    customerService = previousCustomerService,
                    valuePendency = previousCustomerService.totalValue!! - paidValue,
                    beautySalon = previousCustomerService.beautySalon
                )
            )
            previousCustomerService.statusCustomerService = CustomerServiceStatus.FINALIZEDPENDING
        } else {
            previousCustomerService.statusCustomerService = CustomerServiceStatus.FINISHED
        }

        for (payment in payments) {
            paymentService.updateStatusLaunched(payment)
        }

        return CustomerServiceModel(
            idCustomerService = previousCustomerService.idCustomerService,
            dateCustomerService = previousCustomerService.dateCustomerService,
            startTime = previousCustomerService.startTime,
            endTime = LocalTime.now(),
            totalValue = previousCustomerService.totalValue,
            paidValue = paidValue,
            customer = previousCustomerService.customer,
            saleServices = previousCustomerService.saleServices,
            observation = putFinalizeCustomerServiceRequest.observation,
            statusCustomerService = previousCustomerService.statusCustomerService,
            beautySalon = previousCustomerService.beautySalon
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
            saleServices  = previousCustomerService.saleServices,
            observation = previousCustomerService.observation,
            statusCustomerService = CustomerServiceStatus.CANCELLED,
            beautySalon = previousCustomerService.beautySalon
        )
    }

    //Responses
    fun toCustomerServiceResponse(customerService: CustomerServiceModel): CustomerServiceResponse {
        val saleServicesResponse = saleServiceMapper.toSaleServiceResponse(saleServiceModelService.findByCustomerService(customerService))
        val paymentsResponse =  paymentMapper.toListPaymentResponse(paymentService.findPaymentByCustomerService(customerService))
        return CustomerServiceResponse(
            idCustomerService = customerService.idCustomerService,
            dateCustomerService = customerService.dateCustomerService,
            startTime = customerService.startTime,
            endTime = customerService.endTime,
            totalValue = customerService.totalValue,
            paidValue = customerService.paidValue,
            customer = customerService.customer!!.alias,
            payments = paymentsResponse,
            saleServices = saleServicesResponse,
            observation = customerService.observation,
            statusCustomerService = customerService.statusCustomerService,
        )
    }

    fun toListCustomerServiceResponse(customerServices: List<CustomerServiceModel>): MutableList<CustomerServiceResponse> {
        val customerServicesResponse: MutableList<CustomerServiceResponse> = mutableListOf()


        for (customerService in customerServices) {
            val saleServicesResponse = saleServiceMapper.toSaleServiceResponse(saleServiceModelService.findByCustomerService(customerService))
            val paymentsResponse =  paymentMapper.toListPaymentResponse(paymentService.findPaymentByCustomerService(customerService))
            val customerServiceResponse = CustomerServiceResponse(
                idCustomerService = customerService.idCustomerService,
                dateCustomerService = customerService.dateCustomerService,
                startTime = customerService.startTime,
                endTime = customerService.endTime,
                totalValue = customerService.totalValue,
                paidValue = customerService.paidValue,
                customer = customerService.customer!!.alias,
                payments = paymentsResponse,
                saleServices = saleServicesResponse,
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
            customer = customerService.customer!!.alias,
            totalValue = customerService.totalValue,
            paidValue = customerService.paidValue,
            pendingValue = (customerService.totalValue!! - customerService.paidValue!!),
        )
    }

    fun toCustomerServiceWithPendencyResponse(customerService: CustomerServiceModel): CustomerServiceWithPendencyResponse {
        val saleServicesResponse = saleServiceMapper.toSaleServiceResponse(saleServiceModelService.findByCustomerService(customerService))
        val paymentsResponse =  paymentMapper.toListPaymentResponse(paymentService.findPaymentByCustomerService(customerService))
        val pendency = pendencyMapper.toPendencyResponse(pendencyService.findByCustomerService(customerService))
        return CustomerServiceWithPendencyResponse(
            idCustomerService = customerService.idCustomerService,
            dateCustomerService = customerService.dateCustomerService,
            startTime = customerService.startTime,
            endTime = customerService.endTime,
            totalValue = customerService.totalValue,
            paidValue = customerService.paidValue,
            customer = customerService.customer!!.alias,
            payments = paymentsResponse,
            saleServices = saleServicesResponse,
            observation = customerService.observation,
            pendency = pendency,
            statusCustomerService = customerService.statusCustomerService

        )
    }

    fun toListCustomerServiceWithPendencyResponse(customerServices: List<CustomerServiceModel>): MutableList<CustomerServiceWithPendencyResponse> {
        val customerServicesWithPendencyResponse: MutableList<CustomerServiceWithPendencyResponse> = mutableListOf()


        for (customerService in customerServices) {
            val saleServicesResponse = saleServiceMapper.toSaleServiceResponse(saleServiceModelService.findByCustomerService(customerService))
            val paymentsResponse =  paymentMapper.toListPaymentResponse(paymentService.findPaymentByCustomerService(customerService))
            val pendency = pendencyMapper.toPendencyResponse(pendencyService.findByCustomerService(customerService))
            val customerServiceWithPendencyResponse = CustomerServiceWithPendencyResponse(
                idCustomerService = customerService.idCustomerService,
                dateCustomerService = customerService.dateCustomerService,
                startTime = customerService.startTime,
                endTime = customerService.endTime,
                totalValue = customerService.totalValue,
                paidValue = customerService.paidValue,
                customer = customerService.customer!!.alias,
                payments = paymentsResponse,
                saleServices = saleServicesResponse,
                observation = customerService.observation,
                pendency = pendency,
                statusCustomerService = customerService.statusCustomerService
            )
            customerServicesWithPendencyResponse.add(customerServiceWithPendencyResponse)
        }
        return customerServicesWithPendencyResponse
    }
}