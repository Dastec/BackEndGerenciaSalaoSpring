package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.extensions.toListServiceResponse
import br.com.dastec.gerenciasalao.controllers.extensions.toServiceModel
import br.com.dastec.gerenciasalao.controllers.extensions.toServiceResponse
import br.com.dastec.gerenciasalao.controllers.requests.PostServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.PutServiceRequest
import br.com.dastec.gerenciasalao.controllers.responses.MessageResponse
import br.com.dastec.gerenciasalao.controllers.responses.ServiceResponse
import br.com.dastec.gerenciasalao.models.ServiceModel
import br.com.dastec.gerenciasalao.services.CategoryService
import br.com.dastec.gerenciasalao.services.ServiceModelService
import br.com.dastec.gerenciasalao.utils.SpringUtil
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/service")
class SeviceController(val serviceModelService: ServiceModelService, val categoryService: CategoryService, val springUtil: SpringUtil) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody postServiceRequest: PostServiceRequest, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val category = categoryService.findById(salon, postServiceRequest.idCategory)
        serviceModelService.create(postServiceRequest.toServiceModel(category, springUtil.getSalon(token.split(" ")[1])))

        return MessageResponse("Serviço ${postServiceRequest.nameService} criado com sucesso")
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody putServiceRequest: PutServiceRequest, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val category = categoryService.findById(salon, putServiceRequest.idCategory)
        val service = serviceModelService.findById(salon, id)

        serviceModelService.update(putServiceRequest.toServiceModel(service, category))

        return MessageResponse("Serviço atualizado com sucesso")
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): ServiceResponse {
        val salon = springUtil.getSalon(token.split(" ")[1])
        return serviceModelService.findById(salon, id).toServiceResponse()
    }

    @GetMapping()
    fun findAll(@RequestHeader(value = "Authorization") token: String): List<ServiceResponse> {
        val salon = springUtil.getSalon(token.split(" ")[1])
        return serviceModelService.findAll(salon).toListServiceResponse()
    }

    @GetMapping("/category/{id}")
    fun findByCategory(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): List<ServiceResponse> {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val category = categoryService.findById(salon, id)
        return serviceModelService.findByCategory(salon, category).toListServiceResponse()
    }
}