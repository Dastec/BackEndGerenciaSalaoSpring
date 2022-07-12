package br.com.dastec.gerenciasalao.controllers.requests.customers

import br.com.dastec.gerenciasalao.validation.annotation.CpfAvailable
import br.com.dastec.gerenciasalao.validation.annotation.FullName
import br.com.dastec.gerenciasalao.validation.annotation.IsCpf
import com.fasterxml.jackson.annotation.JsonAlias
import java.time.LocalDate
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Past
import javax.validation.constraints.Pattern

data class PostCustomerModelRequest(
    //var idCustomer: Long? = null,

    @field:Pattern(regexp = "[A-Za-z]{2,}", message = "O apelido deve ter no mínimo dois caracteres alfabéticos!")
    val alias: String,

    @JsonAlias("full_name")
    @FullName
    val fullName: String,

    @IsCpf
    //@CpfAvailable
    val cpf: String,

    @JsonAlias("birth_date")
    @field: Past(message = "A data de nascimento tem que ser menor que a data atual!")
    val birthDate: LocalDate,

    @field:NotEmpty(message = "Ao menos deve-se passar um número de telefone!")
    @JsonAlias("phone_numbers")
    val phoneNumber: List<PostPhoneRequest>,

    val photo: String?,

)