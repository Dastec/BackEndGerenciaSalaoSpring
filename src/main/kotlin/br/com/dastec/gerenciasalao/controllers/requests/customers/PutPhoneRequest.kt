package br.com.dastec.gerenciasalao.controllers.requests.customers

import br.com.dastec.gerenciasalao.models.enums.TypePhoneNumber
import org.hibernate.validator.constraints.Length
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.Max

data class PutPhoneRequest(

    @Enumerated(EnumType.STRING)
    val type: TypePhoneNumber?,

    val ddd: String?,

    val number: String?
)