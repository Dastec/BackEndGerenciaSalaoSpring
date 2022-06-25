package br.com.dastec.gerenciasalao.controllers.mapper

import br.com.dastec.gerenciasalao.controllers.requests.person.RegisterPersonRequest
import br.com.dastec.gerenciasalao.controllers.requests.person.updatePersonRequest
import br.com.dastec.gerenciasalao.models.PersonModel
import br.com.dastec.gerenciasalao.models.enums.PersonStatus
import br.com.dastec.gerenciasalao.services.PersonService
import org.springframework.stereotype.Component

@Component
class PersonMapper(
    private val personService: PersonService
) {

    fun registerPersonToPersonModel(registerPersonRequest: RegisterPersonRequest):PersonModel{
        return PersonModel(
            name = registerPersonRequest.name,
            fiscalIdentification = registerPersonRequest.fiscalIdentification,
        )
    }

    fun updatePersonToPersonModel(person: PersonModel, updatePersonRequest: updatePersonRequest):PersonModel{
        return PersonModel(
            idPerson = person.idPerson,
            name = updatePersonRequest.name,
            fiscalIdentification = updatePersonRequest.taxIdentification,
            createdAt = person.createdAt,
            status = person.status,
        )
    }

    fun cancelPersonToPersonModel(person: PersonModel):PersonModel{
        return PersonModel(
            idPerson = person.idPerson,
            name = person.name,
            fiscalIdentification = person.fiscalIdentification,
            createdAt = person.createdAt,
            status = PersonStatus.CANCELED
        )
    }
}