package br.com.dastec.gerenciasalao.models

import br.com.dastec.gerenciasalao.models.enums.CustomerStatus
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "customers")
data class CustomerModel(

    @Column(name = "id_customer")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idCustomer: Long? = null,

    @Column(name = "alias", nullable = false)
    var alias: String,

    @Column(name = "full_name", nullable = false)
    var fullName: String,

    @Column(name = "cpf", nullable = false)
    var cpf: String,

    @Column(name = "birth_date", nullable = false)
    var birthDate: LocalDate,

    @Column(name = "photo")
    var photo: String?,

    @Column(name = "client_key", nullable = false)
    var clientKey: String?,

    @Column(name = "created_at")
    var createdAt: LocalDate?,

    @ManyToOne
    @JoinColumn(name = "salon_id")
    var beautySalon: BeautySalonModel,

    )
{
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: CustomerStatus? = null
        set(value) {
            if (field == CustomerStatus.EXCLUDED) {
                throw Exception("Não é possivel alterar Status ${field}")
            }
            field = value
        }

    constructor(
        idCustomer: Long? = null,
        alias: String,
        fullName: String,
        cpf: String,
        birthDate: LocalDate,
        photo: String?,
        status: CustomerStatus,
        clientKey: String,
        createdAt: LocalDate?,
        beautySalonModel: BeautySalonModel
        ): this(idCustomer, alias, fullName, cpf, birthDate, photo, clientKey, createdAt, beautySalonModel){
            this.status = status
        }
}
