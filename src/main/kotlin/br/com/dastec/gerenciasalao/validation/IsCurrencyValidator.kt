package br.com.dastec.gerenciasalao.validation

import br.com.dastec.gerenciasalao.validation.annotation.IsCurrency
import java.math.BigDecimal
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class IsCurrencyValidator: ConstraintValidator<IsCurrency, BigDecimal> {
    override fun isValid(value: BigDecimal?, context: ConstraintValidatorContext?): Boolean {
        if(value == null){
            return true
        }

        if (value!! <= BigDecimal.ZERO){
            return false
        }

//        if (value.isNaN()){
//            return false
//        }

        return true
    }

}
