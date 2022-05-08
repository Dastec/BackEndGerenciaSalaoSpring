package br.com.dastec.gerenciasalao.models

import br.com.dastec.gerenciasalao.models.enums.PendencyStatus
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "pendency")
data class PendencyModel(

    @Column(name = "id_pendency")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idPendencyModel: Long? = null,

    @ManyToOne
    @JoinColumn(name = "customer_service_id")
    var customerService: CustomerServiceModel,

    @Column(name = "value_pendency", nullable = false)
    var valuePendency: BigDecimal,

    @Enumerated(EnumType.STRING)
    var status: PendencyStatus,

    )
