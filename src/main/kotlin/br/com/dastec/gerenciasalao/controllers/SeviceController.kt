package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.extensions.toServiceModel
import br.com.dastec.gerenciasalao.controllers.requests.PostServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.PutServiceRequest
import br.com.dastec.gerenciasalao.controllers.responses.CreateResponse
import br.com.dastec.gerenciasalao.models.ServiceModel
import br.com.dastec.gerenciasalao.security.JwtUtil
import br.com.dastec.gerenciasalao.services.CategoryService
import br.com.dastec.gerenciasalao.services.SalonService
import br.com.dastec.gerenciasalao.services.ServiceModelService
import br.com.dastec.gerenciasalao.services.UserService
import br.com.dastec.gerenciasalao.utils.SpringUtil
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/service")
class SeviceController(val serviceModelService: ServiceModelService, val categoryService: CategoryService, val springUtil: SpringUtil) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody postServiceRequest: PostServiceRequest, @RequestHeader(value = "Authorization") token: String): CreateResponse {
        val category = categoryService.findById(postServiceRequest.idCategory)
        serviceModelService.create(postServiceRequest.toServiceModel(category, springUtil.getSalon(token.split(" ")[1])))

        return CreateResponse("Serviço criado com sucesso")
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody putServiceRequest: PutServiceRequest): CreateResponse {
        val category = categoryService.findById(putServiceRequest.idCategory)
        val service = serviceModelService.findById(id)

        serviceModelService.update(putServiceRequest.toServiceModel(service, category))

        return CreateResponse("Serviço atualizado com sucesso")
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ServiceModel {
        return serviceModelService.findById(id)
    }

    @GetMapping()
    fun findAll(@RequestHeader(value = "Authorization") token: String): List<ServiceModel> {
        return serviceModelService.findAll(springUtil.getSalon(token.split(" ")[1]))
    }

    @GetMapping("/category/{id}")
    fun findByCategory(@PathVariable id: Long): List<ServiceModel> {
        val category = categoryService.findById(id)
        return serviceModelService.findByCategory(category)
    }
}