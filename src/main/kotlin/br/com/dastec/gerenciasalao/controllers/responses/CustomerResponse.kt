package br.com.dastec.gerenciasalao.controllers.responses

import com.fasterxml.jackson.annotation.JsonAlias
import java.time.LocalDate

data class CustomerResponse(
    val idCustomer : Long,

    val alias: String,

    @JsonAlias("full_name")
    val fullName: String,

    val cpf: String,

    @JsonAlias("birth_date")
    val birthDate: LocalDate,

    val clientKey: String?,

    @JsonAlias("phone_numbers")
    val phoneNumber: List<PhoneNumberResponse>,

    val photo: String?,

)