package br.com.dastec.gerenciasalao.controllers.responses

import br.com.dastec.gerenciasalao.models.ServiceModel
import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal

data class SaleServiceResponse(
    val idSaleService: Long,

    @JsonAlias("service_id")
    val service: ServiceResponse,

    val price: BigDecimal
)
