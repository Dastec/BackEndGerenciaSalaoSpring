package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.PaymentModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepository: JpaRepository<PaymentModel, Long> {

    fun findByCustomerService(customerService: CustomerServiceModel):List<PaymentModel>

    @Query(value = "select u from PaymentModel u where u.customerService.idCustomerService = ?1 and u.status = 'OPEN'")
    fun findPaymentsWithCustomerServiceWithStatusAberto(idCustomerService: Long):List<PaymentModel>

}