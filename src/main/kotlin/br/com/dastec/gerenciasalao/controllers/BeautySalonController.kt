package br.com.dastec.gerenciasalao.controllers

import br.com.dastec.gerenciasalao.controllers.mapper.SalonMapper
import br.com.dastec.gerenciasalao.controllers.requests.salon.RegisterSalonRequest
import br.com.dastec.gerenciasalao.controllers.requests.salon.UpdateSalonRequest
import br.com.dastec.gerenciasalao.controllers.responses.MessageResponse
import br.com.dastec.gerenciasalao.exceptions.BadRequestException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.security.JwtUtil
import br.com.dastec.gerenciasalao.services.BeautySalonService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/salon")
class BeautySalonController(private val beautySalonService: BeautySalonService, private val salonMapper: SalonMapper, private val jwtUtil: JwtUtil) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun registerSalon(@RequestBody @Valid registerSalonRequest: RegisterSalonRequest):MessageResponse{
        beautySalonService.LOGGER.info("Início do método de registro de salão!")
        beautySalonService.registerSalon(salonMapper.registerSalonToSalonModel(registerSalonRequest))
        return MessageResponse(
            message = "Salão ${registerSalonRequest.name} registrado com sucesso!"
        )
    }

    @PutMapping("/admin/{id}")
    fun updateSalon(@PathVariable id: Long,
                    @RequestBody @Valid updateSalonRequest: UpdateSalonRequest,
                    @RequestHeader(value = "Authorization") token: String): BeautySalonModel
    {
        beautySalonService.LOGGER.info("Início do método de atualização de salão!")
        val userToken = jwtUtil.getUserInformation(token.split(" ")[1])

        if (userToken.salonId != id){
            throw BadRequestException(Errors.GS001.message, Errors.GS001.internalCode)
        }

        val salon = beautySalonService.findById(id)
        return beautySalonService.updateSalon(salonMapper.updateSalonToSalonModel(salon, updateSalonRequest))

    }

    @DeleteMapping("/admin/{id}")
    fun cancelSalon(@PathVariable id: Long, @RequestHeader(value = "Authorization") token: String): MessageResponse{
        beautySalonService.LOGGER.info("Início do método de exclusão de salão!")
        val userToken = jwtUtil.getUserInformation(token.split(" ")[1])

        if (userToken.salonId != id){
            throw BadRequestException(Errors.GS001.message, Errors.GS001.internalCode)
        }

        val salon = beautySalonService.findById(id)
        beautySalonService.cancelSalon(salonMapper.cancelSalonToSalonModel(salon))
        return MessageResponse(message = "Seu salão foi cancelado com sucesso!")
    }

    @GetMapping("/{id}")
    fun findSalonById(@PathVariable id: Long):BeautySalonModel{
        return beautySalonService.findById(id)
    }

    @GetMapping()
    fun findAllSalon():List<BeautySalonModel>{
        return beautySalonService.findAll()
    }

}