package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.CategoryModel
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.SaleServiceModel
import br.com.dastec.gerenciasalao.models.ServiceModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SaleServiceRepository: JpaRepository<SaleServiceModel, Long> {

    @Query(value = "select u from SaleServiceModel u where u.service.nameService like %?1%")
    fun findByNameServiceContainingIgnoreCase(name: String): List<SaleServiceModel>

    fun findByCustomerService(customerServiceModel: CustomerServiceModel): MutableList<SaleServiceModel>

    @Query(value = "select u from SaleServiceModel u where u.service.idService = ?1 and u.customerService.idCustomerService = ?2 ")
    fun findByCustomerServiceAndService(idCustomerService: Long, idServiceModel: Long): MutableList<SaleServiceModel>
}