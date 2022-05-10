package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.extensions.toCustomerService
import br.com.dastec.gerenciasalao.controllers.mapper.CustomerServiceMapper
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PostStartCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PutFinalizeCustomerServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.customerservice.PutUpdateCustomerServiceRequest
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.PendencyModel
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
    private val serviceModelService: ServiceModelService,
    private val customerService: CustomerService,
    private val paymentService: PaymentService,
    private val pendencyService: PendencyService,

    private val customerServiceMapper: CustomerServiceMapper
    ) {

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    fun startCustomerService(@RequestBody postStartCustomerServiceRequest: PostStartCustomerServiceRequest){
//        val customer = customerService.findById(postStartCustomerServiceRequest.customer)
//
//        var services = serviceModelService.findByIds(postStartCustomerServiceRequest.services)
//
//        customerServiceModelService.startCustomerService(postStartCustomerServiceRequest.toCustomerService(services, customer))
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun startCustomerService(@RequestBody postStartCustomerServiceRequest: PostStartCustomerServiceRequest){
        customerServiceModelService.startCustomerService(customerServiceMapper.postStartRequestToModel(postStartCustomerServiceRequest))
    }

//    @PutMapping("/{id}")
//    fun update(@PathVariable id: Long, @RequestBody putStartCustomerServiceRequest: PutUpdateCustomerServiceRequest){
//        val previuoCustomerService = customerServiceModelService.findById(id)
//        val customer = customerService.findById(putStartCustomerServiceRequest.customer)
//        var services = serviceModelService.findByIds(putStartCustomerServiceRequest.services)
//
//        customerServiceModelService.update(putStartCustomerServiceRequest.toCustomerService(previuoCustomerService, services, customer))
//    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody putStartCustomerServiceRequest: PutUpdateCustomerServiceRequest){
        val previuoCustomerService = customerServiceModelService.findById(id)

        customerServiceModelService.update(customerServiceMapper.putUpdateRequestToModel(putStartCustomerServiceRequest, previuoCustomerService))
    }


//    @PutMapping("finalize/{id}")
//    fun finalizeCustomerService(@PathVariable id: Long, @RequestBody putFinalizeCustomerServiceRequest: PutFinalizeCustomerServiceRequest){
//        val previuoCustomerService = customerServiceModelService.findById(id)
//
//        customerServiceModelService.update(customerServiceMapper.putFinalizeRequestToModel(previuoCustomerService))
//    }

    @PutMapping("finalize/{id}")
    fun finalizeCustomerService(@PathVariable id: Long){
        var previuoCustomerService = customerServiceModelService.findById(id)

        customerServiceModelService.update(customerServiceMapper.putFinalizeRequestToModel(previuoCustomerService))
    }

    @GetMapping
    fun findAll(): List<CustomerServiceModel>{
        return customerServiceModelService.findAll()
    }

}