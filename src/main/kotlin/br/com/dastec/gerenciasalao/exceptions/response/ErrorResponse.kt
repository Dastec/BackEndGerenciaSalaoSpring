package br.com.dastec.gerenciasalao.exceptions.response

data class ErrorResponse(
    val httpStatuusCode: Int,
    val message: String,
    val internalCodeErro: String,
    val errors: List<FieldErrorResponse>?
)
