package br.com.dastec.gerenciasalao.controllers.requests

import br.com.dastec.gerenciasalao.validation.annotation.IsCurrency
import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import javax.validation.constraints.NotNull

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