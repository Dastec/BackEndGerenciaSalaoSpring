package br.com.dastec.gerenciasalao.models

import br.com.dastec.gerenciasalao.models.enums.PaymentStatus
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "payments")
data class PaymentModel(

    @Column(name = "id_payment")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idPayment: Long? = null,

    @ManyToOne
    @JoinColumn(name = "form_payment_id")
    val formOfPayment: FormOfPaymentModel,

    @ManyToOne
    @JoinColumn(name = "customer_service_id")
    val customerService: CustomerServiceModel,

    @Column(name = "value_payment")
    val valuePayment: BigDecimal = BigDecimal.ZERO,


    @Column(name = "date_payment")
    val datePayment: LocalDate = LocalDate.now(),

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    var status: PaymentStatus = PaymentStatus.OPEN,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserModel?,

    @ManyToOne
    @JoinColumn(name = "salon_id")
    var beautySalon: BeautySalonModel,

    )
