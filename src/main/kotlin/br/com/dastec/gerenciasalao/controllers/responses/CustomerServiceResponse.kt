package br.com.dastec.gerenciasalao.controllers.responses

import br.com.dastec.gerenciasalao.models.ServiceModel
import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import com.fasterxml.jackson.annotation.JsonAlias
import java.time.LocalDate
import java.time.LocalTime

data class CustomerServiceResponse(
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

    @JsonAlias("customer_id")
    var customer: CustomerResponse,

    @JsonAlias("services")
    var services: MutableList<ServiceModel>,

    var observation: String?,

    @JsonAlias("status")
    var statusCustomerService: CustomerServiceStatus
)