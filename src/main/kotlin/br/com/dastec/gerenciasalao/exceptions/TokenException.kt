package br.com.dastec.gerenciasalao.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException

//@ResponseStatus(HttpStatus.FORBIDDEN)
data class TokenException(override val message: String, override val cause: Throwable?): ResponseStatusException(HttpStatus.FORBIDDEN)