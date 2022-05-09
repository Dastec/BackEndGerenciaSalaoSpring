package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.PendencyModel
import org.springframework.data.jpa.repository.JpaRepository

interface PendencyRepository: JpaRepository<PendencyModel, Long> {

    fun findBYCustomerService(customerServiceModel: CustomerServiceModel):List<PendencyModel>
}