package br.com.dastec.gerenciasalao.controllers.responses

import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal

data class FinalizeCustomerServiceResponse(

    @JsonAlias("status")
    val statusCustomerService: CustomerServiceStatus,

    @JsonAlias("id_Customer_Service")
    val idCustomerService: Long? = null,

    val customer: String,

    @JsonAlias("total_value")
    val totalValue: BigDecimal?,

    @JsonAlias("paid_value")
    val paidValue: BigDecimal?,

    @JsonAlias("pending_value")
    val pendingValue: BigDecimal?,
)
