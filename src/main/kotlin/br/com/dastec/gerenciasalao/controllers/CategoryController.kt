package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.extensions.toCategoryModel
import br.com.dastec.gerenciasalao.controllers.requests.categories.PostCategoryRequest
import br.com.dastec.gerenciasalao.controllers.requests.categories.PutCategoryRequest
import br.com.dastec.gerenciasalao.controllers.responses.CreateResponse
import br.com.dastec.gerenciasalao.models.CategoryModel
import br.com.dastec.gerenciasalao.security.JwtUtil
import br.com.dastec.gerenciasalao.services.CategoryService
import br.com.dastec.gerenciasalao.services.SalonService
import br.com.dastec.gerenciasalao.services.UserService
import br.com.dastec.gerenciasalao.utils.SpringUtil
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/category")
class CategoryController(val categoryService: CategoryService, val springUtil: SpringUtil) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCategory(@Valid @RequestBody postCategoryRequest: PostCategoryRequest, @RequestHeader(value = "Authorization") token: String): CreateResponse {
        val salon = springUtil.getSalon(token.split(" ")[1])
        categoryService.createCategory(postCategoryRequest.toCategoryModel(salon))
        return CreateResponse("Categoria criada com sucesso")
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody putCategoryRequest: PutCategoryRequest, @RequestHeader(value = "Authorization") token: String): CreateResponse {
        val salon = springUtil.getSalon(token.split(" ")[1])
        val previousCategory = categoryService.findById(salon, id)
        categoryService.updateCategory(putCategoryRequest.toCategoryModel(previousCategory))
        return CreateResponse("Categoria atualizada com sucesso")
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): CreateResponse {
        categoryService.delete(id)
        return CreateResponse("Categoria deletada com sucesso")
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): CategoryModel {
        val salon = springUtil.getSalon(token.split(" ")[1])
        return categoryService.findById(salon,id)
    }

    @GetMapping("/name/{name}")
    fun findByName(@PathVariable name: String, @RequestHeader(value = "Authorization") token: String): List<CategoryModel> {
        val salon = springUtil.getSalon(token.split(" ")[1])
        return categoryService.findByNameCategory(salon, name)
    }

    @GetMapping()
    fun findAll(@RequestHeader(value = "Authorization") token: String): List<CategoryModel> {
        val salon = springUtil.getSalon(token.split(" ")[1])
        return categoryService.findAll(salon)
    }


}