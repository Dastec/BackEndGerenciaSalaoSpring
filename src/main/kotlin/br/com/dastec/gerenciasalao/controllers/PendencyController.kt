package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.extensions.toPendencyModel
import br.com.dastec.gerenciasalao.controllers.requests.pendency.PostAddPendencyRequest
import br.com.dastec.gerenciasalao.controllers.requests.pendency.PutFinishPendencyRequest
import br.com.dastec.gerenciasalao.models.PendencyModel
import br.com.dastec.gerenciasalao.services.CustomerServiceModelService
import br.com.dastec.gerenciasalao.services.PendencyService
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
@RequestMapping("api/v1/pendency")
class PendencyController(
    private val pendencyService: PendencyService,
    private val customerServiceModelService: CustomerServiceModelService
    ) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addPendency(@RequestBody postAddPendencyRequest: PostAddPendencyRequest){
        val customerService = customerServiceModelService.findById(postAddPendencyRequest.customerService)

        pendencyService.createPendency(postAddPendencyRequest.toPendencyModel(customerService))
    }

    @PutMapping("/{id}")
    fun updatePendency(@PathVariable id: Long){
        val pendency = pendencyService.findById(id)
        pendencyService.updatePendency(pendency)
    }

    @PutMapping("finalize/{id}")
    fun finaizePendency(@PathVariable id: Long){
        val pendency = pendencyService.findById(id)
        val putFinishPendencyRequest:  PutFinishPendencyRequest = PutFinishPendencyRequest()
        pendencyService.finalizePendency(putFinishPendencyRequest.toPendencyModel(pendency))
    }

    @GetMapping
    fun findAll(): List<PendencyModel>{
        return pendencyService.findAll()
    }

    @GetMapping("/customerservice/{id}")
    fun findByIdCustomomer(@PathVariable id: Long): PendencyModel{
        val customerService = customerServiceModelService.findById(id)
        return pendencyService.findByCustomerService(customerService)
    }



}