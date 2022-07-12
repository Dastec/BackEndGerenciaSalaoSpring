package br.com.dastec.gerenciasalao.validation

import br.com.dastec.gerenciasalao.services.CustomerService
import br.com.dastec.gerenciasalao.validation.annotation.CpfAvailable
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class CpfAvailableValidator(val customerService: CustomerService) : ConstraintValidator<CpfAvailable, String> {

    override fun isValid(cpf: String?, context: ConstraintValidatorContext?): Boolean {
        return false//customerService.cpfAvailable(cpf!!)
    }


}
