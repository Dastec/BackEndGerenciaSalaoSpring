package br.com.dastec.gerenciasalao.validation.annotation

import br.com.dastec.gerenciasalao.validation.CpfAvailableValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [CpfAvailableValidator::class])
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class CpfAvailable(
    val message: String = "Esse CPF já está cadastrado!",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
