package br.com.dastec.gerenciasalao.exceptions.controller

import br.com.dastec.gerenciasalao.exceptions.*
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.exceptions.response.ErrorResponse
import br.com.dastec.gerenciasalao.exceptions.response.FieldErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@org.springframework.web.bind.annotation.ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException, request: WebRequest): ResponseEntity<ErrorResponse>{
        val error = ErrorResponse(
            ex.message,
            ex.errorCode,
            null
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            ex.message,
            ex.errorCode,
            null
        )
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(CustomerServiceHasNoPendingException::class)
    fun handleCustomerServiceHasNoPendingException(ex: CustomerServiceHasNoPendingException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            ex.message,
            ex.errorCode,
            null
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(ex: IllegalStateException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            ex.message,
            ex.errorCode,
            null
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(PasswordInvalidException::class)
    fun handlePasswordInvalidException(ex: PasswordInvalidException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            ex.message,
            ex.errorCode,
            null
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(ex: AuthenticationException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            ex.message,
            ex.errorCode,
            null
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        ex: MethodArgumentNotValidException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            Errors.GS001.message,
            Errors.GS001.internalCode,
            ex.bindingResult.fieldErrors.map { FieldErrorResponse(it.defaultMessage ?: "Invalid", it.field) }
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InternalAuthenticationServiceException::class)
    fun handleInternalAuthenticationServiceException(ex: InternalAuthenticationServiceException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            Errors.GSL002.message,
            Errors.GSL002.internalCode,
            null
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }
}

