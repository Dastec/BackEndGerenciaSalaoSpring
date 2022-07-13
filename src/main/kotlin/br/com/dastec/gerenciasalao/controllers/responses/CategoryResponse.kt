package br.com.dastec.gerenciasalao.controllers.responses

import com.fasterxml.jackson.annotation.JsonAlias
import jdk.jfr.Category

data class CategoryResponse(
    @JsonAlias("id_category")
    val idCategory: Long,

    @JsonAlias("name_category")
    val nameCategory: String
)
