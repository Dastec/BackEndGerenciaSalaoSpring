package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.mapper.CustomerServiceMapper
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PostStartCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PutUpdateCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.responses.CreateResponse
import br.com.dastec.gerenciasalao.controllers.responses.CustomerServiceResponse
import br.com.dastec.gerenciasalao.controllers.responses.FinalizeCustomerServiceResponse
import br.com.dastec.gerenciasalao.exceptions.BadRequestException
import br.com.dastec.gerenciasalao.exceptions.IllegalStateException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import br.com.dastec.gerenciasalao.services.*
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
@RequestMapping("api/v1/customerservices")
class CustomerServiceController(
    private val customerServiceModelService: CustomerServiceModelService,
    private val customerServiceMapper: CustomerServiceMapper,
    private val customerService: CustomerService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun startCustomerService(@RequestBody postStartCustomerServiceRequest: PostStartCustomerServiceRequest): CreateResponse {
        customerServiceModelService.startCustomerService(
            customerServiceMapper.postStartRequestToModel(
                postStartCustomerServiceRequest
            )
        )
        return CreateResponse("Atendimento iniciado com sucesso")

    }

    @PutMapping("/{id}")
    fun updateCustomerService(@PathVariable id: Long, @RequestBody putStartCustomerServiceRequest: PutUpdateCustomerServiceRequest): CreateResponse {
        val previousCustomerService = customerServiceModelService.findById(id)
        customerServiceModelService.updateCustomerService(
            customerServiceMapper.putUpdateRequestToModel(
                putStartCustomerServiceRequest,
                previousCustomerService
            )
        )
        return CreateResponse("Atendimento atualizado com sucesso")
    }

    @DeleteMapping("/{id}")
    fun cancelCustomerService(@PathVariable id: Long): CreateResponse {
        val previousCustomerService = customerServiceModelService.findById(id)
        if (previousCustomerService.statusCustomerService == CustomerServiceStatus.CANCELADO){
            throw BadRequestException(Errors.GS505.message.format(previousCustomerService.idCustomerService), Errors.GS505.internalCode)
        }
        customerServiceModelService.cancelCustomerService(customerServiceMapper.putCancelRequestToModel(previousCustomerService))
        return CreateResponse("Atendimento cancelado com sucesso")
    }

    @PutMapping("finalize/{id}")
    fun finalizeCustomerService(@PathVariable id: Long): FinalizeCustomerServiceResponse {
        var previousCustomerService = customerServiceModelService.findById(id)
        if (previousCustomerService.statusCustomerService == CustomerServiceStatus.FINALIZADOCOMPENDENCIA || previousCustomerService.statusCustomerService == CustomerServiceStatus.FINALIZADO) {
            throw IllegalStateException(
                Errors.GS503.message.format(previousCustomerService.idCustomerService),
                Errors.GS503.internalCode
            )
        }
        return customerServiceMapper.toFinalizeCustomerServiceResponse(customerServiceModelService.finalizeCustomerService(customerServiceMapper.putFinalizeRequestToModel(previousCustomerService)))
    }

    @GetMapping
    fun findAll(): MutableList<CustomerServiceResponse> {
        return customerServiceMapper.toListCustomerServiceResponse(customerServiceModelService.findAll())
    }

    @GetMapping("/customer/{id}")
    fun findAllById(@PathVariable id: Long): MutableList<CustomerServiceResponse> {
        val customer = customerService.findById(id)
        return customerServiceMapper.toListCustomerServiceResponse(customerServiceModelService.findAllById(customer))
    }

    @GetMapping("/statusaberto/{id}")
    fun findByCustomerServiceWithStatusAberto(@PathVariable id: Long): MutableList<CustomerServiceResponse> {
        val customer = customerService.findById(id)
        return customerServiceMapper.toListCustomerServiceResponse(customerServiceModelService.findByCustomerServiceWithStatusAberto(customer.idCustomer!!))
    }

}