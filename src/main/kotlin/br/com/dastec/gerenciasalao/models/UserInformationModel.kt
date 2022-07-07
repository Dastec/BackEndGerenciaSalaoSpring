package br.com.dastec.gerenciasalao.models

data class UserInformationModel(
    val idUser: Long,
    val sub: String,
    val name: String,
    val userKey: String,
    val email: String,
    val phoneNumber: String,
    val exp: Long,
    val salonId: Long
)
