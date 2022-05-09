package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerServiceRepository: JpaRepository<CustomerServiceModel, Long> {
}