package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.models.CategoryModel
import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CustomerServiceRepository : JpaRepository<CustomerServiceModel, Long> {

    @Query(value = "SELECT u FROM CustomerServiceModel u WHERE u.beautySalon = ?1 AND u.customer = ?2")
    fun findByCustomer(salon: BeautySalonModel, customer: CustomerModel): List<CustomerServiceModel>

    @Query(value = "SELECT u FROM CustomerServiceModel u WHERE u.beautySalon = ?1 AND u.idCustomerService = ?2")
    fun findByIdAndSalon(salon: BeautySalonModel, idCustomerService: Long): CustomerServiceModel?

    fun findAllByBeautySalon(salon: BeautySalonModel): List<CustomerServiceModel>

    @Query(value = "select u from CustomerServiceModel u where u.beautySalon = ?1 AND u.customer.idCustomer = ?2 AND u.statusCustomerService = 'OPEN'")
    fun findByCustomerServiceWithStatusOpen(salon: BeautySalonModel, idCustomer: Long): List<CustomerServiceModel>

    @Query(value = "select u from CustomerServiceModel u where u.beautySalon = ?1 AND  u.customer.idCustomer = ?2 AND u.statusCustomerService = 'FINALIZEDPENDING'")
    fun findByCustomerServiceWithStatusFinalizedPending(salon: BeautySalonModel, idCustomer: Long): List<CustomerServiceModel>

    @Query(value = "select u from CustomerServiceModel u where u.beautySalon = ?1 AND  u.customer.idCustomer = ?2 and u.statusCustomerService = 'CREATED'")
    fun findByCustomerServiceWithStatusCreated(salon: BeautySalonModel, idCustomer: Long): List<CustomerServiceModel>
}