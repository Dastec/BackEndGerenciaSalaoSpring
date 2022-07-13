package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.mapper.SalonMapper
import br.com.dastec.gerenciasalao.controllers.requests.salon.RegisterSalonRequest
import br.com.dastec.gerenciasalao.controllers.requests.salon.UpdateSalonRequest
import br.com.dastec.gerenciasalao.controllers.responses.MessageResponse
import br.com.dastec.gerenciasalao.exceptions.BadRequestException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.security.JwtUtil
import br.com.dastec.gerenciasalao.services.SalonService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/salon")
class BeautySalonController(private val salonService: SalonService, private val salonMapper: SalonMapper, private val jwtUtil: JwtUtil) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun registerSalon(@RequestBody @Valid registerSalonRequest: RegisterSalonRequest):MessageResponse{
        salonService.registerSalon(salonMapper.registerSalonToSalonModel(registerSalonRequest))
        return MessageResponse(
            message = "Salão ${registerSalonRequest.name} registrado com sucesso!"
        )
    }

    @PutMapping("/admin/{id}")
    fun updateSalon(@PathVariable id: Long,
                    @RequestBody @Valid updateSalonRequest: UpdateSalonRequest,
                    @RequestHeader(value = "Authorization") token: String): BeautySalonModel
    {
        val userToken = jwtUtil.getUserInformation(token.split(" ")[1])

        if (userToken.salonId != id){
            throw BadRequestException(Errors.GS001.message, Errors.GS001.internalCode)
        }

        val salon = salonService.findById(id)
        return salonService.updateSalon(salonMapper.updateSalonToSalonModel(salon, updateSalonRequest))

    }

    @DeleteMapping("/admin/{id}")
    fun cancelSalon(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): MessageResponse{
        val userToken = jwtUtil.getUserInformation(token.split(" ")[1])

        if (userToken.salonId != id){
            throw BadRequestException(Errors.GS001.message, Errors.GS001.internalCode)
        }

        val salon = salonService.findById(id)
        salonService.cancelSalon(salonMapper.cancelSalonToSalonModel(salon))
        return MessageResponse(message = "Seu salão foi cancelado com sucesso!")
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