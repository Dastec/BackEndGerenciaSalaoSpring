package br.com.dastec.gerenciasalao.models

import br.com.dastec.gerenciasalao.models.enums.SalonStatus
import javax.persistence.*
import java.time.LocalDate


@Entity
@Table(name = "beauty_salons")
data class BeautySalonModel(

    @Column(name = "id_salon")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idSalon: Long? = null,

    @Column(nullable = false)
    val name: String,

    @Column(name = "fiscal_identification", nullable = false, unique = true)
    val fiscalIdentification: String,

    @Column(name = "created_at")
    val createdAt: LocalDate =  LocalDate.now(),

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val status:  SalonStatus = SalonStatus.ACTIVE,

    )


