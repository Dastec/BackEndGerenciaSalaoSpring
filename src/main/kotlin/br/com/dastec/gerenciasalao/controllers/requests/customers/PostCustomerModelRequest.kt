package br.com.dastec.gerenciasalao.controllers.requests.customers

import br.com.dastec.gerenciasalao.models.PhoneNumberModel
import br.com.dastec.gerenciasalao.validation.annotation.CpfAvailable
import br.com.dastec.gerenciasalao.validation.annotation.IsCpf
import com.fasterxml.jackson.annotation.JsonAlias
import java.time.LocalDate
import javax.validation.constraints.NotEmpty

data class PostCustomerModelRequest(

    @field:NotEmpty(message = "O campo apelido não pôde ser null ou vazio!")
    var alias: String,

    @field:NotEmpty(message = "O campo nome completo não pôde ser  null ou vazio!")
    @JsonAlias("full_name")
    var fullName: String,

    //@IsCpf
    @CpfAvailable
    var cpf: String,

    @JsonAlias("birth_date")
    var birthDate: LocalDate,

    @JsonAlias("phone_numbers")
    var phoneNumber: List<PostPhoneRequest>,

    var photo: String?

)