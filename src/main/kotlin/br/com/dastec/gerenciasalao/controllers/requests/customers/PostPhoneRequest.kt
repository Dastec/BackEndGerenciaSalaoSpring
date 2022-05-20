package br.com.dastec.gerenciasalao.controllers.requests.customers

import br.com.dastec.gerenciasalao.models.enums.TypePhoneNumber
import org.hibernate.validator.constraints.Length
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.Max
import javax.validation.constraints.NotEmpty

data class PostPhoneRequest(

    @field:NotEmpty(message ="O tipo de telefone não pode estar vazio!")
    @Enumerated(EnumType.STRING)
    val type: TypePhoneNumber,

    @field:NotEmpty(message ="O DDD não pode estar vazio!")
    val ddd: String,

    @field:NotEmpty(message ="O número de telefone não pode estar vazio!")
    val number: String
)