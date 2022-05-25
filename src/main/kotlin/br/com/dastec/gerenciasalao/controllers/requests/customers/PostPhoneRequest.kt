package br.com.dastec.gerenciasalao.controllers.requests.customers

import br.com.dastec.gerenciasalao.models.enums.TypePhoneNumber
import org.hibernate.validator.constraints.Length
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

data class PostPhoneRequest(

    @field:NotBlank(message ="O tipo de telefone não pode ser vazio!")
    @Enumerated(EnumType.STRING)
    val type: TypePhoneNumber,

    @field:Pattern(regexp = "[0-9]{2}", message ="O DDD deve composto por dois numeros!")
    val ddd: String,

    @field:NotBlank(message ="O campo número de telefone não pode ser vazio!")
    val number: String
)