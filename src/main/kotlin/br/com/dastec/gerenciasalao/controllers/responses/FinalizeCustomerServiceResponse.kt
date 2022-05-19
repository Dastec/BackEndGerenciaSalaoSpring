package br.com.dastec.gerenciasalao.controllers.responses

import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import com.fasterxml.jackson.annotation.JsonAlias

data class FinalizeCustomerServiceResponse(

    @JsonAlias("status")
    var statusCustomerService: CustomerServiceStatus,

    @JsonAlias("id_Customer_Service")
    var idCustomerService: Long? = null,

    var customer: String,

    @JsonAlias("total_value")
    var totalValue: Double?,

    @JsonAlias("paid_value")
    var paidValue: Double?,

    @JsonAlias("pending_value")
    var pendingValue: Double?,
)
