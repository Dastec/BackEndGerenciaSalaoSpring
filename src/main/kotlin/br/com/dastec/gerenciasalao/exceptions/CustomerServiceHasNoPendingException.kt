package br.com.dastec.gerenciasalao.exceptions

class CustomerServiceHasNoPendingException(override val message: String, val errorCode: String): Exception() {
}