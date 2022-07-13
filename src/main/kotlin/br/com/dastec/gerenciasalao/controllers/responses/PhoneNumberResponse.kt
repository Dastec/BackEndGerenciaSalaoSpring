package br.com.dastec.gerenciasalao.controllers.responses

import br.com.dastec.gerenciasalao.models.enums.TypePhoneNumber

data class PhoneNumberResponse(
    val type: TypePhoneNumber,

    val ddd: String,

    val number: String,
)
