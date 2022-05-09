package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.controllers.extensions.toPhoneNumberModel
import br.com.dastec.gerenciasalao.controllers.requests.customers.PostPhoneRequest
import br.com.dastec.gerenciasalao.controllers.requests.customers.PutPhoneRequest
import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.models.PhoneNumberModel
import br.com.dastec.gerenciasalao.repositories.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(private val customerRepository: CustomerRepository, private val phoneNumberService: PhoneNumberService) {

    fun create(customerModel: CustomerModel, phones: List<PostPhoneRequest>){
        val customer = customerRepository.save(customerModel)

        for (phone in phones){
            phoneNumberService.create(phone.toPhoneNumberModel(customer))
        }
    }

    fun update(customerModel: CustomerModel, phones: List<PutPhoneRequest>){
        if (customerRepository.existsById(customerModel.idCustomer)){
            val customer = customerRepository.save(customerModel)

            val previusPhones = phoneNumberService.findAllByCustomerId(customer.idCustomer!!)

            for (phone in phones){
                phoneNumberService.update(phone.toPhoneNumberModel(previusPhones!![phones.indexOf(phone)]))
            }
        }else{
            throw Exception("Usuario não encontrado")
        }
    }

    fun delete(customerModel: CustomerModel){
        customerRepository.save(customerModel)
    }

    fun findById(id: Long): CustomerModel {
        return customerRepository.findById(id).orElseThrow{
            NotFoundException(Errors.GS101.message.format(id), Errors.GS101.internalCode)
        }
    }

    fun findAll():List<CustomerModel>{
        return customerRepository.findAll()
    }

    fun findByClientKey(clientKey: String): CustomerModel {
        return customerRepository.findByClientKey(clientKey).orElseThrow{
            NotFoundException(Errors.GS101.message.format(clientKey), Errors.GS101.internalCode)
        }
    }

    fun findByNameContaining(name: String):List<CustomerModel>{
        return customerRepository.findByFullNameContainingIgnoreCase(name)
    }

    fun findByStatus(status: String): List<CustomerModel>{
        return customerRepository.findByStatus(status)
    }

    fun cpfAvaliable(cpf: String): Boolean {
        return !customerRepository.existsByCpf(cpf)
    }
}