package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.mapper.PaymentMapper
import br.com.dastec.gerenciasalao.controllers.requests.payments.PostPaymentServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.payments.PostPaymentServiceWithPendencyRequest
import br.com.dastec.gerenciasalao.controllers.requests.payments.PutPendecyServiceRequest
import br.com.dastec.gerenciasalao.controllers.responses.CreateResponse
import br.com.dastec.gerenciasalao.models.PaymentModel
import br.com.dastec.gerenciasalao.services.CustomerServiceModelService
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
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/payment")
class PaymentController(
    private val paymentService: PaymentService,
    private val customerServiceModelService: CustomerServiceModelService,
    private val paymentMapper: PaymentMapper,
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun payService(@Valid @RequestBody postPaymentServiceRequest: PostPaymentServiceRequest): CreateResponse {
        paymentService.payService(paymentMapper.postPaymentServiceRequestToPaymentModel(postPaymentServiceRequest))
        return CreateResponse("Pagamento incluído com sucesso")
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Esse endpoint é para teste
    @PostMapping("/paypendency/test")
    @ResponseStatus(HttpStatus.CREATED)
    fun payServicePendencyTest(@Valid @RequestBody postPaymentServiceRequest: PostPaymentServiceRequest) {
        paymentService.payServiceWithPendency(
            paymentMapper.postPaymentPendencyServiceRequestToPaymentModel(
                postPaymentServiceRequest
            )
        )
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/paypendencies")
    @ResponseStatus(HttpStatus.CREATED)
    fun payServicePendency(@Valid @RequestBody postPaymentServiceWithPendencyRequest: PostPaymentServiceWithPendencyRequest): CreateResponse {
        paymentService.validPaymentPendency(postPaymentServiceWithPendencyRequest)
        paymentMapper.postPayPendencyRequestToPaymentModel(postPaymentServiceWithPendencyRequest)
        return CreateResponse("Pagamento incluído com sucesso")
    }

    @PutMapping
    fun updatePayService(@PathVariable id: Long, @Valid @RequestBody putPaymentServiceRequest: PutPendecyServiceRequest): CreateResponse {
        val previousPayment = paymentService.findById(id)
        paymentService.updatePayService(
            paymentMapper.putPaymentServiceRequestToPaymentModel(
                putPaymentServiceRequest,
                previousPayment
            )
        )
        return CreateResponse("Pagamento atualizado com sucesso")
    }

    @GetMapping
    fun findAll(): List<PaymentModel> {
        return paymentService.findAll()
    }

    @GetMapping("/customerservice/{id}")
    fun findPaymentByCustomerService(@PathVariable id: Long): List<PaymentModel> {
        val customerService = customerServiceModelService.findById(id)
        return paymentService.findPaymentByCustomerService(customerService)
    }

    @GetMapping("/customerservicewithstatusopen/{id}")
    fun findPaymentWithCustomerServiceWithStatus(@PathVariable id: Long): List<PaymentModel> {
        val customerService = customerServiceModelService.findById(id)
        return paymentService.findPaymentsByCustomerWithCustomerServiceWithStatusOpen(customerService.idCustomerService!!)
    }
}