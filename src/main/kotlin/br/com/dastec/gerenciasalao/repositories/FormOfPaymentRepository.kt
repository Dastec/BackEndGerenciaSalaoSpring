package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.FormOfPaymentModel
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FormOfPaymentRepository: JpaRepository<FormOfPaymentModel, Long> {

    fun findAllByBeautySalon(salon: BeautySalonModel): List<FormOfPaymentModel>

    @Query(value = "SELECT u from FormOfPaymentModel u WHERE u.beautySalon = ?1 AND u.idFormPayment = ?2")
    fun findByIdAndSalon(salon: BeautySalonModel, id: Long): FormOfPaymentModel?
}