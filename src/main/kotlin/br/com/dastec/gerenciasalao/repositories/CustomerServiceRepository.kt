package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface CustomerServiceRepository : JpaRepository<CustomerServiceModel, Long> {

    @Query(value = "SELECT u FROM CustomerServiceModel u WHERE u.beautySalon = ?1 AND u.customer = ?2")
    fun findByCustomer(salon: BeautySalonModel, customer: CustomerModel): List<CustomerServiceModel>

    @Query(value = "SELECT u FROM CustomerServiceModel u WHERE u.beautySalon = ?1 AND u.idCustomerService = ?2")
    fun findByIdAndSalon(salon: BeautySalonModel, idCustomerService: Long): CustomerServiceModel?

    fun findAllByBeautySalon(salon: BeautySalonModel, pageable: Pageable): Page<CustomerServiceModel>

    @Query(value = "select u from CustomerServiceModel u where u.beautySalon = ?1 AND u.customer.idCustomer = ?2 AND u.statusCustomerService = 'OPEN'")
    fun findByCustomerServiceWithStatusOpen(salon: BeautySalonModel, idCustomer: Long): List<CustomerServiceModel>

    @Query(value = "select u from CustomerServiceModel u where u.beautySalon = ?1 AND  u.customer.idCustomer = ?2 AND u.statusCustomerService = 'FINALIZEDPENDING'")
    fun findByCustomerServiceWithStatusFinalizedPending(salon: BeautySalonModel, idCustomer: Long): List<CustomerServiceModel>

    @Query(value = "select u from CustomerServiceModel u where u.beautySalon = ?1 AND  u.customer.idCustomer = ?2 and u.statusCustomerService = 'CREATED'")
    fun findByCustomerServiceWithStatusCreated(salon: BeautySalonModel, idCustomer: Long): List<CustomerServiceModel>

    @Query(value = "select u from CustomerServiceModel u where u.beautySalon = ?1 AND u.customer = ?2 and (u.statusCustomerService = 'CREATED' or u.statusCustomerService = 'OPEN')")
    fun findByCustomerServiceWithStatusCreatedOrOpen(salon: BeautySalonModel, customer: CustomerModel): List<CustomerServiceModel>


    @Query(value = "SELECT u FROM CustomerServiceModel u WHERE u.beautySalon = ?1 AND u.dateCustomerService = ?2 AND (u.statusCustomerService = 'FINISHED' or u.statusCustomerService = 'FINALIZEDPENDING')")
    fun getDailyGain(salon: BeautySalonModel, data: LocalDate): List<CustomerServiceModel>

    @Query(value = "SELECT u FROM CustomerServiceModel u WHERE u.beautySalon = ?1 AND (u.dateCustomerService BETWEEN ?2 AND ?3) AND (u.statusCustomerService = 'FINISHED' or u.statusCustomerService = 'FINALIZEDPENDING')")
    fun getMonthlyGain(salon: BeautySalonModel, initialDate: LocalDate, finalDate: LocalDate): List<CustomerServiceModel>
}