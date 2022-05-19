package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.extensions.deleteCustomer
import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.controllers.extensions.toCustomerModel
import br.com.dastec.gerenciasalao.controllers.mapper.CustomerMapper
import br.com.dastec.gerenciasalao.controllers.requests.customers.PostCustomerModelRequest
import br.com.dastec.gerenciasalao.controllers.requests.customers.PutCustomerModelRequest
import br.com.dastec.gerenciasalao.controllers.responses.CreateResponse
import br.com.dastec.gerenciasalao.controllers.responses.CustomerResponse
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
class CustomerController(val customerService: CustomerService, val customerMapper: CustomerMapper) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid postCustomerRequest: PostCustomerModelRequest): CreateResponse {
        customerService.create(postCustomerRequest.toCustomerModel(), postCustomerRequest.phoneNumber)
        return CreateResponse("Cliente criado com sucesso")
    }

    @PutMapping("/{id}")
    fun update(@PathVariable @Valid id: Long, @RequestBody putCustomerRequest: PutCustomerModelRequest): CreateResponse {
        val customerModel: CustomerModel = customerService.findById(id)
        customerService.update(putCustomerRequest.toCustomerModel(customerModel), putCustomerRequest.phoneNumber)
        return CreateResponse("Clinte Atualizada com sucesso")
    }

    @DeleteMapping("/{clientKey}")
    fun delete(@PathVariable clientKey: String): CreateResponse {
        val customerModel: CustomerModel = customerService.findByClientKey(clientKey)
        customerService.delete(deleteCustomer(customerModel))

        return CreateResponse("Cliente deletada com sucesso")
    }

    @GetMapping()
    fun findAll(): List<CustomerResponse> {
        return customerMapper.toListCustomerResponse(customerService.findAll())
    }

    @GetMapping("{customerId}")
    fun findById(@PathVariable @Valid customerId: Long): CustomerResponse {
        return customerMapper.toCustomerResponse(customerService.findById(customerId))
    }

    @GetMapping("/name/{name}")
    fun findByName(@PathVariable name: String): List<CustomerResponse> {
        return customerMapper.toListCustomerResponse(customerService.findByNameContaining(name.trim()))
    }

    @GetMapping("/status/{status}")
    fun findByStatus(@PathVariable status: String): List<CustomerResponse> {
        return customerMapper.toListCustomerResponse(customerService.findByStatus(status))
    }

    @GetMapping("/clientkey/{clientKey}")
    fun findByClientKey(@PathVariable clientKey: String): CustomerResponse {
        return customerMapper.toCustomerResponse(customerService.findByClientKey(clientKey))
    }
}