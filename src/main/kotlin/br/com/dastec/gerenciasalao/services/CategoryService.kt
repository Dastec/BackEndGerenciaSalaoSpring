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

    fun findById(id: Long): CategoryModel{
        return categoryRepository.findById(id).orElseThrow {
            NotFoundException(Errors.GS201.message.format(id), Errors.GS201.internalCode)
        }
    }

    fun findAll(salon: BeautySalonModel):List<CategoryModel>{
        return categoryRepository.findAllByBeautySalon(salon)
    }

    fun findByNameCategory(person: BeautySalonModel, name: String): List<CategoryModel>{
        return categoryRepository.findByNameCategoryContainingIgnoreCase(person, name)
    }

    fun delete(id: Long){
        if(!categoryRepository.existsById(id)){
            NotFoundException(Errors.GS201.message.format(id), Errors.GS201.internalCode)
        }
        categoryRepository.deleteById(id)
    }
}