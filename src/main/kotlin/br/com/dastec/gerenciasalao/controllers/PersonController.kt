package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.mapper.PersonMapper
import br.com.dastec.gerenciasalao.controllers.requests.person.RegisterPersonRequest
import br.com.dastec.gerenciasalao.controllers.requests.person.updatePersonRequest
import br.com.dastec.gerenciasalao.models.PersonModel
import br.com.dastec.gerenciasalao.services.PersonService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/persons")
class PersonController(private val personService: PersonService, private val personMapper: PersonMapper ) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun registerPerson(@RequestBody @Valid registerPersonRequest: RegisterPersonRequest){
        personService.registerPerson(personMapper.registerPersonToPersonModel(registerPersonRequest))
    }

    @PutMapping("/{id}")
    fun updatePerson(@PathVariable id: Long, @RequestBody @Valid updatePersonRequest: updatePersonRequest){
        val person = personService.findById(id)
        personService.updatePerson(personMapper.updatePersonToPersonModel(person, updatePersonRequest))
    }

    @DeleteMapping("/{id}")
    fun cancelPerson(@PathVariable id: Long){
        val person = personService.findById(id)
        personService.cancelPerson(personMapper.cancelPersonToPersonModel(person))
    }

    @GetMapping("/{id}")
    fun findPersonById(@PathVariable id: Long):PersonModel{
        return personService.findById(id)
    }

    @GetMapping()
    fun findAllPerson():List<PersonModel>{
        return personService.findAll()
    }

}