package br.com.dastec.gerenciasalao.models

import javax.persistence.*

@Entity
@Table(name = "form_of_payment")
data class FormOfPaymentModel(

    @Column(name ="id_form_payment")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idFormPayment: Long? = null,

    @Column(name = "name_form_payment", nullable = false, unique = true)
    var nameFormPayment: String
)
