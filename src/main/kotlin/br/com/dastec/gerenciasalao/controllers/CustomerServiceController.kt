package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.mapper.CustomerServiceMapper
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PostStartCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PutUpdateCustomerServiceRequest
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.services.*
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
@RequestMapping("api/v1/customerservices")
class CustomerServiceController(
    private val customerServiceModelService: CustomerServiceModelService,
    private val customerServiceMapper: CustomerServiceMapper,
    private val customerService: CustomerService
    ) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun startCustomerService(@RequestBody postStartCustomerServiceRequest: PostStartCustomerServiceRequest){
        customerServiceModelService.startCustomerService(customerServiceMapper.postStartRequestToModel(postStartCustomerServiceRequest))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody putStartCustomerServiceRequest: PutUpdateCustomerServiceRequest){
        val previousCustomerService = customerServiceModelService.findById(id)

        customerServiceModelService.finalizeCustomerService(customerServiceMapper.putUpdateRequestToModel(putStartCustomerServiceRequest, previousCustomerService))
    }

    @PutMapping("finalize/{id}")
    fun finalizeCustomerService(@PathVariable id: Long){
        var previousCustomerService = customerServiceModelService.findById(id)

        customerServiceModelService.finalizeCustomerService(customerServiceMapper.putFinalizeRequestToModel(previousCustomerService))
    }

    @GetMapping
    fun findAll(): List<CustomerServiceModel>{
        return customerServiceModelService.findAll()
    }

    @GetMapping("/customer/{id}")
    fun findAllById(@PathVariable id: Long): List<CustomerServiceModel>{
        val customer = customerService.findById(id)
        return customerServiceModelService.findAllById(customer)
    }

    @GetMapping("/statusaberto/{id}")
    fun findByCustomerServiceWithStatusAberto(@PathVariable id: Long): List<CustomerServiceModel>{
        val customer = customerService.findById(id)
        return customerServiceModelService.findByCustomerServiceWithStatusAberto(customer.idCustomer!!)
    }

}