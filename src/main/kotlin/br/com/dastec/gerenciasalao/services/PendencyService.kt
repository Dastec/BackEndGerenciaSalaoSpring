package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.PendencyModel
import br.com.dastec.gerenciasalao.repositories.PendencyRepository
import org.springframework.stereotype.Service

@Service
class PendencyService(private val pendencyRepository: PendencyRepository) {

    fun create(pendency: PendencyModel){
        pendencyRepository.save(pendency)
    }

    fun update(pendency: PendencyModel){
        pendencyRepository.save(pendency)
    }

    fun findAll():List<PendencyModel>{
        return pendencyRepository.findAll()
    }

    fun findByCustomerService(customerService: CustomerServiceModel): List<PendencyModel>{
        return pendencyRepository.findByCustomerService(customerService)
    }
}