package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.controllers.requests.payments.PostPaymentServiceWithPendencyRequest
import br.com.dastec.gerenciasalao.events.PaymentEventFinalizePendency
import br.com.dastec.gerenciasalao.events.PaymentEventUpdatePendency
import br.com.dastec.gerenciasalao.exceptions.BadRequestException
import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.CustomerServiceModel
import br.com.dastec.gerenciasalao.models.PaymentModel
import br.com.dastec.gerenciasalao.models.enums.CustomerServiceStatus
import br.com.dastec.gerenciasalao.models.enums.PaymentStatus
import br.com.dastec.gerenciasalao.realCurrency
import br.com.dastec.gerenciasalao.repositories.PaymentRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val pendencyService: PendencyService,
    private val customerServiceModelService: CustomerServiceModelService,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun updatePayService(payment: PaymentModel) {
        paymentRepository.save(payment)
    }

    fun payService(payment: PaymentModel) {
        val customerService = customerServiceModelService.findById(payment.customerService.idCustomerService!!)
        if (customerService.statusCustomerService == CustomerServiceStatus.FINISHED || customerService.statusCustomerService == CustomerServiceStatus.FINALIZEDPENDING) {
            throw BadRequestException(
                Errors.GS503.message.format(customerService.idCustomerService),
                Errors.GS503.internalCode
            )
        }

        val payments =
            findPaymentsByCustomerWithCustomerServiceWithStatusOpen(payment.customerService.idCustomerService!!)
        val paidValue = payments.sumOf { it.valuePayment }

        if ((paidValue + payment.valuePayment) > customerService.totalValue!!) {
            throw br.com.dastec.gerenciasalao.exceptions.IllegalStateException(
                Errors.GS702.message.format(
                    realCurrency(
                        customerService.totalValue!!
                    )
                ), Errors.GS702.internalCode
            )
        }
        paymentRepository.save(payment)
    }

    fun payServiceWithPendency(payment: PaymentModel) {
        val customerService = customerServiceModelService.findById(payment.customerService.idCustomerService!!)
        if (customerService.statusCustomerService == CustomerServiceStatus.FINISHED) {
            throw BadRequestException(
                Errors.GS504.message.format(customerService.idCustomerService),
                Errors.GS504.internalCode
            )
        }

        if ((payment.valuePayment + customerService.paidValue!!) > customerService.totalValue!!) {
            throw br.com.dastec.gerenciasalao.exceptions.IllegalStateException(
                Errors.GS702.message.format(
                    realCurrency(
                        customerService.totalValue!!
                    )
                ), Errors.GS702.internalCode
            )
        }

        if (customerService.statusCustomerService == CustomerServiceStatus.FINALIZEDPENDING &&
            (payment.valuePayment + customerService.paidValue!!) == customerService.totalValue!!
        ) {
            applicationEventPublisher.publishEvent(PaymentEventFinalizePendency(this, payment))
            payment.status = PaymentStatus.LAUNCHED
        } else if (customerService.statusCustomerService == CustomerServiceStatus.FINALIZEDPENDING &&
            (payment.valuePayment + customerService.paidValue!!) < customerService.totalValue!!
        ) {
            applicationEventPublisher.publishEvent(PaymentEventUpdatePendency(this, payment))
            payment.status = PaymentStatus.LAUNCHED
        }
        paymentRepository.save(payment)
    }

    fun updateStatusLaunched(payment: PaymentModel) {
        payment.status = PaymentStatus.LAUNCHED
        paymentRepository.save(payment)
    }

    fun findById(id: Long): PaymentModel {
        return paymentRepository.findById(id).orElseThrow {
            NotFoundException(Errors.GS701.message.format(id), Errors.GS701.internalCode)
        }
    }

    fun findAll(): List<PaymentModel> {
        return paymentRepository.findAll()
    }

    fun findPaymentByCustomerService(customerService: CustomerServiceModel): List<PaymentModel> {
        return paymentRepository.findByCustomerService(customerService)
    }

    fun findPaymentsByCustomerWithCustomerServiceWithStatusOpen(id: Long): List<PaymentModel> {
        return paymentRepository.findPaymentsWithCustomerServiceWithStatusAberto(id)
    }

    fun validPaymentPendency(postPaymentServiceWithPendencyRequest: PostPaymentServiceWithPendencyRequest) {
        val totalPayments = postPaymentServiceWithPendencyRequest.paymentObject.sumOf { it.valuePayment }
        val customerServices =
            customerServiceModelService.findAllByIds(postPaymentServiceWithPendencyRequest.customerServices)
        val pendencies =
            pendencyService.findAllByCustomerService(customerServices).sortedBy { it.valuePendency }.toMutableList()

        if (totalPayments > pendencies.sumOf { it.valuePendency }) {
            throw BadRequestException(Errors.GS402.message, Errors.GS402.internalCode)
        }
        pendencies.removeFirst()
        if (totalPayments < pendencies.sumOf { it.valuePendency } + 1) {
            throw BadRequestException(Errors.GS403.message, Errors.GS403.internalCode)
        }
    }

}