package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.extensions.toCategoryModel
import br.com.dastec.gerenciasalao.controllers.requests.categories.PostCategoryRequest
import br.com.dastec.gerenciasalao.controllers.requests.categories.PutCategoryRequest
import br.com.dastec.gerenciasalao.controllers.responses.CreateResponse
import br.com.dastec.gerenciasalao.models.CategoryModel
import br.com.dastec.gerenciasalao.services.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/category")
class CategoryController(val categoryService: CategoryService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCategory(@Valid @RequestBody postCategoryRequest: PostCategoryRequest): CreateResponse {
        categoryService.createCategory(postCategoryRequest.toCategoryModel())
        return CreateResponse("Categoria criada com sucesso")
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody putCategoryRequest: PutCategoryRequest): CreateResponse {
        val previousCategory = categoryService.findById(id)
        categoryService.updateCategory(putCategoryRequest.toCategoryModel(previousCategory))
        return CreateResponse("Categoria atualizada com sucesso")
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): CreateResponse {
        categoryService.delete(id)
        return CreateResponse("Categoria deletada com sucesso")
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): CategoryModel {
        return categoryService.findById(id)
    }

    @GetMapping("/name/{name}")
    fun findById(@PathVariable name: String): List<CategoryModel> {
        return categoryService.findByNameCategory(name)
    }

    @GetMapping()
    fun findAll(): List<CategoryModel> {
        return categoryService.findAll()
    }


}