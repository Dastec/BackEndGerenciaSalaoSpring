package br.com.dastec.gerenciasalao.models.enums

enum class CustomerStatus(val code: Long, val tipo: String) {
    ATIVO(1, "ATIVO"), EXCLUIDO(2, "EXCLUIDO")
}