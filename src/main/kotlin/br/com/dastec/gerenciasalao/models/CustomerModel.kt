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

    @Column(name = "cpf", nullable = false, unique = true)
    var cpf: String,

    @Column(name = "birth_date", nullable = false)
    var birthDate: LocalDate,

//    @OneToMany(mappedBy = "customerModel")
//    @JoinColumn(name = "customer_id")
//    var phones: List<PhoneNumberModel>,

    @Column(name = "photo")
    var photo: String?,

    @Column(name = "client_key", nullable = false, unique = true)
    var clientKey: String?,

    @Column(name = "created_at")
    var createdAt: LocalDate?
)
{

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    var status: CustomerStatus? = null
        set(value) {
            if (field == CustomerStatus.EXCLUIDO) {
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
        createdAt: LocalDate?
        ): this(idCustomer, alias, fullName, cpf, birthDate, photo, clientKey, createdAt){
            this.status = status
        }
}
