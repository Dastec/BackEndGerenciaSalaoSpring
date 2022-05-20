package br.com.dastec.gerenciasalao.controllers.requests.categories

import com.fasterxml.jackson.annotation.JsonAlias
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PostCategoryRequest(

    @JsonAlias("name_category")
    @field:NotEmpty(message = "O nome da categoria não pode estar vazio!")
    val nameCategory: String
)
