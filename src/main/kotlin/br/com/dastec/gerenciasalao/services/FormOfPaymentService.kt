package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.FormOfPaymentModel
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.repositories.FormOfPaymentRepository
import org.springframework.stereotype.Service

@Service
class FormOfPaymentService(private val formOfPaymentRepository: FormOfPaymentRepository) {

    fun create(formOfPaymentModel: FormOfPaymentModel){
        formOfPaymentRepository.save(formOfPaymentModel)
    }

    fun update(formOfPaymentModel: FormOfPaymentModel){
        formOfPaymentRepository.save(formOfPaymentModel)
    }

    fun delete(id:Long){
        formOfPaymentRepository.deleteById(id)
    }

    fun findAll(salon: BeautySalonModel):List<FormOfPaymentModel>{
        return formOfPaymentRepository.findAllByBeautySalon(salon)
    }

    fun findById(id: Long):FormOfPaymentModel{
        return formOfPaymentRepository.findById(id).orElseThrow {
            NotFoundException(Errors.GS401.message.format(id), Errors.GS401.internalCode)
        }
    }

}