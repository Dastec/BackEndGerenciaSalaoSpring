package br.com.dastec.gerenciasalao.utils

import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.security.JwtUtil
import br.com.dastec.gerenciasalao.services.BeautySalonService
import org.springframework.stereotype.Service

@Service
class SpringUtil(val jwtUtil: JwtUtil, val beautySalonService: BeautySalonService) {

    fun getSalon(token: String): BeautySalonModel{
        return beautySalonService.findById(jwtUtil.getUserInformation(token).salonId)
    }

    fun hasAuthorization():Boolean{
        return false
    }
}