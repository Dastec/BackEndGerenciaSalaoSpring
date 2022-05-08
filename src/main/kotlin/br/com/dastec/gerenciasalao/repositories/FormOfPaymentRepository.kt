package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.FormOfPaymentModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FormOfPaymentRepository: JpaRepository<FormOfPaymentModel, Long> {
}