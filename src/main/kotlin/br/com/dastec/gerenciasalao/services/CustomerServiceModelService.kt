package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.repositories.CustomerServiceRepository
import org.springframework.stereotype.Service

@Service
class CustomerServiceModelService(private val customerServiceRepository: CustomerServiceRepository) {

    fun startCustomerService(customerServiceModel: CustomerServiceModel){
        customerServiceRepository.save(customerServiceModel)
    }

    fun update(customerServiceModel: CustomerServiceModel){
        customerServiceRepository.save(customerServiceModel)
    }

    fun findAll():List<CustomerServiceModel>{
        return customerServiceRepository.findAll()
    }

    fun findById(id: Long):CustomerServiceModel{
        return customerServiceRepository.findById(id).orElseThrow {
            NotFoundException(Errors.GS501.message.format(id), Errors.GS501.internalCode)
        }
    }
}