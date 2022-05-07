package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.CategoryModel
import br.com.dastec.gerenciasalao.models.ServiceModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ServiceRepository: JpaRepository<ServiceModel, Long> {

    @Query(value = "select u from ServiceModel u where u.nameService like %?1%")
    fun findByNameServiceContainingIgnoreCase(name: String): List<ServiceModel>
}