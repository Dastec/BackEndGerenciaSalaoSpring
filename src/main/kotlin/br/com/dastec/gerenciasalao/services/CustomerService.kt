package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.controllers.extensions.toPhoneNumberModel
import br.com.dastec.gerenciasalao.controllers.requests.customers.PostPhoneRequest
import br.com.dastec.gerenciasalao.controllers.requests.customers.PutPhoneRequest
import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.repositories.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(private val customerRepository: CustomerRepository, private val phoneNumberService: PhoneNumberService) {

    fun createCustomer(customerModel: CustomerModel, phones: List<PostPhoneRequest>){
        val customer = customerRepository.save(customerModel)

        for (phone in phones){
            phoneNumberService.create(phone.toPhoneNumberModel(customer))
        }
    }

    fun updateCustomer(customerModel: CustomerModel, phones: List<PutPhoneRequest>){
        if (customerRepository.existsById(customerModel.idCustomer)){
            val customer = customerRepository.save(customerModel)

            val previousPhones = phoneNumberService.findAllByCustomerId(customer.idCustomer!!)

            for (phone in phones){
                phoneNumberService.update(phone.toPhoneNumberModel(previousPhones!![phones.indexOf(phone)]))
            }
        }else{
            throw Exception("Usuario não encontrado")
        }
    }

    fun deleteCustomer(customerModel: CustomerModel){
        customerRepository.save(customerModel)
    }

    fun findAllBySalonModel(salon: BeautySalonModel): List<CustomerModel>{
        return customerRepository.findAllByBeautySalon(salon)
    }

    fun findByClientKey(salon: BeautySalonModel, clientKey: String): CustomerModel {
        return customerRepository.findByClientKey(salon, clientKey).orElseThrow{
            NotFoundException(Errors.GS101.message.format(clientKey), Errors.GS101.internalCode)
        }
    }

    fun findByNameContaining(salon: BeautySalonModel, name: String):List<CustomerModel>{
        return customerRepository.findByFullNameAndSalon(salon, name)
    }

    fun findByStatus(salon: BeautySalonModel, status: String): List<CustomerModel>{
        return customerRepository.findByStatusAndSalonModel(salon, status)
    }

    fun cpfAvailable(salon: BeautySalonModel, cpf: String): Boolean {
        if (customerRepository.existsByCpf(salon, cpf) == null){
            return true
        }
        return false
    }

    fun findByIdAndSalon(salon: BeautySalonModel, idCustomer: Long): CustomerModel {
        return customerRepository.findByIdAndSalon(salon, idCustomer)
            ?: throw NotFoundException(Errors.GS101.message.format(idCustomer), Errors.GS101.internalCode)
    }

    fun hasAuthorization(salon: BeautySalonModel, idCustomer: Long) {
        if (customerRepository.findByIdAndSalon(salon, idCustomer) == null){
            throw NotFoundException(Errors.GS101.message.format(idCustomer), Errors.GS101.internalCode)
        }
    }
}