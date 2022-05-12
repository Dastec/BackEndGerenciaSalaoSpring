package br.com.dastec.gerenciasalao.exceptions.response

data class ErrorResponse(
    val httpStatusCode: Int,
    val message: String,
    val internalCodeErro: String,
    val errors: List<FieldErrorResponse>?
)
