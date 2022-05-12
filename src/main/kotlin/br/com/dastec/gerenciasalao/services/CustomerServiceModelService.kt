package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.IllegalStateException
import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import br.com.dastec.gerenciasalao.repositories.CustomerServiceRepository
import org.springframework.stereotype.Service

@Service
class CustomerServiceModelService(private val customerServiceRepository: CustomerServiceRepository) {

    fun startCustomerService(customerServiceModel: CustomerServiceModel){
        val customerServiceWithStatusOpen = findByCustomerServiceWithStatusAberto(customerServiceModel.customer.idCustomer!!)
        if (!customerServiceWithStatusOpen.isEmpty()){
            throw IllegalStateException(Errors.GS102.message.format(customerServiceModel.customer.alias), Errors.GS102.internalCode)
        }
        customerServiceRepository.save(customerServiceModel)
    }

    fun findByCustomerServiceWithStatusAberto(idCustomer: Long): List<CustomerServiceModel>{
        return customerServiceRepository.findByCustomerServiceWithStatusAberto(idCustomer)
    }

    fun finalizeCustomerService(customerServiceModel: CustomerServiceModel){
        if (customerServiceModel.statusCustomerService == CustomerServiceStatus.FINALIZADOCOMPENDENCIA  || customerServiceModel.statusCustomerService == CustomerServiceStatus.FINALIZADO){
            throw IllegalStateException(Errors.GS503.message.format(customerServiceModel.idCustomerService), Errors.GS503.internalCode)
        }
        customerServiceRepository.save(customerServiceModel)
    }

    fun finalizeCustomerServiceWithPendency(customerServiceModel: CustomerServiceModel){
        if (customerServiceModel.statusCustomerService == CustomerServiceStatus.FINALIZADOCOMPENDENCIA  || customerServiceModel.statusCustomerService == CustomerServiceStatus.FINALIZADO){
            throw IllegalStateException(Errors.GS504.message.format(customerServiceModel.idCustomerService), Errors.GS504.internalCode)
        }
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

    fun findAllById(customer: CustomerModel):List<CustomerServiceModel>{
        return customerServiceRepository.findByCustomer(customer)
    }
}