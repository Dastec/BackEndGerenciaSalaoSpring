package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.CategoryModel
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository: JpaRepository<CategoryModel, Long> {

    @Query(value = "select u from CategoryModel u where u.beautySalon = ?1 AND u.nameCategory like %?2%")
    fun findByNameCategoryContainingIgnoreCase(salon: BeautySalonModel, name: String): List<CategoryModel>

    fun findAllByBeautySalon(salon: BeautySalonModel): List<CategoryModel>

    @Query(value = "SELECT u from CategoryModel u WHERE u.beautySalon = ?1 AND u.idCategory = ?2")
    fun findByIdAndSalon(salon: BeautySalonModel, idCategory: Long): CategoryModel?

}