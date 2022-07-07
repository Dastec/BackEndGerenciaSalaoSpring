package br.com.dastec.gerenciasalao.controllers.requests.salon

import com.fasterxml.jackson.annotation.JsonAlias

data class updateSalonRequest(

    var name: String,

    @JsonAlias("tax_identification")
    var taxIdentification: String,

)
