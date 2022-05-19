package br.com.dastec.gerenciasalao.controllers.responses

import br.com.dastec.gerenciasalao.models.enums.TypePhoneNumber

data class PhoneNumberResponse(
    var type: TypePhoneNumber,

    var ddd: String,

    var number: String,
)
