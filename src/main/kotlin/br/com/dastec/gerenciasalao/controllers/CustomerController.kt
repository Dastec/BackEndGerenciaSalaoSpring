package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.extensions.deleteCustomer
import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.controllers.extensions.toCustomerModel
import br.com.dastec.gerenciasalao.controllers.requests.customers.PostCustomerModelRequest
import br.com.dastec.gerenciasalao.controllers.requests.customers.PutCustomerModelRequest
import br.com.dastec.gerenciasalao.services.CustomerService
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
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/customers")
class CustomerController(val customerService: CustomerService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid postCustomerRequest: PostCustomerModelRequest){
        customerService.create(postCustomerRequest.toCustomerModel(), postCustomerRequest.phoneNumber)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable @Valid id: Long, @RequestBody putCustomerRequest: PutCustomerModelRequest){
        val customerModel: CustomerModel = customerService.findById(id)
        customerService.update(putCustomerRequest.toCustomerModel(customerModel), putCustomerRequest.phoneNumber)
    }

    @DeleteMapping("/{clientKey}")
    fun delete(@PathVariable clientKey: String){
        val customerModel: CustomerModel = customerService.findByClientKey(clientKey)
        customerService.delete(deleteCustomer(customerModel))
    }

    @GetMapping()
    fun findAll(): List<CustomerModel>{
        return customerService.findAll()
    }

    @GetMapping("{customerId}")
    fun findById(@PathVariable @Valid customerId: Long):CustomerModel{
        return customerService.findById(customerId)
    }

    @GetMapping("/name/{name}")
    fun findByName(@PathVariable name: String): List<CustomerModel> {
        return customerService.findByNameContaining(name.trim())
    }

    @GetMapping("/status/{status}")
    fun findByStatus(@PathVariable status: String):List<CustomerModel>{
        return customerService.findByStatus(status)
    }

    @GetMapping("/clientkey/{clientKey}")
    fun findByClientKey(@PathVariable clientKey: String):CustomerModel{
        return customerService.findByClientKey(clientKey)
    }
}