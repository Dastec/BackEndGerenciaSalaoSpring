package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.PendencyModel
import br.com.dastec.gerenciasalao.repositories.PendencyRepository

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
        return pendencyRepository.findBYCustomerService(customerService)
    }
}