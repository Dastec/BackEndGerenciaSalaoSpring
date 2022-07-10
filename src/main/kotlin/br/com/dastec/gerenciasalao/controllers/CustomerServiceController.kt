package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.mapper.CustomerServiceMapper
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PostCreateCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PostStartCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PutFinalizeCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PutUpdateCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.responses.CreateResponse
import br.com.dastec.gerenciasalao.controllers.responses.CustomerServiceResponse
import br.com.dastec.gerenciasalao.controllers.responses.CustomerServiceWithPendencyResponse
import br.com.dastec.gerenciasalao.controllers.responses.FinalizeCustomerServiceResponse
import br.com.dastec.gerenciasalao.exceptions.BadRequestException
import br.com.dastec.gerenciasalao.exceptions.IllegalStateException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import br.com.dastec.gerenciasalao.security.JwtUtil
import br.com.dastec.gerenciasalao.services.CustomerService
import br.com.dastec.gerenciasalao.services.CustomerServiceModelService
import br.com.dastec.gerenciasalao.services.UserService
import br.com.dastec.gerenciasalao.utils.SpringUtil
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/customerservice")
class CustomerServiceController(
    private val customerServiceModelService: CustomerServiceModelService,
    private val customerServiceMapper: CustomerServiceMapper,
    private val customerService: CustomerService,
    val springUtil: SpringUtil
) {

    @PostMapping("/start")
    @ResponseStatus(HttpStatus.CREATED)
    fun startCustomerService(@Valid @RequestBody postStartCustomerServiceRequest: PostStartCustomerServiceRequest): CreateResponse {
        customerServiceModelService.startCustomerService(
            customerServiceMapper.postStartRequestToModel(postStartCustomerServiceRequest)
        )
        return CreateResponse("Atendimento iniciado com sucesso")

    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomerService(@Valid @RequestBody postCreateCustomerServiceRequest: PostCreateCustomerServiceRequest, @RequestHeader(value = "Authorization") token: String): CustomerServiceModel {
        val salon = springUtil.getSalon(token.split(" ")[1])
        return customerServiceModelService.createCustomerService(
            customerServiceMapper.createCustomerModel(postCreateCustomerServiceRequest, salon)
        )
    }

    @PutMapping()
    fun updateCustomerService(@Valid @RequestBody putUpdateCustomerServiceRequest: PutUpdateCustomerServiceRequest): CreateResponse {
        customerServiceModelService.updateCustomerService(
            customerServiceMapper.putUpdateRequestToModel(
                putUpdateCustomerServiceRequest
            )
        )
        return CreateResponse("Atendimento atualizado com sucesso")
    }

    @DeleteMapping("/{id}")
    fun cancelCustomerService(@PathVariable id: Long): CreateResponse {
        val previousCustomerService = customerServiceModelService.findById(id)
        if (previousCustomerService.statusCustomerService == CustomerServiceStatus.CANCELLED) {
            throw BadRequestException(
                Errors.GS505.message.format(previousCustomerService.idCustomerService),
                Errors.GS505.internalCode
            )
        }
        customerServiceModelService.cancelCustomerService(
            customerServiceMapper.putCancelRequestToModel(
                previousCustomerService
            )
        )
        return CreateResponse("Atendimento cancelado com sucesso")
    }

    @PutMapping("finalize/{id}")
    fun finalizeCustomerService(
        @PathVariable id: Long,
        @RequestBody putFinalizeCustomerServiceRequest: PutFinalizeCustomerServiceRequest,
    ): FinalizeCustomerServiceResponse {
        val previousCustomerService = customerServiceModelService.findById(id)
        if (previousCustomerService.statusCustomerService == CustomerServiceStatus.FINALIZEDPENDING || previousCustomerService.statusCustomerService == CustomerServiceStatus.FINISHED) {
            throw IllegalStateException(
                Errors.GS503.message.format(previousCustomerService.idCustomerService),
                Errors.GS503.internalCode
            )
        }
        return customerServiceMapper.toFinalizeCustomerServiceResponse(
            customerServiceModelService.finalizeCustomerService(
                customerServiceMapper.putFinalizeRequestToModel(
                    previousCustomerService,
                    putFinalizeCustomerServiceRequest,
                )
            )
        )
    }

    @GetMapping
    fun findAll(): MutableList<CustomerServiceResponse> {
        return customerServiceMapper.toListCustomerServiceResponse(customerServiceModelService.findAll())
    }

    @GetMapping("/customer/{id}")
    fun findAllByCustomer(@PathVariable id: Long): MutableList<CustomerServiceResponse> {
        val customer = customerService.findById(id)
        return customerServiceMapper.toListCustomerServiceResponse(customerServiceModelService.findAllByCustomer(customer))
    }

    @GetMapping("/statusopen/{id}")
    fun findByCustomerServiceWithStatusOpen(@PathVariable id: Long): MutableList<CustomerServiceResponse> {
        val customer = customerService.findById(id)
        return customerServiceMapper.toListCustomerServiceResponse(
            customerServiceModelService.findByCustomerServiceWithStatusOpen(
                customer.idCustomer!!
            )
        )
    }

    @GetMapping("/statusfinalizedpending/{id}")
    fun findByCustomerServiceWithStatusFinalizedPending(@PathVariable id: Long): MutableList<CustomerServiceWithPendencyResponse> {
        val customer = customerService.findById(id)
        return customerServiceMapper.toListCustomerServiceWithPendencyResponse(customerServiceModelService.findByCustomerServiceWithStatusFinalizedPending(customer.idCustomer!!)
        )
    }

}