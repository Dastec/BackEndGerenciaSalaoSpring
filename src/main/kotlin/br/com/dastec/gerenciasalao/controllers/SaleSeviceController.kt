package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.extensions.toSalesServiceModel
import br.com.dastec.gerenciasalao.controllers.requests.PostCreateSaleServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.PutUpdateSaleServiceRequest
import br.com.dastec.gerenciasalao.controllers.responses.MessageResponse
import br.com.dastec.gerenciasalao.models.SaleServiceModel
import br.com.dastec.gerenciasalao.services.CustomerServiceModelService
import br.com.dastec.gerenciasalao.services.SaleServiceModelService
import br.com.dastec.gerenciasalao.services.ServiceModelService
import br.com.dastec.gerenciasalao.utils.SpringUtil
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/saleservice")
class SaleSeviceController(private val saleServiceModelService: SaleServiceModelService,
                           private val customerServiceModelService: CustomerServiceModelService,
                           private val serviceModelService: ServiceModelService,
                           private val springUtil: SpringUtil
                           ) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createSaleService(@RequestBody postSaleServiceRequest: PostCreateSaleServiceRequest, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        saleServiceModelService.LOGGER.info("Início do método de criação de serviço de atendimento!")
        val salon = springUtil.getSalon(token.split(" ")[1])
        val service = serviceModelService.findById(salon, postSaleServiceRequest.service)
        val customerService = customerServiceModelService.findById(salon, postSaleServiceRequest.customerService)

        saleServiceModelService.createSaleService(postSaleServiceRequest.toSalesServiceModel(service, customerService))
        return MessageResponse("Serviço criado com sucesso")
    }

    @PutMapping("/{id}")
    fun updateValue(@PathVariable id: Long, @RequestBody putSaleServiceRequest: PutUpdateSaleServiceRequest, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        saleServiceModelService.LOGGER.info("Início do método de atualização de serviço de atendimento!")
        val salon = springUtil.getSalon(token.split(" ")[1])
        val saleService = saleServiceModelService.findById(salon, id)
        saleServiceModelService.updateValue(putSaleServiceRequest.toSalesServiceModel(saleService))
        return MessageResponse("Serviço atualizado com sucesso")
    }

    @DeleteMapping("/{id}")
    fun deleteSaleService(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        saleServiceModelService.LOGGER.info("Início do método de atualização de serviço de atendimento!")
        val salon = springUtil.getSalon(token.split(" ")[1])
        saleServiceModelService.deleteSaleService(salon, id)
        return MessageResponse("Serviço excluído com sucesso")
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): SaleServiceModel {
        val salon = springUtil.getSalon(token.split(" ")[1])
        return saleServiceModelService.findById(salon, id)
    }

    @GetMapping()
    fun findAll(@RequestHeader(value = "Authorization") token: String): List<SaleServiceModel> {
        val salon = springUtil.getSalon(token.split(" ")[1])
        return saleServiceModelService.findAll(salon)
    }

    @GetMapping("/customerservice/{id}")
    fun findByCustomerService(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): MutableList<SaleServiceModel> {
        val salon = springUtil.getSalon(token.split(" ")[1])
        return saleServiceModelService.findByCustomerService(customerServiceModelService.findById(salon, id))
    }

}