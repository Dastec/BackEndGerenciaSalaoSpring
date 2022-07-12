package br.com.dastec.gerenciasalao.utils

import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.security.JwtUtil
import br.com.dastec.gerenciasalao.services.SalonService
import org.springframework.stereotype.Service

@Service
class SpringUtil(val jwtUtil: JwtUtil, val salonService: SalonService) {

    fun getSalon(token: String): BeautySalonModel{
        return salonService.findById(jwtUtil.getUserInformation(token).salonId)
    }

    fun hasAuthorization():Boolean{
        return false
    }
}