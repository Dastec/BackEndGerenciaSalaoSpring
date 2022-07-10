package br.com.dastec.gerenciasalao.models

import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "customer_services")
data class CustomerServiceModel(

    @Column(name = "id_customer_service")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idCustomerService: Long? = null,

    @Column(name = "date_customer_service", nullable = false)
    val dateCustomerService: LocalDate = LocalDate.now(),

    @Column(name = "start_time")
    val startTime: LocalTime = LocalTime.now(),

    @Column(name = "end_time")
    val endTime: LocalTime?,

    @Column(name = "total_value")
    val totalValue: Double?,

    @Column(name = "paid_value")
    val paidValue: Double? = 0.0,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    val customer: CustomerModel?,

    @JsonIgnore
    @OneToMany(mappedBy = "customerService", fetch = FetchType.LAZY)
    val saleServices: MutableList<SaleServiceModel>? = null,

    val observation: String?,

    @Enumerated(EnumType.STRING)
    @Column(name = "status_customer_service")
    var statusCustomerService: CustomerServiceStatus = CustomerServiceStatus.CREATED,

    @ManyToOne
    @JoinColumn(name = "salon_id")
    var beautySalon: BeautySalonModel,
)
