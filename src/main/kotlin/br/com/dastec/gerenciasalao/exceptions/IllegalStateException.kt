package br.com.dastec.gerenciasalao.exceptions

class IllegalStateException(override val message: String, val errorCode: String): Exception() {
}