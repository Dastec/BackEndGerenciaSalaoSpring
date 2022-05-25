package br.com.dastec.gerenciasalao.validation.annotation

import br.com.dastec.gerenciasalao.validation.FullNameValidator
import br.com.dastec.gerenciasalao.validation.IsCurrencyValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [IsCurrencyValidator::class])
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class IsCurrency(
    val message: String = "O valor não pode ser null ou negativo!",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
