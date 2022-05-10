package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.PaymentModel
import br.com.dastec.gerenciasalao.repositories.PaymentRepository
import org.springframework.stereotype.Service

@Service
class PaymentService(private val paymentRepository: PaymentRepository) {

    fun payService(payment: PaymentModel){
        paymentRepository.save(payment)
    }

    fun findAll():List<PaymentModel>{
        return paymentRepository.findAll()
    }

    fun findByCustomerService(customerService: CustomerServiceModel):List<PaymentModel>{
        return paymentRepository.findByCustomerService(customerService)
    }

    fun getTotalValue(payments: List<PaymentModel>):Double{
        var totalValue = 0.0

        for (payment in payments){
            totalValue+= payment.valuePayment
        }
        return totalValue
    }

    fun findById(id: Long): PaymentModel{
        return paymentRepository.findById(id).orElseThrow {
            NotFoundException(Errors.GS701.message.format(id), Errors.GS701.internalCode)
        }
    }

}