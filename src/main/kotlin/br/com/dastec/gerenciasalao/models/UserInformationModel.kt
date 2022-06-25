package br.com.dastec.gerenciasalao.models

data class UserInformationModel(
    var name: String,
    var fiscalIdentification: String,
    var userKey: String,
    var email: String,
    var phoneNumber: String,
    var exp: Long
)
