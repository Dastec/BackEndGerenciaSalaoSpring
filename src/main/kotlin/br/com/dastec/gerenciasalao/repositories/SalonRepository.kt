package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.BeautySalonModel
import org.springframework.data.jpa.repository.JpaRepository

interface SalonRepository: JpaRepository<BeautySalonModel, Long> {
    fun findByFiscalIdentification(fiscalIdentification: String): BeautySalonModel?
}