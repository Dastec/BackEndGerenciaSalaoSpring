package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.extensions.toCategoryModel
import br.com.dastec.gerenciasalao.controllers.extensions.toCategoryResponse
import br.com.dastec.gerenciasalao.controllers.extensions.toListCategoryResponse
import br.com.dastec.gerenciasalao.controllers.requests.categories.PostCategoryRequest
import br.com.dastec.gerenciasalao.controllers.requests.categories.PutCategoryRequest
import br.com.dastec.gerenciasalao.controllers.responses.CategoryResponse
import br.com.dastec.gerenciasalao.controllers.responses.MessageResponse
import br.com.dastec.gerenciasalao.models.CategoryModel
import br.com.dastec.gerenciasalao.services.CategoryService
import br.com.dastec.gerenciasalao.utils.SpringUtil
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/category")
class CategoryController(val categoryService: CategoryService, val springUtil: SpringUtil) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCategory(@Valid @RequestBody postCategoryRequest: PostCategoryRequest, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        categoryService.LOGGER.info("Início do método de criação de categoria")
        val salon = springUtil.getSalon(token.split(" ")[1])
        categoryService.createCategory(postCategoryRequest.toCategoryModel(salon))
        return MessageResponse("Categoria ${postCategoryRequest.nameCategory} criada com sucesso")
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody putCategoryRequest: PutCategoryRequest, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        categoryService.LOGGER.info("Início do método de atualização de categoria")
        val salon = springUtil.getSalon(token.split(" ")[1])
        val previousCategory = categoryService.findById(salon, id)
        categoryService.updateCategory(putCategoryRequest.toCategoryModel(previousCategory))
        return MessageResponse("Categoria atualizada com sucesso")
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): MessageResponse {
        categoryService.LOGGER.info("Início do método de exclusão de categoria")
        categoryService.delete(id)
        return MessageResponse("Categoria deletada com sucesso")
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): CategoryResponse {
        val salon = springUtil.getSalon(token.split(" ")[1])
        return categoryService.findById(salon,id).toCategoryResponse()
    }

    @GetMapping("/name/{name}")
    fun findByName(@PathVariable name: String, @RequestHeader(value = "Authorization") token: String): List<CategoryResponse> {
        val salon = springUtil.getSalon(token.split(" ")[1])
        return categoryService.findByNameCategory(salon, name).toListCategoryResponse()
    }

    @GetMapping()
    fun findAll(@RequestHeader(value = "Authorization") token: String): List<CategoryResponse> {
        val salon = springUtil.getSalon(token.split(" ")[1])
        return categoryService.findAll(salon).toListCategoryResponse()
    }
}