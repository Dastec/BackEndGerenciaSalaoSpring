package br.com.dastec.gerenciasalao.controllers.responses

import br.com.dastec.gerenciasalao.models.PhoneNumberModel
import com.fasterxml.jackson.annotation.JsonAlias
import java.time.LocalDate

data class CustomerResponse(
    var idCustomer : Long,

    var alias: String,

    @JsonAlias("full_name")
    var fullName: String,

    var cpf: String,

    @JsonAlias("birth_date")
    var birthDate: LocalDate,

    var clientKey: String?,

    @JsonAlias("phone_numbers")
    var phoneNumber: List<PhoneNumberResponse>,

    var photo: String?,

)