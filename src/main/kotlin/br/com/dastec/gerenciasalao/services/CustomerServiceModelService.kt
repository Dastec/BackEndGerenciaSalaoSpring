package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.BadRequestException
import br.com.dastec.gerenciasalao.exceptions.IllegalStateException
import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.repositories.CustomerServiceRepository
import org.springframework.stereotype.Service

@Service
class CustomerServiceModelService(
    private val customerServiceRepository: CustomerServiceRepository,
    val saleServiceModelService: SaleServiceModelService
) {

    fun startCustomerService(customerServiceModel: CustomerServiceModel) {
        if (findByCustomerServiceWithStatusOpen(customerServiceModel.customer!!.idCustomer!!).isNotEmpty()
        ) {
            throw BadRequestException(
                Errors.GS102.message.format(customerServiceModel.customer!!.alias),
                Errors.GS102.internalCode
            )
        }

        if (saleServiceModelService.findByCustomerService(customerServiceModel).isEmpty()) {
            throw BadRequestException(
                Errors.GS508.message.format(customerServiceModel.idCustomerService),
                Errors.GS508.internalCode
            )
        }

        customerServiceRepository.save(customerServiceModel)
    }

    fun createCustomerService(customerServiceModel: CustomerServiceModel): CustomerServiceModel {
        if (findByCustomerServiceWithStatusCreated(customerServiceModel.customer!!.idCustomer!!).isNotEmpty() || findByCustomerServiceWithStatusOpen(
                customerServiceModel.customer!!.idCustomer!!
            ).isNotEmpty()
        ) {
            throw BadRequestException(
                Errors.GS103.message.format(customerServiceModel.customer!!.alias),
                Errors.GS103.internalCode
            )
        }
        return customerServiceRepository.save(customerServiceModel)
    }

    fun findByCustomerServiceWithStatusOpen(idCustomerService: Long): List<CustomerServiceModel> {
        return customerServiceRepository.findByCustomerServiceWithStatusOpen(idCustomerService)
    }

    fun findByCustomerServiceWithStatusCreated(idCustomer: Long): List<CustomerServiceModel> {
        return customerServiceRepository.findByCustomerServiceWithStatusCreated(idCustomer)
    }

    fun findByCustomerServiceWithStatusFinalizedPending(idCustomer: Long): List<CustomerServiceModel> {
        return customerServiceRepository.findByCustomerServiceWithStatusFinalizedPending(idCustomer)
    }


    fun updateCustomerService(customerServiceModel: CustomerServiceModel) {
        customerServiceRepository.save(customerServiceModel)
    }

    fun cancelCustomerService(customerServiceModel: CustomerServiceModel) {
        customerServiceRepository.save(customerServiceModel)
    }

    fun finalizeCustomerService(customerServiceModel: CustomerServiceModel): CustomerServiceModel {
        return customerServiceRepository.save(customerServiceModel)
    }

    fun finalizeCustomerServicePendencyStatus(customerServiceModel: CustomerServiceModel) {
        customerServiceRepository.save(customerServiceModel)
    }

    fun findAll(): List<CustomerServiceModel> {
        return customerServiceRepository.findAll()
    }

    fun findById(id: Long): CustomerServiceModel {
        return customerServiceRepository.findById(id).orElseThrow {
            NotFoundException(Errors.GS501.message.format(id), Errors.GS501.internalCode)
        }
    }

    fun findAllByCustomer(customer: CustomerModel): List<CustomerServiceModel> {
        return customerServiceRepository.findByCustomer(customer)
    }

    fun findAllByIds(ids: Set<Long>): Set<CustomerServiceModel> {
        return customerServiceRepository.findAllById(ids).toSet()
    }
}