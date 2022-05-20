package br.com.dastec.gerenciasalao.controllers.requests.customers

import br.com.dastec.gerenciasalao.models.PhoneNumberModel
import br.com.dastec.gerenciasalao.validation.annotation.CpfAvailable
import br.com.dastec.gerenciasalao.validation.annotation.IsCpf
import com.fasterxml.jackson.annotation.JsonAlias
import java.time.LocalDate
import javax.validation.constraints.NotEmpty

data class PutCustomerModelRequest(

    @field:NotEmpty(message = "O campo apelido não pode ser null ou vazio!")
    var alias: String,

    @field:NotEmpty(message = "O campo nome completo não pode ser  null ou vazio!")
    @JsonAlias("full_name")
    var fullName: String,

    @IsCpf
    var cpf: String,

    @field:NotEmpty(message = "O campo data de nascimento não pode ser null ou vazio!")
    @JsonAlias("birth_date")
    var birthDate: LocalDate,

    @field:NotEmpty(message = "Ao menos deve-se passar um número de telefone!")
    @JsonAlias("phone_numbers")
    var phoneNumber: List<PutPhoneRequest>,

    var photo: String?
)
