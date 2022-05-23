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
    var idCustomerService: Long? = null,

    @Column(name = "date_customer_service", nullable = false)
    var dateCustomerService: LocalDate = LocalDate.now(),

    @Column(name = "start_time")
    var startTime: LocalTime = LocalTime.now(),

    @Column(name = "end_time")
    var endTime: LocalTime?,

    @Column(name = "total_value")
    var totalValue: Double?,

    @Column(name = "paid_value")
    var paidValue: Double? = 0.0,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel?,

    @JsonIgnore
    @OneToMany(mappedBy = "customerService", fetch = FetchType.LAZY)
    var saleServices: MutableList<SaleServiceModel>? = null,

    var observation: String?,

    @Enumerated(EnumType.STRING)
    @Column(name = "status_customer_service")
    var statusCustomerService: CustomerServiceStatus = CustomerServiceStatus.CREATED
)
