package br.com.dastec.gerenciasalao.models

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "services")
data class ServiceModel(

    @Column(name = "id_service")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idService: Long? = null,

    @Column(name = "name_service", nullable = false)
    val nameService: String,

    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    val category: CategoryModel,

    val price: BigDecimal?,

    @ManyToOne
    @JoinColumn(name = "salon_id")
    var beautySalon: BeautySalonModel,
)
