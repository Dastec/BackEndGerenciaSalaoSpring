package br.com.dastec.gerenciasalao.exceptions.response

data class FieldErrorResponse(
    val message: String,
    val field: String
)
