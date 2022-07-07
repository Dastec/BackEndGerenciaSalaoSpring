package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.mapper.SalonMapper
import br.com.dastec.gerenciasalao.controllers.requests.salon.RegisterSalonRequest
import br.com.dastec.gerenciasalao.controllers.requests.salon.updateSalonRequest
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.services.SalonService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/salon")
class BeautySalonController(private val salonService: SalonService, private val salonMapper: SalonMapper ) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun registerSalon(@RequestBody @Valid registerSalonRequest: RegisterSalonRequest){
        salonService.registerSalon(salonMapper.registerSalonToSalonModel(registerSalonRequest))
    }

    @PutMapping("/{id}")
    fun updateSalon(@PathVariable id: Long, @RequestBody @Valid updateSalonRequest: updateSalonRequest){
        val salon = salonService.findById(id)
        salonService.updateSalon(salonMapper.updateSalonToSalonModel(salon, updateSalonRequest))
    }

    @DeleteMapping("/{id}")
    fun cancelSalon(@PathVariable id: Long){
        val salon = salonService.findById(id)
        salonService.cancelSalon(salonMapper.cancelSalonToSalonModel(salon))
    }

    @GetMapping("/{id}")
    fun findSalonById(@PathVariable id: Long):BeautySalonModel{
        return salonService.findById(id)
    }

    @GetMapping()
    fun findAllSalon():List<BeautySalonModel>{
        return salonService.findAll()
    }

}