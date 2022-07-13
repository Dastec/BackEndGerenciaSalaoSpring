package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.repositories.SalonRepository
import org.springframework.stereotype.Service

@Service
class SalonService(private val salonRepository: SalonRepository) {

    fun registerSalon(salon: BeautySalonModel){
        salonRepository.save(salon)
    }

    fun updateSalon(salon: BeautySalonModel): BeautySalonModel{
        return salonRepository.save(salon)
    }

    fun cancelSalon(salon: BeautySalonModel){
        salonRepository.save(salon)
    }

    fun findById(id: Long): BeautySalonModel{
        return salonRepository.findById(id).orElseThrow {
            throw NotFoundException(Errors.GS101.message.format(id), Errors.GS101.internalCode)
        }
    }

    fun findAll():List<BeautySalonModel>{
        return salonRepository.findAll()
    }

    fun findByFiscalIdentification(fiscalIdentification: String): BeautySalonModel?{
        return salonRepository.findByFiscalIdentification(fiscalIdentification)
    }

}