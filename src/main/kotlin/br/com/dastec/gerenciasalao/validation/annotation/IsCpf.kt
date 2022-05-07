package br.com.dastec.gerenciasalao.validation.annotation

import br.com.dastec.gerenciasalao.validation.IsCpfValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [IsCpfValidator::class])
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class IsCpf(
    val message: String = "CPF inválido!",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
