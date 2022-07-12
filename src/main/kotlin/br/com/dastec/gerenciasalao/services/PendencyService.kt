package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.BadRequestException
import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.PendencyModel
import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import br.com.dastec.gerenciasalao.repositories.PendencyRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PendencyService(
    private val pendencyRepository: PendencyRepository,
    private val customerServiceModelService: CustomerServiceModelService,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun createPendency(pendency: PendencyModel) {
        pendencyRepository.save(pendency)
    }

    fun updatePendency(pendency: PendencyModel) {
        pendencyRepository.save(pendency)
    }

    fun finalizePendency(pendency: PendencyModel) {
        pendencyRepository.save(pendency)
    }

    fun findAll(salon: BeautySalonModel): List<PendencyModel> {
        return pendencyRepository.findAllByBeautySalon(salon)
    }

    fun findByCustomerService(customerService: CustomerServiceModel): PendencyModel {
        return pendencyRepository.findByCustomerService(customerService)
    }

    fun findById(salon: BeautySalonModel, id: Long): PendencyModel {
        return pendencyRepository.findByIdAndSalon(salon, id) ?:
            throw NotFoundException(Errors.GS601.message.format(id), Errors.GS601.internalCode)

    }

    fun findByCustomerServiceWhereStatusOpen(customerServiceModel: CustomerServiceModel): PendencyModel {
        return pendencyRepository.findByCustomerServiceWhereStatusOpen(customerServiceModel)
    }

    fun findAllByCustomerService(customerServices: Set<CustomerServiceModel>): MutableList<PendencyModel> {
        val pendencies = mutableListOf<PendencyModel>()
        for (customerService in customerServices) {
            if (customerService.statusCustomerService != CustomerServiceStatus.FINALIZEDPENDING) {
                throw BadRequestException(
                    Errors.GS504.message.format(customerService.idCustomerService),
                    Errors.GS504.internalCode
                )
            }
            pendencies.add(findByCustomerServiceWhereStatusOpen(customerService))
        }
        return pendencies
    }

    fun findByCustomer(customer: CustomerModel): MutableList<PendencyModel> {
        return pendencyRepository.findByCustomer(customer)
    }
}