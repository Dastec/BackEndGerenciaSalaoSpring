package br.com.dastec.gerenciasalao.security

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.access.prepost.PreFilter

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@PreFilter("filterObject.id = 3")
annotation class UserCanOnlyAccessTheirOwnResource()
