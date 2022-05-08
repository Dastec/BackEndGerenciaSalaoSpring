package br.com.dastec.gerenciasalao.controllers.requests

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal

data class PutServiceRequest(

    @JsonAlias("name_service")
    val nameService: String?,

    @JsonAlias("id_category")
    val idCategory: Long,

    val price: BigDecimal?
)