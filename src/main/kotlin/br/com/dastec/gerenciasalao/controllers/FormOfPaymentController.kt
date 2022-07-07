package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.extensions.toFormOfPayment
import br.com.dastec.gerenciasalao.controllers.requests.formofpayment.PostFormPaymentRequest
import br.com.dastec.gerenciasalao.controllers.requests.formofpayment.PutFormPaymentRequest
import br.com.dastec.gerenciasalao.controllers.responses.CreateResponse
import br.com.dastec.gerenciasalao.models.FormOfPaymentModel
import br.com.dastec.gerenciasalao.security.JwtUtil
import br.com.dastec.gerenciasalao.services.FormOfPaymentService
import br.com.dastec.gerenciasalao.services.SalonService
import br.com.dastec.gerenciasalao.services.UserService
import br.com.dastec.gerenciasalao.utils.SpringUtil
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/formpayment")
class FormOfPaymentController(private val formOfPaymentService: FormOfPaymentService, val springUtil: SpringUtil) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody postFormPaymentRequest: PostFormPaymentRequest, @RequestHeader(value = "Authorization") token: String): CreateResponse {
        formOfPaymentService.create(postFormPaymentRequest.toFormOfPayment(springUtil.getSalon(token.split(" ")[1])))
        return CreateResponse("Forma de pagamento criada com sucesso")
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    fun update(@PathVariable id: Long, @Valid @RequestBody putFormPaymentRequest: PutFormPaymentRequest): CreateResponse {
        val previousFormPayment = formOfPaymentService.findById(id)
        formOfPaymentService.create(putFormPaymentRequest.toFormOfPayment(previousFormPayment))
        return CreateResponse("Forma de pagamento atualizada com sucesso")
    }

    @GetMapping
    fun findAll(@RequestHeader(value = "Authorization") token: String): List<FormOfPaymentModel> {
        return formOfPaymentService.findAll(springUtil.getSalon(token.split(" ")[1]))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): CreateResponse {
        formOfPaymentService.delete(id)
        return CreateResponse("Forma de pagamento deletada com sucesso")
    }
}