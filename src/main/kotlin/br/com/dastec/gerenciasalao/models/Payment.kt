package br.com.dastec.gerenciasalao.models

data class Payment(

    var idPayment: Long,
    var formOfPayment: FormOfPaymentModel,
    var customerService: CustomerServiceModel,
    var value: Double
)
