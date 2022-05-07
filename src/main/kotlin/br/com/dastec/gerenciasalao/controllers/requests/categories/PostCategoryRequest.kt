package br.com.dastec.gerenciasalao.controllers.requests.categories

import com.fasterxml.jackson.annotation.JsonAlias
import javax.validation.constraints.NotEmpty

data class PostCategoryRequest(

    @JsonAlias("name_category")
    @field:NotEmpty(message = "O nome da categoria não pôde estar vazio!")
    val nameCategory: String
)
