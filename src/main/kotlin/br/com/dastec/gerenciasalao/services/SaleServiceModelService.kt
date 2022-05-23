package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.BadRequestException
import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.SaleServiceModel
import br.com.dastec.gerenciasalao.models.ServiceModel
import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import br.com.dastec.gerenciasalao.repositories.SaleServiceRepository
import org.springframework.stereotype.Service

@Service
class SaleServiceModelService(private val saleServiceRepository: SaleServiceRepository) {

    fun createSaleService(saleService: SaleServiceModel){
        if (saleService.customerService!!.statusCustomerService == CustomerServiceStatus.FINISHED || saleService.customerService!!.statusCustomerService == CustomerServiceStatus.FINALIZEDPENDING){
            throw BadRequestException(Errors.GS506.message.format(saleService.customerService.idCustomerService), Errors.GS506.internalCode)
        }

        if (findByCustomerServiceAndService(saleService.customerService, saleService.service).isNotEmpty()){
            throw BadRequestException(Errors.GS507.message.format(saleService.customerService.idCustomerService), Errors.GS507.internalCode)
        }
        saleServiceRepository.save(saleService)
    }

    fun updateValue(saleServiceModel: SaleServiceModel){
        if (saleServiceModel.customerService!!.statusCustomerService == CustomerServiceStatus.FINISHED || saleServiceModel.customerService!!.statusCustomerService == CustomerServiceStatus.FINALIZEDPENDING){
            throw BadRequestException(Errors.GS506.message.format(saleServiceModel.customerService.idCustomerService), Errors.GS506.internalCode)
        }
        saleServiceRepository.save(saleServiceModel)
    }

    fun deleteSaleService(id: Long){
        if (!saleServiceRepository.existsById(id)){
            NotFoundException(Errors.GS201.message.format(id), Errors.GS201.internalCode)
        }
        saleServiceRepository.deleteById(id)
    }

    fun findById(id: Long): SaleServiceModel{
        return saleServiceRepository.findById(id).orElseThrow {
            NotFoundException(Errors.GS301.message.format(id), Errors.GS301.internalCode)
        }
    }

    fun findByName(name: String): List<SaleServiceModel>{
        return saleServiceRepository.findByNameServiceContainingIgnoreCase(name)
    }

    fun findAll(): List<SaleServiceModel>{
        return saleServiceRepository.findAll()
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