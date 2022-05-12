package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.CustomerServiceHasNoPendingException
import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.PaymentModel
import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import br.com.dastec.gerenciasalao.models.enums.PaymentStatus
import br.com.dastec.gerenciasalao.repositories.PaymentRepository
import org.springframework.stereotype.Service

@Service
class PaymentService(private val paymentRepository: PaymentRepository, private val customerServiceModelService: CustomerServiceModelService) {

    fun payService(payment: PaymentModel){
        paymentRepository.save(payment)
    }

    fun payServiceWithPendeny(payment: PaymentModel){
        val customerService = customerServiceModelService.findById(payment.customerService.idCustomerService!!)
        if(customerService.statusCustomerService != CustomerServiceStatus.FINALIZADOCOMPENDENCIA){
            throw CustomerServiceHasNoPendingException(Errors.GS502.message.format(customerService.customer.idCustomer), Errors.GS502.internalCode)
        }
        paymentRepository.save(payment)
    }

    fun updateStatusLancado(payment: PaymentModel){
        payment.status = PaymentStatus.LANCADO
        paymentRepository.save(payment)
    }
    fun updateStatusAberto(payment: PaymentModel){
        payment.status = PaymentStatus.LANCADO
        paymentRepository.save(payment)
    }

    fun findAll():List<PaymentModel>{
        return paymentRepository.findAll()
    }

    fun findPaymentByCustomerService(customerService: CustomerServiceModel):List<PaymentModel>{
        return paymentRepository.findByCustomerService(customerService)
    }

    fun findPaymentsWithCustomerServiceWithStatusAberto(id: Long): List<PaymentModel>{
        return paymentRepository.findPaymentsWithCustomerServiceWithStatusAberto(id)
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