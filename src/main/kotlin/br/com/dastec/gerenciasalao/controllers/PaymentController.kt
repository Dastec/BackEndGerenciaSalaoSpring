package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.extensions.toPaymentModel
import br.com.dastec.gerenciasalao.controllers.requests.payments.PostPaymentServiceRequest
import br.com.dastec.gerenciasalao.models.PaymentModel
import br.com.dastec.gerenciasalao.repositories.FormOfPaymentRepository
import br.com.dastec.gerenciasalao.repositories.PaymentRepository
import br.com.dastec.gerenciasalao.services.CustomerServiceModelService
import br.com.dastec.gerenciasalao.services.FormOfPaymentService
import br.com.dastec.gerenciasalao.services.PaymentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/payment")
class PaymentController(
    private val paymentService: PaymentService,
    private val customerServiceModelService: CustomerServiceModelService,
    private val formOfPaymentService: FormOfPaymentService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun payService(@RequestBody postPaymentServiceRequest: PostPaymentServiceRequest){
        val custumerService = customerServiceModelService.findById(postPaymentServiceRequest.customerService)
        val formOfPayment = formOfPaymentService.findById(postPaymentServiceRequest.formOfPayment)

        paymentService.payService(postPaymentServiceRequest.toPaymentModel(formOfPayment, custumerService))
    }

    @GetMapping
    fun findAll(): List<PaymentModel>{
        return paymentService.findAll()
    }

    @GetMapping("/customerservice/{id}")
    fun findByCustomerService(@PathVariable id : Long): List<PaymentModel>{
        val customerService = customerServiceModelService.findById(id)
        return paymentService.findByCustomerService(customerService)
    }
}