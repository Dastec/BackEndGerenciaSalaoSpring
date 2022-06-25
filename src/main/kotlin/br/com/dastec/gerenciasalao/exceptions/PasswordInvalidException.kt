package br.com.dastec.gerenciasalao.exceptions

data class PasswordInvalidException(override val message: String, val errorCode: String): Exception()