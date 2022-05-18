package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.extensions.toServiceModel
import br.com.dastec.gerenciasalao.controllers.requests.PostServiceRequest
import br.com.dastec.gerenciasalao.controllers.requests.PutServiceRequest
import br.com.dastec.gerenciasalao.models.CategoryModel
import br.com.dastec.gerenciasalao.models.ServiceModel
import br.com.dastec.gerenciasalao.services.CategoryService
import br.com.dastec.gerenciasalao.services.ServiceModelService
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
@RequestMapping("/api/v1/service")
class SeviceController(val serviceModelService: ServiceModelService, val categoryService: CategoryService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody postServiceRequest: PostServiceRequest) {
        val category = categoryService.findById(postServiceRequest.idCategory)
        serviceModelService.create(postServiceRequest.toServiceModel(category))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody putServiceRequest: PutServiceRequest) {
        val category = categoryService.findById(putServiceRequest.idCategory)
        val service = serviceModelService.findById(id)

        serviceModelService.update(putServiceRequest.toServiceModel(service, category))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ServiceModel {
        return serviceModelService.findById(id)
    }

    @GetMapping()
    fun findAll(): List<ServiceModel> {
        return serviceModelService.findAll()
    }

    @GetMapping("/category/{id}")
    fun findByCategory(@PathVariable id: Long): List<ServiceModel> {
        val category = categoryService.findById(id)
        return serviceModelService.findByCategory(category)
    }
}