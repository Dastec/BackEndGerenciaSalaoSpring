package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.PersonModel
import br.com.dastec.gerenciasalao.repositories.PersonRepository
import org.springframework.stereotype.Service

@Service
class PersonService(private val personRepository: PersonRepository) {

    fun registerPerson(person: PersonModel){
        personRepository.save(person)
    }

    fun updatePerson(person: PersonModel){
        personRepository.save(person)
    }

    fun cancelPerson(person: PersonModel){
        personRepository.save(person)
    }

    fun findById(id: Long): PersonModel{
        return personRepository.findById(id).orElseThrow {
            throw NotFoundException(Errors.GS101.message.format(id), Errors.GS101.internalCode)
        }
    }

    fun findAll():List<PersonModel>{
        return personRepository.findAll()
    }

}