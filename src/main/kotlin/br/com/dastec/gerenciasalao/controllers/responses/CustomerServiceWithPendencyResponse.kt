package br.com.dastec.gerenciasalao.controllers.responses

import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

data class CustomerServiceWithPendencyResponse(
    val idCustomerService: Long? = null,

    val dateCustomerService: LocalDate,

    @JsonAlias("start_time")
    val startTime: LocalTime,

    @JsonAlias("end_time")
    val endTime: LocalTime?,

    @JsonAlias("total_value")
    val totalValue: BigDecimal?,

    @JsonAlias("paid_value")
    val paidValue: BigDecimal?,

    val customer: String,

    val payments: MutableList<PaymentResponse>,

    @JsonAlias("services")
    val saleServices: MutableList<SaleServiceResponse>,

    val observation: String?,

    @JsonAlias("status")
    val statusCustomerService: CustomerServiceStatus,

    val pendency: PendencyResponse,
)