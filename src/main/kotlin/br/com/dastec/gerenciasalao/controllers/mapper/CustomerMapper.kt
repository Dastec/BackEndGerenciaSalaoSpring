package br.com.dastec.gerenciasalao.controllers.mapper

import br.com.dastec.gerenciasalao.controllers.responses.CustomerResponse
import br.com.dastec.gerenciasalao.controllers.responses.PhoneNumberResponse
import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.services.PhoneNumberService
import org.springframework.stereotype.Component

@Component
class CustomerMapper(val phoneNumberService: PhoneNumberService) {

    fun toCustomerResponse(customer: CustomerModel): CustomerResponse {
        val phoneNumbers = phoneNumberService.findAllByCustomerId(customer.idCustomer!!)!!

        val phoneNumbersResponse: MutableList<PhoneNumberResponse> = mutableListOf()
        for (phoneNumber in phoneNumbers) {
            phoneNumbersResponse.add(phoneNumber.toPhoneNumberResponse())
        }

        return CustomerResponse(
            idCustomer = customer.idCustomer!!,
            alias = customer.alias,
            fullName = customer.fullName,
            cpf = customer.cpf,
            birthDate = customer.birthDate,
            clientKey = customer.clientKey,
            phoneNumber = phoneNumbersResponse,
            photo = customer.photo
        )
    }

    fun toListCustomerResponse(customers: List<CustomerModel>): List<CustomerResponse> {

        val customersReponse: MutableList<CustomerResponse> = mutableListOf()

        for (customer in customers) {
            val phoneNumbers = phoneNumberService.findAllByCustomerId(customer.idCustomer!!)!!

            val phoneNumbersResponse: MutableList<PhoneNumberResponse> = mutableListOf()
            for (phoneNumber in phoneNumbers) {
                phoneNumbersResponse.add(phoneNumber.toPhoneNumberResponse())
            }

            val customerResponse = CustomerResponse(
                idCustomer = customer.idCustomer!!,
                alias = customer.alias,
                fullName = customer.fullName,
                cpf = customer.cpf,
                birthDate = customer.birthDate,
                clientKey = customer.clientKey,
                phoneNumber = phoneNumbersResponse,
                photo = customer.photo
            )

            customersReponse.add(customerResponse)
        }

        return customersReponse
    }
}