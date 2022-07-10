package br.com.dastec.gerenciasalao.models

import br.com.dastec.gerenciasalao.models.enums.PendencyStatus
import javax.persistence.*

@Entity
@Table(name = "pendency")
data class PendencyModel(

    @Column(name = "id_pendency")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idPendencyModel: Long? = null,

    @ManyToOne
    @JoinColumn(name = "customer_service_id")
    val customerService: CustomerServiceModel,

    @Column(name = "value_pendency", nullable = false)
    var valuePendency: Double,

    @Enumerated(EnumType.STRING)
    var status: PendencyStatus = PendencyStatus.OPEN,

    @ManyToOne
    @JoinColumn(name = "salon_id")
    var beautySalon: BeautySalonModel,
    )
