package br.com.dastec.gerenciasalao.controllers.requests

import br.com.dastec.gerenciasalao.validation.annotation.IsCurrency
import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class PostCreateSaleServiceRequest(

    @JsonAlias("customer_service_id")
    @field:NotBlank(message = "O campo id do atendimento não pode ser null!")
    val customerService: Long,

    @JsonAlias("service_id")
    @field:NotBlank(message = "O campo id do serviço não pode ser null!")
    val service: Long,


    @IsCurrency
    @field:NotNull(message = "O preço não pode ser null!")
    val price: BigDecimal
)