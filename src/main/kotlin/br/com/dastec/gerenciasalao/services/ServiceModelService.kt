package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.BadRequestException
import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.CategoryModel
import br.com.dastec.gerenciasalao.models.ServiceModel
import br.com.dastec.gerenciasalao.repositories.ServiceRepository
import org.springframework.stereotype.Service

@Service
class ServiceModelService(private val serviceRepository: ServiceRepository) {

    fun create(serviceModel: ServiceModel){
        serviceRepository.save(serviceModel)
    }

    fun update(serviceModel: ServiceModel){
        serviceRepository.save(serviceModel)
    }

    fun delete(id: Long){
        if (!serviceRepository.existsById(id)){
            NotFoundException(Errors.GS201.message.format(id), Errors.GS201.internalCode)
        }
        serviceRepository.deleteById(id)
    }

    fun findById(id: Long): ServiceModel{
        return serviceRepository.findById(id).orElseThrow {
            NotFoundException(Errors.GS301.message.format(id), Errors.GS301.internalCode)
        }
    }

    fun findByName(name: String): List<ServiceModel>{
        return serviceRepository.findByNameServiceContainingIgnoreCase(name)
    }

    fun findAll(): List<ServiceModel>{
        return serviceRepository.findAll()
    }

    fun findByCategory(category: CategoryModel): List<ServiceModel>{
        return serviceRepository.findByCategory(category)
    }

    fun findByIds(serviceIds: MutableList<Long>): MutableList<ServiceModel>{
        if (serviceIds.isEmpty()){
            throw NotFoundException(Errors.GS302.message, Errors.GS302.internalCode)
        }
        return serviceRepository.findAllById(serviceIds).toMutableList()
    }
}