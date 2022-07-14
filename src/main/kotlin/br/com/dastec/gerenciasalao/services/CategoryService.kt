package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.CategoryModel
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.repositories.CategoryRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository,) {
     val LOGGER = LoggerFactory.getLogger(javaClass)

    fun createCategory(category: CategoryModel){
        categoryRepository.save(category)
        LOGGER.info("Categoria criada com sucesso!")
        LOGGER.info("Final do método de criação de categoria")
    }

    fun updateCategory(category: CategoryModel){
        categoryRepository.save(category)
        LOGGER.info("Categoria atualizada com sucesso!")
        LOGGER.info("Final do método de atualização de categoria")
    }

    fun delete(id: Long){
        if(categoryRepository.findById(id) == null){
            NotFoundException(Errors.GS201.message.format(id), Errors.GS201.internalCode)
            LOGGER.info("Categoria não encontrada!")
        }
        categoryRepository.deleteById(id)
        LOGGER.info("Categoria deletada com sucesso!")
        LOGGER.info("Final do método de exclusão de categoria")
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