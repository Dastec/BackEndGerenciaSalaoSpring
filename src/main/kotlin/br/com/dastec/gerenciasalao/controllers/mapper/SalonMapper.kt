package br.com.dastec.gerenciasalao.controllers.mapper

import br.com.dastec.gerenciasalao.controllers.requests.salon.RegisterSalonRequest
import br.com.dastec.gerenciasalao.controllers.requests.salon.UpdateSalonRequest
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.models.enums.SalonStatus
import br.com.dastec.gerenciasalao.services.SalonService
import org.springframework.stereotype.Component

@Component
class SalonMapper(
    private val salonService: SalonService
) {

    fun registerSalonToSalonModel(registerSalonRequest: RegisterSalonRequest):BeautySalonModel{
        return BeautySalonModel(
            name = registerSalonRequest.name,
            fiscalIdentification = registerSalonRequest.fiscalIdentification,
        )
    }

    fun updateSalonToSalonModel(salon: BeautySalonModel, updateSalonRequest: UpdateSalonRequest):BeautySalonModel{
        return BeautySalonModel(
            idSalon = salon.idSalon,
            name = updateSalonRequest.name,
            fiscalIdentification = updateSalonRequest.fiscalIdentification,
            createdAt = salon.createdAt,
            status = salon.status,
        )
    }

    fun cancelSalonToSalonModel(salon: BeautySalonModel):BeautySalonModel{
        return BeautySalonModel(
            idSalon = salon.idSalon,
            name = salon.name,
            fiscalIdentification = salon.fiscalIdentification,
            createdAt = salon.createdAt,
            status = SalonStatus.CANCELED
        )
    }
}