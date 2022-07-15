package br.com.dastec.gerenciasalao.controllers.requests

import br.com.dastec.gerenciasalao.validation.annotation.IsCurrency
import com.fasterxml.jackson.annotation.JsonAlias
import javax.validation.constraints.NotNull
import java.math.BigDecimal


data class PostServiceRequest(

    @JsonAlias("name_service")
    @field:NotNull(message = "O campo nome da categoria não pode ser null ou vazio!")
    val nameService: String,

    @JsonAlias("id_category")
    @field:NotNull(message = "O campo id da categoria não pode ser null!")
    val idCategory: Long,

    @IsCurrency
    val price: BigDecimal?
)