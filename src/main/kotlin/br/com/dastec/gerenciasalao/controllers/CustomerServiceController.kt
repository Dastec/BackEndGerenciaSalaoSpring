package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.mapper.CustomerServiceMapper
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PostCreateCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PostStartCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PutFinalizeCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PutUpdateCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.responses.*
import br.com.dastec.gerenciasalao.exceptions.BadRequestException
import br.com.dastec.gerenciasalao.exceptions.IllegalStateException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import br.com.dastec.gerenciasalao.services.CustomerService
import br.com.dastec.gerenciasalao.services.CustomerServiceModelService
import br.com.dastec.gerenciasalao.utils.SpringUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
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
    fun startCustomerService(@Valid @RequestBody postStartCustomerServiceRequest: PostStartCustomerServiceRequest, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        customerService.LOGGER.info("Início do método de iniciar atendimento!")
        val salon = springUtil.getSalon(token.split(" ")[1])
        customerServiceModelService.startCustomerService(customerServiceMapper.postStartRequestToModel(salon, postStartCustomerServiceRequest))
        return MessageResponse("Atendimento iniciado com sucesso")
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomerService(@Valid @RequestBody postCreateCustomerServiceRequest: PostCreateCustomerServiceRequest, @RequestHeader(value = "Authorization") token: String): CustomerServiceResponse {
        customerService.LOGGER.info("Início do método de criação de atendimento!")
        val salon = springUtil.getSalon(token.split(" ")[1])
        return customerServiceMapper.toCustomerServiceResponse(customerServiceModelService.createCustomerService(customerServiceMapper.createCustomerModel(postCreateCustomerServiceRequest, salon)))
    }

    @PutMapping()
    fun updateCustomerService(@Valid @RequestBody putUpdateCustomerServiceRequest: PutUpdateCustomerServiceRequest, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        customerService.LOGGER.info("Início do método de atualização de atendimento!")
        val salon = springUtil.getSalon(token.split(" ")[1])
        customerServiceModelService.updateCustomerService(customerServiceMapper.putUpdateRequestToModel(salon, putUpdateCustomerServiceRequest))
        return MessageResponse("Atendimento atualizado com sucesso")
    }

    @DeleteMapping("/{id}")
    fun cancelCustomerService(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        customerService.LOGGER.info("Início do método de cancelamento de atendimento!")
        val salon = springUtil.getSalon(token.split(" ")[1])
        val previousCustomerService = customerServiceModelService.findById(salon, id)
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
        return MessageResponse("Atendimento cancelado com sucesso")
    }

    @PutMapping("finalize/{id}")
    fun finalizeCustomerService(
        @PathVariable id: Long,
        @RequestBody putFinalizeCustomerServiceRequest: PutFinalizeCustomerServiceRequest,
        @RequestHeader(value = "Authorization") token: String
    ): FinalizeCustomerServiceResponse {
        customerService.LOGGER.info("Início do método de finalização de atendimento!")
        val salon = springUtil.getSalon(token.split(" ")[1])
        val previousCustomerService = customerServiceModelService.findById(salon, id)
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
    fun findAll(@PageableDefault(page = 0, size = 10)pageable: Pageable, @RequestHeader(value = "Authorization") token: String): PageResponse<CustomerServiceResponse> {
        return customerServiceMapper.toPageResponse(customerServiceModelService.findAll(springUtil.getSalon(token.split(" ")[1]), pageable))
    }

    @GetMapping("/customer/{id}")
    fun findAllByCustomer(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): MutableList<CustomerServiceResponse> {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val customer = customerService.findByIdAndSalon(springUtil.getSalon(token.split(" ")[1]), id)
        return customerServiceMapper.toListCustomerServiceResponse(customerServiceModelService.findAllByCustomer(salon, customer))
    }

    @GetMapping("/statusopen/{id}")
    fun findByCustomerServiceWithStatusOpen(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): MutableList<CustomerServiceResponse> {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val customer = customerService.findByIdAndSalon(salon, id)
        return customerServiceMapper.toListCustomerServiceResponse(
            customerServiceModelService.findByCustomerServiceWithStatusOpen(salon, customer.idCustomer!!)
        )
    }

    @GetMapping("/statusfinalizedpending/{id}")
    fun findByCustomerServiceWithStatusFinalizedPending(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): MutableList<CustomerServiceWithPendencyResponse> {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val customer = customerService.findByIdAndSalon(salon, id)
        return customerServiceMapper.toListCustomerServiceWithPendencyResponse(customerServiceModelService.findByCustomerServiceWithStatusFinalizedPending(salon, customer.idCustomer!!)
        )
    }

    @GetMapping("/dailygain")
    fun findDailyGain(@RequestHeader(value = "Authorization") token: String): BigDecimal {
        return customerServiceModelService.findDailyGain(springUtil.getSalon(token.split(" ")[1]))
    }

    @GetMapping("/monthlygain")
    fun findMonthlyGain(@RequestHeader(value = "Authorization") token: String): BigDecimal {
        return customerServiceModelService.findMonthlyGain(springUtil.getSalon(token.split(" ")[1]))
    }


}