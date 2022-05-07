package br.com.dastec.gerenciasalao.exceptions

data class NotFoundException(override val message: String, val errorCode: String): Exception()