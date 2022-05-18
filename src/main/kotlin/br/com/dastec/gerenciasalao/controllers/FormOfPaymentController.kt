package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.extensions.toFormOfPayment
import br.com.dastec.gerenciasalao.controllers.requests.formofpayment.PostFormPaymentRequest
import br.com.dastec.gerenciasalao.controllers.requests.formofpayment.PutFormPaymentRequest
import br.com.dastec.gerenciasalao.models.FormOfPaymentModel
import br.com.dastec.gerenciasalao.services.FormOfPaymentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/formpayment")
class FormOfPaymentController(private val formOfPaymentService: FormOfPaymentService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody postFormPaymentRequest: PostFormPaymentRequest) {
        formOfPaymentService.create(postFormPaymentRequest.toFormOfPayment())
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    fun update(@PathVariable id: Long, @RequestBody putFormPaymentRequest: PutFormPaymentRequest) {
        val previouFormPayment = formOfPaymentService.findById(id)
        formOfPaymentService.create(putFormPaymentRequest.toFormOfPayment(previouFormPayment))
    }

    @GetMapping
    fun findAll(): List<FormOfPaymentModel> {
        return formOfPaymentService.findAll()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        formOfPaymentService.delete(id)
    }
}