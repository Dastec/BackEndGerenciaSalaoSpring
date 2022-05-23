package br.com.dastec.gerenciasalao.controllers.responses

import br.com.dastec.gerenciasalao.models.enums.PendencyStatus
import com.fasterxml.jackson.annotation.JsonAlias

data class PendencyResponse(
    @JsonAlias("id_pendency")
    var idPendencyModel: Long,

    @JsonAlias("value_pendency")
    var valuePendency: Double,

    var status: PendencyStatus
    )
