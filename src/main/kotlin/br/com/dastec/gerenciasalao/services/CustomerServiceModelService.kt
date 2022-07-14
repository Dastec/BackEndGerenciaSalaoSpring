package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.BadRequestException
import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import br.com.dastec.gerenciasalao.repositories.CustomerServiceRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Service
class CustomerServiceModelService(
    private val customerServiceRepository: CustomerServiceRepository,
    private val saleServiceModelService: SaleServiceModelService
) {
    val LOGGER = LoggerFactory.getLogger(javaClass)
    fun startCustomerService(customerServiceModel: CustomerServiceModel) {
        if (customerServiceModel.statusCustomerService == CustomerServiceStatus.FINISHED
            || customerServiceModel.statusCustomerService == CustomerServiceStatus.FINALIZEDPENDING
            || customerServiceModel.statusCustomerService == CustomerServiceStatus.CANCELLED)
        {
            throw BadRequestException(
                Errors.GS510.message.format(customerServiceModel.idCustomerService),
                Errors.GS510.internalCode
            )
            LOGGER.info("Atendimento iniciado com sucesso!")
        }

        if (saleServiceModelService.findByCustomerService(customerServiceModel).isEmpty()) {
            throw BadRequestException(
                Errors.GS508.message.format(customerServiceModel.idCustomerService),
                Errors.GS508.internalCode
            )
        }
        customerServiceRepository.save(customerServiceModel)
        LOGGER.info("Atendimento iniciado com sucesso!")
        LOGGER.info("Final do método de início de atendimento!")
    }

    fun createCustomerService(customerServiceModel: CustomerServiceModel): CustomerServiceModel {
        if (findByCustomerServiceWithStatusCreated(customerServiceModel.beautySalon, customerServiceModel.customer!!.idCustomer!!).isNotEmpty()
            || findByCustomerServiceWithStatusOpen(customerServiceModel.beautySalon, customerServiceModel.customer!!.idCustomer!!).isNotEmpty()
        ) {
            throw BadRequestException(
                Errors.GS103.message.format(customerServiceModel.customer!!.alias),
                Errors.GS103.internalCode
            )
            LOGGER.info("Cliente já tem um atendimento criado ou aberto!")
        }
        return customerServiceRepository.save(customerServiceModel)
        LOGGER.info("Atendimento críado com sucesso!")
        LOGGER.info("Final do método de criação de atendimento!")
    }

    fun updateCustomerService(customerServiceModel: CustomerServiceModel) {
        customerServiceRepository.save(customerServiceModel)
        LOGGER.info("Atendimento atualizado com sucesso!")
        LOGGER.info("Final do método de atualização de atendimento!")
    }

    fun cancelCustomerService(customerServiceModel: CustomerServiceModel) {
        customerServiceRepository.save(customerServiceModel)
        LOGGER.info("Atendimento cancelado com sucesso!")
        LOGGER.info("Final do método de cancelamento de atendimento!")
    }

    fun findByCustomerServiceWithStatusOpen(salon: BeautySalonModel, idCustomerService: Long): List<CustomerServiceModel> {
        return customerServiceRepository.findByCustomerServiceWithStatusOpen(salon, idCustomerService)
    }

    fun findByCustomerServiceWithStatusCreatedOrOpen(salon: BeautySalonModel, customer: CustomerModel): List<CustomerServiceModel> {
        return customerServiceRepository.findByCustomerServiceWithStatusCreatedOrOpen(salon, customer)
    }

    fun findByCustomerServiceWithStatusCreated(salon: BeautySalonModel, idCustomer: Long): List<CustomerServiceModel> {
        return customerServiceRepository.findByCustomerServiceWithStatusCreated(salon, idCustomer)
    }

    fun findByCustomerServiceWithStatusFinalizedPending(salon: BeautySalonModel, idCustomer: Long): List<CustomerServiceModel> {
        return customerServiceRepository.findByCustomerServiceWithStatusFinalizedPending(salon, idCustomer)
    }

    fun finalizeCustomerService(customerServiceModel: CustomerServiceModel): CustomerServiceModel {
        return customerServiceRepository.save(customerServiceModel)
    }

    fun finalizeCustomerServicePendencyStatus(customerServiceModel: CustomerServiceModel) {
        customerServiceRepository.save(customerServiceModel)
    }

    fun findAll(salon: BeautySalonModel, pageable: Pageable): Page<CustomerServiceModel>{
        return customerServiceRepository.findAllByBeautySalon(salon, pageable)
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

    fun findDailyGain(salon: BeautySalonModel): BigDecimal{
        val customerServices = customerServiceRepository.getDailyGain(salon, LocalDate.now())
        return  customerServices.sumOf { it.totalValue!!}
    }

    fun findMonthlyGain(salon: BeautySalonModel): BigDecimal{
        val initialDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth())
        val finalDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth())
        val customerServices = customerServiceRepository.getMonthlyGain(salon, initialDate, finalDate)
        return  customerServices.sumOf { it.totalValue!!}
    }
}