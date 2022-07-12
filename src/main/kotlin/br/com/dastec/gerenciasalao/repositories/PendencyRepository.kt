package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.PendencyModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PendencyRepository : JpaRepository<PendencyModel, Long> {

    fun findByCustomerService(customerServiceModel: CustomerServiceModel): PendencyModel

    fun findAllByBeautySalon(salon: BeautySalonModel): List<PendencyModel>

    @Query("SELECT p from PendencyModel p WHERE p.customerService = ?1 and status = 'OPEN'")
    fun findByCustomerServiceWhereStatusOpen(customerService: CustomerServiceModel): PendencyModel

    @Query("SELECT p from PendencyModel p WHERE p.beautySalon = ?1 AND p.idPendencyModel = ?2")
    fun findByIdAndSalon(salon: BeautySalonModel, idPendency: Long): PendencyModel?

    @Query("SELECT p from PendencyModel p WHERE p.customerService.customer = ?1")
    fun findByCustomer(customerModel: CustomerModel): MutableList<PendencyModel>

}