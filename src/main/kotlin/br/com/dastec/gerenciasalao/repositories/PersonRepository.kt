package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.PersonModel
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository: JpaRepository<PersonModel, Long> {
}