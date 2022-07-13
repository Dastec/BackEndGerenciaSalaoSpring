package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.extensions.toFormOfPayment
import br.com.dastec.gerenciasalao.controllers.extensions.toListFormOfPayment
import br.com.dastec.gerenciasalao.controllers.requests.formofpayment.PostFormPaymentRequest
import br.com.dastec.gerenciasalao.controllers.requests.formofpayment.PutFormPaymentRequest
import br.com.dastec.gerenciasalao.controllers.responses.FormOfPaymentResponse
import br.com.dastec.gerenciasalao.controllers.responses.MessageResponse
import br.com.dastec.gerenciasalao.models.FormOfPaymentModel
import br.com.dastec.gerenciasalao.services.FormOfPaymentService
import br.com.dastec.gerenciasalao.utils.SpringUtil
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/formpayment")
class FormOfPaymentController(private val formOfPaymentService: FormOfPaymentService, val springUtil: SpringUtil) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody postFormPaymentRequest: PostFormPaymentRequest, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        formOfPaymentService.create(postFormPaymentRequest.toFormOfPayment(springUtil.getSalon(token.split(" ")[1])))
        return MessageResponse("Forma de pagamento ${postFormPaymentRequest.nameFormPayment} criada com sucesso")
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    fun update(@PathVariable id: Long, @Valid @RequestBody putFormPaymentRequest: PutFormPaymentRequest, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val previousFormPayment = formOfPaymentService.findById(salon, id)
        formOfPaymentService.create(putFormPaymentRequest.toFormOfPayment(previousFormPayment))
        return MessageResponse("Forma de pagamento atualizada com sucesso")
    }

    @GetMapping
    fun findAll(@RequestHeader(value = "Authorization") token: String): List<FormOfPaymentResponse> {
        val salon = springUtil.getSalon(token.split(" ")[1])
        return formOfPaymentService.findAll(salon).toListFormOfPayment()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        val salon = springUtil.getSalon(token.split(" ")[1])
        formOfPaymentService.delete(salon, id)
        return MessageResponse("Forma de pagamento deletada com sucesso")
    }
}