package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.CategoryModel
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.repositories.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun createCategory(category: CategoryModel){
        categoryRepository.save(category)
    }

    fun updateCategory(category: CategoryModel){
        categoryRepository.save(category)
    }

    fun delete(id: Long){
        if(categoryRepository.findById(id) == null){
            NotFoundException(Errors.GS201.message.format(id), Errors.GS201.internalCode)
        }
        categoryRepository.deleteById(id)
    }

    fun findById(salon: BeautySalonModel, id: Long): CategoryModel{
        return categoryRepository.findByIdAndSalon(salon, id) ?: throw NotFoundException(Errors.GS201.message.format(id), Errors.GS201.internalCode)
    }

    fun findAll(salon: BeautySalonModel):List<CategoryModel>{
        return categoryRepository.findAllByBeautySalon(salon)
    }

    fun findByNameCategory(salon: BeautySalonModel, name: String): List<CategoryModel>{
        return categoryRepository.findByNameCategoryContainingIgnoreCase(salon, name)
    }
}