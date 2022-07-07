package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.extensions.toSalesServiceModel
import br.com.dastec.gerenciasalao.controllers.requests.PostCreateSaleServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.PutUpdateSaleServiceRequest
import br.com.dastec.gerenciasalao.controllers.responses.CreateResponse
import br.com.dastec.gerenciasalao.models.SaleServiceModel
import br.com.dastec.gerenciasalao.services.CustomerServiceModelService
import br.com.dastec.gerenciasalao.services.SaleServiceModelService
import br.com.dastec.gerenciasalao.services.ServiceModelService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/saleservice")
class SaleSeviceController(val saleServiceModelService: SaleServiceModelService,
                           val customerServiceModelService: CustomerServiceModelService,
                           val serviceModelService: ServiceModelService
                           ) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createSaleService(@RequestBody postSaleServiceRequest: PostCreateSaleServiceRequest): CreateResponse {
        val service = serviceModelService.findById(postSaleServiceRequest.service)
        val customerService = customerServiceModelService.findById(postSaleServiceRequest.customerService)

        saleServiceModelService.createSaleService(postSaleServiceRequest.toSalesServiceModel(service, customerService))
        return CreateResponse("Serviço criado com sucesso")
    }

    @PutMapping("/{id}")
    fun updateValue(@PathVariable id: Long, @RequestBody putSaleServiceRequest: PutUpdateSaleServiceRequest): CreateResponse {
        val saleService = saleServiceModelService.findById(id)
        saleServiceModelService.updateValue(putSaleServiceRequest.toSalesServiceModel(saleService))
        return CreateResponse("Serviço atualizado com sucesso")
    }

    @DeleteMapping("/{id}")
    fun deleteSaleService(@PathVariable id: Long): CreateResponse {
        saleServiceModelService.deleteSaleService(id)
        return CreateResponse("Serviço excluído com sucesso")
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): SaleServiceModel {
        return saleServiceModelService.findById(id)
    }

    @GetMapping()
    fun findAll(): List<SaleServiceModel> {
        return saleServiceModelService.findAll()
    }

    @GetMapping("/customerservice/{id}")
    fun findByCustomerService(@PathVariable id: Long): MutableList<SaleServiceModel> {
        return saleServiceModelService.findByCustomerService(customerServiceModelService.findById(id))
    }

}