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
    var idCustomerService: Long? = null,

    @Column(name = "date_customer_service", nullable = false)
    var dateCustomerService: LocalDate = LocalDate.now(),

    @Column(name = "start_time", nullable = false)
    var startTime: LocalTime = LocalTime.now(),

    @Column(name = "end_time", nullable = false)
    var endTime: LocalTime?,

    @Column(name = "total_value")
    var totalValue: Double?,

    @Column(name = "paid_value")
    val paidValue: Double?,

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    var customer: CustomerModel,

    @ManyToMany
    @JoinTable(name = "customer_service_service",
        joinColumns = [JoinColumn(name = "customer_service_id")],
        inverseJoinColumns = [JoinColumn(name = "service_id")])
    var services: MutableList<ServiceModel>,

    var observation: String?,

    @Enumerated(EnumType.STRING)
    @Column(name = "status_customer_service")
    var statusCustomerService: CustomerServiceStatus = CustomerServiceStatus.ABERTO
)
