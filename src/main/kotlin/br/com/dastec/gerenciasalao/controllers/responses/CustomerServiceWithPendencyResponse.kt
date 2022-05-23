package br.com.dastec.gerenciasalao.controllers.responses

import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import com.fasterxml.jackson.annotation.JsonAlias
import java.time.LocalDate
import java.time.LocalTime

data class CustomerServiceWithPendencyResponse(
    var idCustomerService: Long? = null,

    var dateCustomerService: LocalDate,

    @JsonAlias("start_time")
    var startTime: LocalTime,

    @JsonAlias("end_time")
    var endTime: LocalTime?,

    @JsonAlias("total_value")
    var totalValue: Double?,

    @JsonAlias("paid_value")
    var paidValue: Double?,

    var customer: String,

    var payments: MutableList<PaymentResponse>,

    @JsonAlias("services")
    var saleServices: MutableList<SaleServiceResponse>,

    var observation: String?,

    @JsonAlias("status")
    var statusCustomerService: CustomerServiceStatus,

    var pendency: PendencyResponse,
)