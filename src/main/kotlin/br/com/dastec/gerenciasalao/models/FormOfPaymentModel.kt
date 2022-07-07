package br.com.dastec.gerenciasalao.models

import javax.persistence.*

@Entity
@Table(name = "forms_of_payment")
data class FormOfPaymentModel(

    @Column(name ="id_form_payment")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idFormPayment: Long? = null,

    @Column(name = "name_form_payment", nullable = false)
    var nameFormPayment: String,

    @ManyToOne
    @JoinColumn(name = "salon_id")
    var beautySalon: BeautySalonModel,
)
