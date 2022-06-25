package br.com.dastec.gerenciasalao.exceptions

data class AuthenticationException(override val message: String, val errorCode: String): Exception()