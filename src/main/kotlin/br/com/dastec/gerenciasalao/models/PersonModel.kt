package br.com.dastec.gerenciasalao.models

import br.com.dastec.gerenciasalao.models.enums.PersonStatus
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "persons")
data class PersonModel(

    @Column(name = "id_person")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idPerson: Long? = null,

    @Column(nullable = false)
    val name: String,

    @Column(name = "tax_identification", nullable = false, unique = true)
    val fiscalIdentification: String,

    @Column(name = "created_at")
    val createdAt: LocalDate =  LocalDate.now(),

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val status:  PersonStatus = PersonStatus.ACTIVE,

    )


