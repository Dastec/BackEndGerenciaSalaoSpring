package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.models.PendencyModel
import br.com.dastec.gerenciasalao.services.CustomerService
import br.com.dastec.gerenciasalao.services.CustomerServiceModelService
import br.com.dastec.gerenciasalao.services.PendencyService
import br.com.dastec.gerenciasalao.utils.SpringUtil
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/pendency")
class PendencyController(
    private val pendencyService: PendencyService,
    private val customerServiceModelService: CustomerServiceModelService,
    private val springUtil: SpringUtil,
    private val customerService: CustomerService
) {

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    fun addPendency(@Valid @RequestBody postAddPendencyRequest: PostAddPendencyRequest, @RequestHeader(value = "Authorization") token: String): CreateResponse {
//        val salon = springUtil.getSalon(token.split(" ")[1])
//        val customerService = customerServiceModelService.findById(salon, postAddPendencyRequest.customerService)
//        pendencyService.createPendency(postAddPendencyRequest.toPendencyModel(customerService))
//
//        return CreateResponse("Pendência incluída com sucesso")
//    }

//    @PutMapping("/{id}")
//    fun updatePendency(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): CreateResponse {
//        val salon = springUtil.getSalon(token.split(" ")[1])
//        val pendency = pendencyService.findById(salon, id)
//        pendencyService.updatePendency(pendency)
//
//        return CreateResponse("Pendência atualizada com sucesso")
//    }

//    @PutMapping("finalize/{id}")
//    fun finalizePendency(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): CreateResponse {
//        val salon = springUtil.getSalon(token.split(" ")[1])
//        val pendency = pendencyService.findById(salon, id)
//        val putFinishPendencyRequest: PutFinishPendencyRequest = PutFinishPendencyRequest()
//        pendencyService.finalizePendency(putFinishPendencyRequest.toPendencyModel(pendency))
//
//        return CreateResponse("Pendência finalizada com sucesso")
//    }

    @GetMapping
    fun findAll(@RequestHeader(value = "Authorization") token: String): List<PendencyModel> {
        val salon = springUtil.getSalon(token.split(" ")[1])
        return pendencyService.findAll(salon)
    }

    @GetMapping("/customerservice/{id}")
    fun findByIdCustomerService(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): PendencyModel {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val customerService = customerServiceModelService.findById(salon, id)
        return pendencyService.findByCustomerService(customerService)
    }

    @GetMapping("/customer/{id}")
    fun findByIdCustomer(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): MutableList<PendencyModel> {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val customer = customerService.findByIdAndSalon(salon, id)
        return pendencyService.findByCustomer(customer)
    }
}
