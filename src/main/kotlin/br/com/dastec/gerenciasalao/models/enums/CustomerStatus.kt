package br.com.dastec.gerenciasalao.models.enums

enum class CustomerStatus(val code: Long, val tipo: String) {
    ACTIVE(1, "ACTIVE"), EXCLUDED(2, "EXCLUDED")
}