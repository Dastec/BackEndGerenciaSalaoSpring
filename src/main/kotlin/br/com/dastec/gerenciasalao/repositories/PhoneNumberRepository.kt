package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.PhoneNumberModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PhoneNumberRepository: JpaRepository<PhoneNumberModel, Long> {

    @Query(value = "select u from PhoneNumberModel u where u.customerModel.idCustomer = ?1")
    fun findAllByCustomerId(id: Long): List<PhoneNumberModel>
}