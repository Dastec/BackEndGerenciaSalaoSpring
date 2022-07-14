package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.repositories.SalonRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BeautySalonService(private val salonRepository: SalonRepository) {
    val LOGGER = LoggerFactory.getLogger(javaClass)
    fun registerSalon(salon: BeautySalonModel){
        salonRepository.save(salon)
        LOGGER.info("Salão registrado com sucesso!")
        LOGGER.info("Final do método de registro de salão")
    }

    fun updateSalon(salon: BeautySalonModel): BeautySalonModel{
        return salonRepository.save(salon)
        LOGGER.info("Salão atualizado com sucesso!")
        LOGGER.info("Final do método de atualização de salão")
    }

    fun cancelSalon(salon: BeautySalonModel){
        salonRepository.save(salon)
        LOGGER.info("Salão excluído com sucesso!")
        LOGGER.info("Final do método de exclusão de salão")
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