package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.FormOfPaymentModel
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.repositories.FormOfPaymentRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FormOfPaymentService(private val formOfPaymentRepository: FormOfPaymentRepository) {

    val LOGGER = LoggerFactory.getLogger(javaClass)

    fun create(formOfPaymentModel: FormOfPaymentModel){
        formOfPaymentRepository.save(formOfPaymentModel)
        LOGGER.info("Forma de pagamento criada com sucesso!")
        LOGGER.info("Final do método de criação de forma de pagamento!")
    }

    fun update(formOfPaymentModel: FormOfPaymentModel){
        formOfPaymentRepository.save(formOfPaymentModel)
        LOGGER.info("Forma de pagamento atualizada com sucesso!")
        LOGGER.info("Final do método de atualização de forma de pagamento!")
    }

    fun delete(salon: BeautySalonModel, id:Long){
        findById(salon, id)
        formOfPaymentRepository.deleteById(id)
        LOGGER.info("Forma de pagamento excluída com sucesso!")
        LOGGER.info("Final do método de exclusão de forma de pagamento!")
    }

    fun findAll(salon: BeautySalonModel):List<FormOfPaymentModel>{
        return formOfPaymentRepository.findAllByBeautySalon(salon)
    }

    fun findById(salon: BeautySalonModel, id: Long):FormOfPaymentModel{
        return formOfPaymentRepository.findByIdAndSalon(salon, id) ?:
             throw NotFoundException(Errors.GS401.message.format(id), Errors.GS401.internalCode)
    }

}