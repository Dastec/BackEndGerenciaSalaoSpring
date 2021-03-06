package br.com.dastec.gerenciasalao.controllers.requests.categories

import com.fasterxml.jackson.annotation.JsonAlias
import javax.validation.constraints.NotBlank


data class PostCategoryRequest(

    @JsonAlias("name_category")
    @field:NotBlank(message = "O campo nome da categoria não pode estar vazio!")
    val nameCategory: String
)

