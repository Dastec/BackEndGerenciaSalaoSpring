package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.models.PhoneNumberModel
import br.com.dastec.gerenciasalao.repositories.PhoneNumberRepository
import org.springframework.stereotype.Service

@Service
class PhoneNumberService(private val phoneNumberRepository: PhoneNumberRepository) {

    fun create(phoneNumber: PhoneNumberModel) {
        phoneNumberRepository.save(phoneNumber)
    }

    fun update(phoneNumber: PhoneNumberModel) {
        phoneNumberRepository.save(phoneNumber)
    }

    fun findAllByCustomerId(idCustomer: Long): List<PhoneNumberModel>?{
        return phoneNumberRepository.findAllByCustomerId(idCustomer)
    }

    fun delete(id: Long){
        if (phoneNumberRepository.existsById(id)){
            throw NotFoundException("Telefone não existe", "0000")
        }
        phoneNumberRepository.deleteById(id)
    }
}