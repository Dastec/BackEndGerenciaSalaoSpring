package br.com.dastec.gerenciasalao.models

import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import java.time.LocalDate
import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "customer_service")
data class CustomerServiceModel(

    @Column(name = "id_customer_service")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idCustomerService: Long?,

    @Column(name = "date_customer_service", nullable = false)
    val dateCustomerService: LocalDate,

    @Column(name = "start_time", nullable = false)
    val startTime: LocalTime,

    @Column(name = "end_time", nullable = false)
    val endTime: LocalTime,

    @Column(name = "total_value")
    val totalValue: Double?,

    @Column(name = "paid_value")
    val paidValue: Double?,

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    var customer: CustomerModel,

    @Enumerated(EnumType.STRING)
    @Column(name = "status_customer_service")
    val statusCustomerService: CustomerServiceStatus,

    @ManyToMany
    @JoinTable(name = "customer_service_service",
        joinColumns = [JoinColumn(name = "customer_service_id")],
        inverseJoinColumns = [JoinColumn(name = "service_id")])
    val services: List<ServiceModel>,

    val observation: String?
)
