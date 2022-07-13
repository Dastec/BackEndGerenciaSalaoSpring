package br.com.dastec.gerenciasalao.controllers.responses

import br.com.dastec.gerenciasalao.models.enums.PendencyStatus
import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal

data class PendencyResponse(
    @JsonAlias("id_pendency")
    val idPendencyModel: Long,

    @JsonAlias("value_pendency")
    val valuePendency: BigDecimal,

    val status: PendencyStatus
    )
