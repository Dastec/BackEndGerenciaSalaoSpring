package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.CategoryModel
import br.com.dastec.gerenciasalao.models.CustomerModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository: JpaRepository<CategoryModel, Long> {

    @Query(value = "select u from CategoryModel u where u.nameCategory like %?1%")
    fun findByNameCategoryContainingIgnoreCase(name: String): List<CategoryModel>
}