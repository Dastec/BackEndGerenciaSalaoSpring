package br.com.dastec.gerenciasalao.controllers.requests.person

import com.fasterxml.jackson.annotation.JsonAlias
import javax.persistence.Column

data class updatePersonRequest(

    var name: String,

    @JsonAlias("tax_identification")
    var taxIdentification: String,

)
