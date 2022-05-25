package br.com.dastec.gerenciasalao.validation

import br.com.dastec.gerenciasalao.services.CustomerService
import br.com.dastec.gerenciasalao.validation.annotation.FullName
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class FullNameValidator(): ConstraintValidator<FullName, String> {
    override fun isValid(fullName: String?, context: ConstraintValidatorContext?): Boolean {
        var isFullName = Regex("^[a-zA-Z\\u00C0-\\u017F´]+\\s+[a-zA-Z\\u00C0-\\u017F´]{0,}\$")
        return isFullName.containsMatchIn(fullName!!)
    }
}