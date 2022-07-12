package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.BadRequestException
import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.repositories.CustomerServiceRepository
import org.springframework.stereotype.Service

@Service
class CustomerServiceModelService(
    private val customerServiceRepository: CustomerServiceRepository,
    private val saleServiceModelService: SaleServiceModelService
) {

    fun startCustomerService(customerServiceModel: CustomerServiceModel) {
        if (findByCustomerServiceWithStatusOpen(customerServiceModel.beautySalon, customerServiceModel.customer!!.idCustomer!!).isNotEmpty()
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
        if (findByCustomerServiceWithStatusCreated(customerServiceModel.beautySalon, customerServiceModel.customer!!.idCustomer!!).isNotEmpty()
            || findByCustomerServiceWithStatusOpen(customerServiceModel.beautySalon, customerServiceModel.customer!!.idCustomer!!).isNotEmpty()
        ) {
            throw BadRequestException(
                Errors.GS103.message.format(customerServiceModel.customer!!.alias),
                Errors.GS103.internalCode
            )
        }
        return customerServiceRepository.save(customerServiceModel)
    }

    fun findByCustomerServiceWithStatusOpen(salon: BeautySalonModel, idCustomerService: Long): List<CustomerServiceModel> {
        return customerServiceRepository.findByCustomerServiceWithStatusOpen(salon, idCustomerService)
    }

    fun findByCustomerServiceWithStatusCreated(salon: BeautySalonModel, idCustomer: Long): List<CustomerServiceModel> {
        return customerServiceRepository.findByCustomerServiceWithStatusCreated(salon, idCustomer)
    }

    fun findByCustomerServiceWithStatusFinalizedPending(salon: BeautySalonModel, idCustomer: Long): List<CustomerServiceModel> {
        return customerServiceRepository.findByCustomerServiceWithStatusFinalizedPending(salon, idCustomer)
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

    fun findAll(salon: BeautySalonModel): List<CustomerServiceModel>{
        return customerServiceRepository.findAllByBeautySalon(salon)
    }

    fun findById(salon: BeautySalonModel, id: Long): CustomerServiceModel {
        return customerServiceRepository.findByIdAndSalon(salon, id) ?:
            throw NotFoundException(Errors.GS501.message.format(id), Errors.GS501.internalCode)

    }

    fun findAllByCustomer(salon: BeautySalonModel, customer: CustomerModel): List<CustomerServiceModel> {
        return customerServiceRepository.findByCustomer(salon, customer)
    }

    fun findAllByIds(salon: BeautySalonModel, ids: Set<Long>): Set<CustomerServiceModel> {
        val customerServicesModel = mutableSetOf<CustomerServiceModel>()
        for (id in ids){
            customerServicesModel.add(findById(salon, id))
        }
        return customerServicesModel
    }
}