package br.com.dastec.gerenciasalao.controllers.requests.pendency

import br.com.dastec.gerenciasalao.validation.annotation.IsCurrency
import com.fasterxml.jackson.annotation.JsonAlias

data class PutPendencyRequest(

    @JsonAlias("value_pendency")
    @IsCurrency
    var valuePendency: Double,
)
