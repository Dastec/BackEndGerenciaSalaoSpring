package br.com.dastec.gerenciasalao.controllers.requests

import br.com.dastec.gerenciasalao.validation.annotation.IsCurrency
import com.fasterxml.jackson.annotation.JsonAlias
import javax.validation.constraints.NotBlank

data class PutServiceRequest(

    @JsonAlias("name_service")
    @field:NotBlank(message = "O campo nome da categoria não pode ser null ou vazio!")
    val nameService: String?,

    @JsonAlias("id_category")
    @field:NotBlank(message = "O campo id da categoria não pode ser null!")
    val idCategory: Long,

    @IsCurrency
    val price: Double?
)