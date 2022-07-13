package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.BeautySalonModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SalonRepository: JpaRepository<BeautySalonModel, Long> {
    fun findByFiscalIdentification(fiscalIdentification: String): BeautySalonModel?

//    @Query("SELECT u FROM BeautySalonModel u WHERE u.idSalon = ?1")
//    fun findByIdSalon(salon: BeautySalonModel): BeautySalonModel?
}