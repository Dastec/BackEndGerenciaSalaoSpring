package br.com.dastec.gerenciasalao.controllers.requests.pendency

import br.com.dastec.gerenciasalao.validation.annotation.IsCurrency
import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import javax.validation.constraints.NotNull

data class PostAddPendencyRequest(
    @JsonAlias("customer_service_id")
    @field:NotNull(message = "O campo id do atendimento não pode ser null!")
    var customerService: Long,

    @JsonAlias("value_pendency")
    @IsCurrency
    var valuePendency: BigDecimal
)
