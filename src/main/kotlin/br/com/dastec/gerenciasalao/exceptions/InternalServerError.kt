package br.com.dastec.gerenciasalao.exceptions

data class InternalServerError(override val message: String, val errorCode: String): RuntimeException()