package br.com.dastec.gerenciasalao.controllers.requests

import br.com.dastec.gerenciasalao.validation.annotation.IsCurrency
import java.math.BigDecimal


data class PutUpdateSaleServiceRequest(
    @IsCurrency
    val price: BigDecimal
)