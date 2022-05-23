package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CustomerServiceRepository : JpaRepository<CustomerServiceModel, Long> {

    fun findByCustomer(customer: CustomerModel): List<CustomerServiceModel>

    @Query(value = "select u from CustomerServiceModel u where u.customer.idCustomer = ?1 and u.statusCustomerService = 'OPEN'")
    fun findByCustomerServiceWithStatusOpen(idCustomerService: Long): List<CustomerServiceModel>

    @Query(value = "select u from CustomerServiceModel u where u.customer.idCustomer = ?1 and u.statusCustomerService = 'FINALIZEDPENDING'")
    fun findByCustomerServiceWithStatusFinalizedPending(idCustomerService: Long): List<CustomerServiceModel>

    @Query(value = "select u from CustomerServiceModel u where u.customer.idCustomer = ?1 and (u.statusCustomerService = 'CREATED' or u.statusCustomerService = 'OPEN')")
    fun findByCustomerServiceWithStatusCreated(idCustomerService: Long): List<CustomerServiceModel>
}