package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.models.CategoryModel
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.SaleServiceModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SaleServiceRepository: JpaRepository<SaleServiceModel, Long> {

    fun findAllByBeautySalon(salon: BeautySalonModel): List<SaleServiceModel>

    @Query(value = "select u from SaleServiceModel u where u.beautySalon = ?1 and u.service.nameService like %?2%")
    fun findByIdAndSalon(salon: BeautySalonModel, idSaleService: Long): SaleServiceModel?

    @Query(value = "select u from SaleServiceModel u where u.beautySalon = ?1 and u.service.nameService like %?2%")
    fun findByNameServiceContainingIgnoreCase(salon: BeautySalonModel, name: String): List<SaleServiceModel>

    fun findByCustomerService(customerServiceModel: CustomerServiceModel): MutableList<SaleServiceModel>

    @Query(value = "select u from SaleServiceModel u where u.customerService.idCustomerService = ?1 and u.service.idService = ?2")
    fun findByCustomerServiceAndService(idCustomerService: Long, idServiceModel: Long): MutableList<SaleServiceModel>
}