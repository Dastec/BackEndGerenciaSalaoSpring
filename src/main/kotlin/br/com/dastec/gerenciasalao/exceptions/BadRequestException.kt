package br.com.dastec.gerenciasalao.exceptions

class BadRequestException(override val message: String, val errorCode: String): Exception() {

}