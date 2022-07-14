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
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PendencyService(
    private val pendencyRepository: PendencyRepository,
    private val customerServiceModelService: CustomerServiceModelService,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    val LOGGER = LoggerFactory.getLogger(javaClass)

    fun createPendency(pendency: PendencyModel) {
        pendencyRepository.save(pendency)
        LOGGER.info("Pendência criada com sucesso!")
        LOGGER.info("Final do método de criação de pendência!")
    }

    fun updatePendency(pendency: PendencyModel) {
        pendencyRepository.save(pendency)
        LOGGER.info("Pendência atualizada com sucesso!")
        LOGGER.info("Final do método de atualização de pendência!")
    }

    fun finalizePendency(pendency: PendencyModel) {
        pendencyRepository.save(pendency)
        LOGGER.info("Pendência finalizada com sucesso!")
        LOGGER.info("Final do método de finalização de pendência!")
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