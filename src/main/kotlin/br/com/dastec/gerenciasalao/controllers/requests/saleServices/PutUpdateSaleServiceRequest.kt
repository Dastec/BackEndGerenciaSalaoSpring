package br.com.dastec.gerenciasalao.controllers.requests

import br.com.dastec.gerenciasalao.validation.annotation.IsCurrency


data class PutUpdateSaleServiceRequest(
    @IsCurrency
    val price: Double
)