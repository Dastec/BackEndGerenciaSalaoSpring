package br.com.dastec.gerenciasalao.validation

import br.com.dastec.gerenciasalao.validation.annotation.IsCurrency
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class IsCurrencyValidator: ConstraintValidator<IsCurrency, Double> {
    override fun isValid(value: Double?, context: ConstraintValidatorContext?): Boolean {
        if (value!! <= 0.0){
            return false
        }

        if(value == null){
            return false
        }

        if (value.isNaN()){
            return false
        }

        return true
    }

}
