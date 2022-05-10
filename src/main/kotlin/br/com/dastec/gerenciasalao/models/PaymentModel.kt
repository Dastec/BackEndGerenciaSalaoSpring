package br.com.dastec.gerenciasalao.models

import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "payment")
data class PaymentModel(

    @Column(name = "id_payment")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idPayment: Long? = null,

    @ManyToOne
    @JoinColumn(name = "form_payment_id")
    var formOfPayment: FormOfPaymentModel,

    @ManyToOne
    @JoinColumn(name = "customer_service_id")
    var customerService: CustomerServiceModel,

    @Column(name = "value_payment")
    var valuePayment: Double = 0.0,


    @Column(name = "date_payment")
    var datePayment: LocalDate = LocalDate.now()
)
