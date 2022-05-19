package br.com.dastec.gerenciasalao.models

import br.com.dastec.gerenciasalao.controllers.responses.PhoneNumberResponse
import br.com.dastec.gerenciasalao.models.enums.TypePhoneNumber
import javax.persistence.*

@Entity
@Table(name = "phone_numbers")
data class PhoneNumberModel(

    @Column(name = "id_phone")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idPhone: Long? = null,

    @Enumerated(EnumType.STRING)
    var type:TypePhoneNumber,

    var ddd: String,

    var number: String,

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    var customerModel: CustomerModel

){
    fun toPhoneNumberResponse(): PhoneNumberResponse {
        return PhoneNumberResponse(
            type = this.type,
            ddd = this.ddd,
            number = this.number
        )
    }
}
