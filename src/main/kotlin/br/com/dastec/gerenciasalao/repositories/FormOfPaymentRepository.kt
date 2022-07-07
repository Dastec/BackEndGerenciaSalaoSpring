package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.FormOfPaymentModel
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FormOfPaymentRepository: JpaRepository<FormOfPaymentModel, Long> {

    fun findAllByBeautySalon(salon: BeautySalonModel): List<FormOfPaymentModel>
}