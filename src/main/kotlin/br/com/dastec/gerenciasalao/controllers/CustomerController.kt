package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.extensions.deleteCustomer
import br.com.dastec.gerenciasalao.controllers.extensions.toCustomerModel
import br.com.dastec.gerenciasalao.controllers.mapper.CustomerMapper
import br.com.dastec.gerenciasalao.controllers.requests.customers.PostCustomerModelRequest
import br.com.dastec.gerenciasalao.controllers.requests.customers.PutCustomerModelRequest
import br.com.dastec.gerenciasalao.controllers.responses.CreateResponse
import br.com.dastec.gerenciasalao.controllers.responses.CustomerResponse
import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.security.JwtUtil
import br.com.dastec.gerenciasalao.services.CustomerService
import br.com.dastec.gerenciasalao.services.SalonService
import br.com.dastec.gerenciasalao.utils.SpringUtil
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/customer")
class CustomerController(val customerService: CustomerService, val customerMapper: CustomerMapper, val springUtil: SpringUtil) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody postCustomerRequest: PostCustomerModelRequest, @RequestHeader(value = "Authorization") token: String): CreateResponse {

        customerService.createCustomer(postCustomerRequest.toCustomerModel(springUtil.getSalon(token.split(" ")[1])), postCustomerRequest.phoneNumber)
        return CreateResponse("Cliente criado com sucesso")
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody putCustomerRequest: PutCustomerModelRequest): CreateResponse {
        val customerModel: CustomerModel = customerService.findById(id)
        customerService.updateCustomer(putCustomerRequest.toCustomerModel(customerModel), putCustomerRequest.phoneNumber)
        return CreateResponse("Cliente Atualizada com sucesso")
    }

    @DeleteMapping("/{clientKey}")
    fun delete(@PathVariable clientKey: String): CreateResponse {
        val customerModel: CustomerModel = customerService.findByClientKey(clientKey)
        customerService.deleteCustomer(deleteCustomer(customerModel))

        return CreateResponse("Cliente deletada com sucesso")
    }

    @GetMapping()
    fun findAll(@RequestHeader(value = "Authorization") token: String): List<CustomerResponse> {
        //val salon = salonService.findById(jwtUtil.getUserInformation(token.split(" ")[1]).salonId)
        return customerMapper.toListCustomerResponse(customerService.findAllBySalonModel(springUtil.getSalon(token.split(" ")[1])))
    }

    @GetMapping("{customerId}")
    fun findById(@PathVariable @Valid customerId: Long): CustomerResponse {
        return customerMapper.toCustomerResponse(customerService.findById(customerId))
    }

    @GetMapping("/name/{name}")
    fun findByName(@PathVariable name: String, @RequestHeader(value = "Authorization") token: String): List<CustomerResponse> {
        //val salon = salonService.findById(jwtUtil.getUserInformation(token.split(" ")[1]).salonId)
        return customerMapper.toListCustomerResponse(customerService.findByNameContaining(springUtil.getSalon(token.split(" ")[1]), name.trim()))
    }

    @GetMapping("/status/{status}")
    fun findByStatus(@PathVariable status: String, @RequestHeader(value = "Authorization") token: String): List<CustomerResponse> {
        //val salon = salonService.findById(jwtUtil.getUserInformation(token.split(" ")[1]).salonId)
        return customerMapper.toListCustomerResponse(customerService.findByStatus(springUtil.getSalon(token.split(" ")[1]), status))
    }

    @GetMapping("/clientkey/{clientKey}")
    fun findByClientKey(@PathVariable clientKey: String): CustomerResponse {
        return customerMapper.toCustomerResponse(customerService.findByClientKey(clientKey))
    }
}