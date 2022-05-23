package br.com.dastec.gerenciasalao.controllers.mapper

import br.com.dastec.gerenciasalao.controllers.responses.PendencyResponse
import br.com.dastec.gerenciasalao.models.PendencyModel
import org.springframework.stereotype.Component

@Component
class PendencyMapper {

    fun toPendencyResponse(pendency: PendencyModel): PendencyResponse{
        return PendencyResponse(
            idPendencyModel = pendency.idPendencyModel!!,
            valuePendency = pendency.valuePendency,
            status = pendency.status
        )
    }
}