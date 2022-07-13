package br.com.dastec.gerenciasalao.models

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "sale_services")
data class SaleServiceModel(

    @Column(name = "id_sale_service")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idSaleService: Long? = null,


    @ManyToOne
    @JoinColumn(name = "service_id")
    val service: ServiceModel,

    @ManyToOne
    @JoinColumn(name = "customer_service_id")
    val customerService: CustomerServiceModel? = null,

    val price: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "salon_id")
    var beautySalon: BeautySalonModel,
)
