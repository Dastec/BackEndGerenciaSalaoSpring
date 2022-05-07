package br.com.dastec.gerenciasalao.models

import br.com.dastec.gerenciasalao.models.enums.PendencyStatus
import javax.persistence.*

@Entity
@Table(name = "pendency")
data class PendencyModel(

    @Column(name = "id_pendency")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idPendencyModel: Long? = null,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customerService: CustomerServiceModel,

    var valuePendency: Double,

    @Enumerated(EnumType.STRING)
    var status: PendencyStatus
)
