package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.BadRequestException
import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.SaleServiceModel
import br.com.dastec.gerenciasalao.models.ServiceModel
import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import br.com.dastec.gerenciasalao.repositories.SaleServiceRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SaleServiceModelService(private val saleServiceRepository: SaleServiceRepository) {
    val LOGGER = LoggerFactory.getLogger(javaClass)
    fun createSaleService(saleService: SaleServiceModel){
        if (saleService.customerService!!.statusCustomerService == CustomerServiceStatus.FINISHED || saleService.customerService!!.statusCustomerService == CustomerServiceStatus.FINALIZEDPENDING){
            throw BadRequestException(Errors.GS506.message.format(saleService.customerService.idCustomerService), Errors.GS506.internalCode)
            LOGGER.info("Atendimento já está finalizado!")
            LOGGER.info("Final do método de criação de serviço de atendimento!")
        }

        if (findByCustomerServiceAndService(saleService.customerService, saleService.service).isNotEmpty()){
            throw BadRequestException(Errors.GS507.message.format(saleService.customerService.idCustomerService), Errors.GS507.internalCode)
            LOGGER.info("Não é possível adicionar duas vezes o mesmo serviço dentro de um atendimento!")
            LOGGER.info("Final do método de criação de serviço de atendimento!")
        }
        saleServiceRepository.save(saleService)
        LOGGER.info("Serviço de atendimento criado com sucesso!")
        LOGGER.info("Final do método de criação de serviço de atendimento!")
    }

    fun updateValue(saleServiceModel: SaleServiceModel){
        if (saleServiceModel.customerService!!.statusCustomerService == CustomerServiceStatus.FINISHED || saleServiceModel.customerService!!.statusCustomerService == CustomerServiceStatus.FINALIZEDPENDING){
            throw BadRequestException(Errors.GS506.message.format(saleServiceModel.customerService.idCustomerService), Errors.GS506.internalCode)
            LOGGER.info("Atendimento já está finalizado!!")
            LOGGER.info("Final do método de criação de serviço de atendimento!")
        }
        saleServiceRepository.save(saleServiceModel)
        LOGGER.info("Serviço de atendimento atualizado com sucesso!")
        LOGGER.info("Final do método de atualização de serviço de atendimento!")
    }

    fun deleteSaleService(salon: BeautySalonModel, id: Long){
        findById(salon, id)
        saleServiceRepository.deleteById(id)
        LOGGER.info("Serviço de atendimento deletado com sucesso!")
        LOGGER.info("Final do método de exclusão de serviço de atendimento!")
    }

    fun findById(salon: BeautySalonModel, id: Long): SaleServiceModel{
        return saleServiceRepository.findByIdAndSalon(salon, id) ?:
            throw NotFoundException(Errors.GS301.message.format(id), Errors.GS301.internalCode)

    }

    fun findByName(salon: BeautySalonModel, name: String): List<SaleServiceModel>{
        return saleServiceRepository.findByNameServiceContainingIgnoreCase(salon, name)
    }

    fun findAll(salon: BeautySalonModel): List<SaleServiceModel>{
        return saleServiceRepository.findAllByBeautySalon(salon)
    }

    fun findByIds(serviceIds: Set<Long>): MutableList<SaleServiceModel>{
        if (serviceIds.isEmpty()){
            throw NotFoundException(Errors.GS302.message, Errors.GS302.internalCode)
        }
        return saleServiceRepository.findAllById(serviceIds).toMutableList().ifEmpty { throw BadRequestException(Errors.GS301.message.format(serviceIds), Errors.GS301.internalCode) }
    }

    fun findByCustomerService(customerService: CustomerServiceModel): MutableList<SaleServiceModel>{
        return saleServiceRepository.findByCustomerService(customerService)
    }

    fun findByCustomerServiceAndService(customerService: CustomerServiceModel, service: ServiceModel): MutableList<SaleServiceModel>{
        return saleServiceRepository.findByCustomerServiceAndService(customerService.idCustomerService!!, service.idService!!)
    }

}