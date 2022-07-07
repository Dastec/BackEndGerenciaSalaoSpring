package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.CategoryModel
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.models.ServiceModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ServiceRepository: JpaRepository<ServiceModel, Long> {

    @Query(value = "select u from ServiceModel u where u.beautySalon = ?1 and u.nameService like %?2%")
    fun findByNameServiceContainingIgnoreCase(salon: BeautySalonModel, name: String): List<ServiceModel>

    fun findByCategory(categoryModel: CategoryModel): List<ServiceModel>

    fun findAllByBeautySalon(salon: BeautySalonModel): List<ServiceModel>
}