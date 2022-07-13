package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.mapper.PaymentMapper
import br.com.dastec.gerenciasalao.controllers.requests.payments.PostPaymentServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.payments.PostPaymentServiceWithPendencyRequest
import br.com.dastec.gerenciasalao.controllers.requests.payments.PutPendecyServiceRequest
import br.com.dastec.gerenciasalao.controllers.responses.MessageResponse
import br.com.dastec.gerenciasalao.models.PaymentModel
import br.com.dastec.gerenciasalao.security.JwtUtil
import br.com.dastec.gerenciasalao.services.CustomerServiceModelService
import br.com.dastec.gerenciasalao.services.PaymentService
import br.com.dastec.gerenciasalao.services.UserService
import br.com.dastec.gerenciasalao.utils.SpringUtil
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/payment")
class PaymentController(
    private val paymentService: PaymentService,
    private val customerServiceModelService: CustomerServiceModelService,
    private val paymentMapper: PaymentMapper,
    private val userService: UserService,
    private val jwtUtil: JwtUtil,
    private val springUtil: SpringUtil
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun payService(@Valid @RequestBody postPaymentServiceRequest: PostPaymentServiceRequest, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val user = userService.findById(salon, jwtUtil.getUserInformation(token.split(" ")[1]).idUser)
        paymentService.payService(paymentMapper.postPaymentServiceRequestToPaymentModel(postPaymentServiceRequest, user))
        return MessageResponse("Pagamento incluído com sucesso")
    }

    @PostMapping("/paypendencies")
    @ResponseStatus(HttpStatus.CREATED)
    fun payServicePendency(@Valid @RequestBody postPaymentServiceWithPendencyRequest: PostPaymentServiceWithPendencyRequest, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val user = userService.findById(salon, jwtUtil.getUserInformation(token.split(" ")[1]).idUser)
        paymentService.validPaymentPendency(user.beautySalon, postPaymentServiceWithPendencyRequest)
        paymentMapper.postPayPendencyRequestToPaymentModel(postPaymentServiceWithPendencyRequest, user)
        return MessageResponse("Pagamento incluído com sucesso")
    }

    @PutMapping
    fun updatePayService(@PathVariable id: Long, @Valid @RequestBody putPaymentServiceRequest: PutPendecyServiceRequest, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val previousPayment = paymentService.findById(salon, id)
        paymentService.updatePayService(
            paymentMapper.putPaymentServiceRequestToPaymentModel(
                putPaymentServiceRequest,
                previousPayment
            )
        )
        return MessageResponse("Pagamento atualizado com sucesso")
    }

    @GetMapping
    fun findAll(@RequestHeader(value = "Authorization") token: String): List<PaymentModel> {
        val salon = springUtil.getSalon(token.split(" ")[1])
        return paymentService.findAll(salon)
    }

    @GetMapping("/customerservice/{id}")
    fun findPaymentByCustomerService(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): List<PaymentModel> {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val customerService = customerServiceModelService.findById(salon, id)
        return paymentService.findPaymentByCustomerService(customerService)
    }

    @GetMapping("/customerservicewithstatusopen/{id}")
    fun findPaymentWithCustomerServiceWithStatus(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): List<PaymentModel> {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val customerService = customerServiceModelService.findById(salon, id)
        return paymentService.findPaymentsByCustomerWithCustomerServiceWithStatusOpen(customerService.idCustomerService!!)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Esse endpoint é para teste
    @PostMapping("/paypendency/test")
    @ResponseStatus(HttpStatus.CREATED)
    fun payServicePendencyTest(@Valid @RequestBody postPaymentServiceRequest: PostPaymentServiceRequest, @RequestHeader(value = "Authorization") token: String) {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val user = userService.findById(salon, jwtUtil.getUserInformation(token.split(" ")[1]).idUser)
        paymentService.payServiceWithPendency(
            paymentMapper.postPaymentPendencyServiceRequestToPaymentModel(
                postPaymentServiceRequest, user
            )
        )
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}