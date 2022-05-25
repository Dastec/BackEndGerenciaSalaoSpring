package br.com.dastec.gerenciasalao.validation.annotation

import br.com.dastec.gerenciasalao.validation.FullNameValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [FullNameValidator::class])
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class FullName(
    val message: String = "Nome completo inválido!",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
