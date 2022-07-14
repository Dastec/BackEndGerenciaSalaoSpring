package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.BadRequestException
import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.CategoryModel
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.models.ServiceModel
import br.com.dastec.gerenciasalao.repositories.ServiceRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ServiceModelService(private val serviceRepository: ServiceRepository) {

    val LOGGER = LoggerFactory.getLogger(javaClass)
    fun create(serviceModel: ServiceModel){
        serviceRepository.save(serviceModel)
        LOGGER.info("Serviço criado com sucesso!")
        LOGGER.info("Final do método de criação de serviço!")
    }

    fun update(serviceModel: ServiceModel){
        serviceRepository.save(serviceModel)
        LOGGER.info("Serviço atualizado com sucesso!")
        LOGGER.info("Final do método de atualização de serviço!")
    }

    fun delete(salon: BeautySalonModel, id: Long){
        findById(salon, id)
        serviceRepository.deleteById(id)
        LOGGER.info("Serviço deletado com sucesso!")
        LOGGER.info("Final do método de exclusão de serviço!")
    }

    fun findById(salon: BeautySalonModel, id: Long): ServiceModel{
        return serviceRepository.findByIdAndSalon(salon, id) ?:
            throw NotFoundException(Errors.GS301.message.format(id), Errors.GS301.internalCode)
    }

    fun findByName(salon: BeautySalonModel, name: String): List<ServiceModel>{
        return serviceRepository.findByNameServiceContainingIgnoreCase(salon, name)
    }

    fun findAll(salon: BeautySalonModel): List<ServiceModel>{
        return serviceRepository.findAllByBeautySalon(salon)
    }

    fun findByCategory(salon: BeautySalonModel, category: CategoryModel): List<ServiceModel>{
        return serviceRepository.findByCategory(salon, category)
    }

    fun findByIds(serviceIds: Set<Long>): MutableList<ServiceModel>{
        if (serviceIds.isEmpty()){
            throw NotFoundException(Errors.GS302.message, Errors.GS302.internalCode)
        }
        return serviceRepository.findAllById(serviceIds).toMutableList().ifEmpty { throw BadRequestException(Errors.GS301.message.format(serviceIds), Errors.GS301.internalCode) }
    }
}