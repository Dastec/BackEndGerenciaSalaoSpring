package br.com.dastec.gerenciasalao.controllers.responses

import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.ServiceModel
import com.fasterxml.jackson.annotation.JsonAlias
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

data class SaleServiceResponse(
    val idSaleService: Long,

    @JsonAlias("service_id")
    val service: ServiceModel,

    val price: Double
)
