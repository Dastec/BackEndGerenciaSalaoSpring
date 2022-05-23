package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.BadRequestException
import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
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

    fun findAll(): List<PendencyModel> {
        return pendencyRepository.findAll()
    }

    fun findByCustomerService(customerService: CustomerServiceModel): PendencyModel {
        return pendencyRepository.findByCustomerService(customerService)
    }

    fun findById(id: Long): PendencyModel {
        return pendencyRepository.findById(id).orElseThrow {
            NotFoundException(Errors.GS601.message.format(id), Errors.GS601.internalCode)
        }
    }

    fun findByCustomerServiceWhereStatusOpen(customerServiceModel: CustomerServiceModel): PendencyModel {
        return pendencyRepository.findByCustomerServiceWhereStatusAberto(customerServiceModel.idCustomerService!!)
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
}