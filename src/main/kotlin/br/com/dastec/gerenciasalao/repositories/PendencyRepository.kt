package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.PaymentModel
import br.com.dastec.gerenciasalao.models.PendencyModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PendencyRepository : JpaRepository<PendencyModel, Long> {

    fun findByCustomerService(customerServiceModel: CustomerServiceModel): PendencyModel

    @Query("SELECT p from PendencyModel p WHERE p.customerService.idCustomerService = ?1 and status = 'OPEN'")
    fun findByCustomerServiceWhereStatusAberto(idCustomerService: Long): PendencyModel

}