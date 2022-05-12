package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.mapper.PaymentMapper
import br.com.dastec.gerenciasalao.controllers.requests.payments.PostPaymentServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.payments.PutPaymentServiceRequest
import br.com.dastec.gerenciasalao.models.PaymentModel
import br.com.dastec.gerenciasalao.services.CustomerServiceModelService
import br.com.dastec.gerenciasalao.services.FormOfPaymentService
import br.com.dastec.gerenciasalao.services.PaymentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/payment")
class PaymentController(
    private val paymentService: PaymentService,
    private val customerServiceModelService: CustomerServiceModelService,
    private val formOfPaymentService: FormOfPaymentService,
    private val paymentMapper: PaymentMapper
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun payService(@RequestBody postPaymentServiceRequest: PostPaymentServiceRequest){
        paymentService.payService(paymentMapper.postPaymentServiceRequestToPaymentModel(postPaymentServiceRequest))
    }

    @PostMapping("/paypendency")
    @ResponseStatus(HttpStatus.CREATED)
    fun payServicePendency(@RequestBody postPaymentServiceRequest: PostPaymentServiceRequest){
        paymentService.payServiceWithPendeny(paymentMapper.postPaymentPendencyServiceRequestToPaymentModel(postPaymentServiceRequest))
    }

    @PutMapping
    fun updatePayServicePendency(@PathVariable id: Long, @RequestBody putPaymentServiceRequest: PutPaymentServiceRequest){
        val previouPayment = paymentService.findById(id)
        paymentService.payService(paymentMapper.putPaymentServiceRequestToPaymentModel(putPaymentServiceRequest, previouPayment))
    }

    @GetMapping
    fun findAll(): List<PaymentModel>{
        return paymentService.findAll()
    }

    @GetMapping("/customerservice/{id}")
    fun findPaymentByCustomerService(@PathVariable id : Long): List<PaymentModel>{
        val customerService = customerServiceModelService.findById(id)
        return paymentService.findPaymentByCustomerService(customerService)
    }

    @GetMapping("/customerservicewithstatusaaberto/{id}")
    fun findPaymentWithCustomerServiceWithStatus(@PathVariable id : Long): List<PaymentModel>{
        val customerService = customerServiceModelService.findById(id)
        return paymentService.findPaymentsWithCustomerServiceWithStatusAberto(customerService.idCustomerService!!)
    }
}