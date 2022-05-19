package br.com.dastec.gerenciasalao.controllers.extensions

import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.models.enums.CustomerStatus
import br.com.dastec.gerenciasalao.controllers.requests.customers.PostCustomerModelRequest
import br.com.dastec.gerenciasalao.controllers.requests.customers.PostPhoneRequest
import br.com.dastec.gerenciasalao.controllers.requests.customers.PutCustomerModelRequest
import br.com.dastec.gerenciasalao.controllers.requests.customers.PutPhoneRequest
import br.com.dastec.gerenciasalao.controllers.responses.CustomerResponse
import br.com.dastec.gerenciasalao.models.PhoneNumberModel
import br.com.dastec.gerenciasalao.services.PhoneNumberService
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import java.util.*

fun PostCustomerModelRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(
        alias = this.alias,
        fullName = this.fullName,
        cpf = this.cpf,
        birthDate = this.birthDate,
        photo = this.photo,
        status = CustomerStatus.ATIVO,
        clientKey = UUID.randomUUID().toString(),
        createdAt = LocalDate.now()
    )
}

fun PutCustomerModelRequest.toCustomerModel(customerModel: CustomerModel): CustomerModel {
    return CustomerModel(
        idCustomer = customerModel.idCustomer,
        alias = this.alias,
        fullName = this.fullName,
        cpf = this.cpf,
        birthDate = this.birthDate,
        photo = this.photo,
        status = customerModel.status!!,
        clientKey = customerModel.clientKey!!,
        createdAt = customerModel.createdAt
    )
}

fun deleteCustomer(customerModel: CustomerModel): CustomerModel {
    return CustomerModel(
        idCustomer = customerModel.idCustomer,
        alias = customerModel.alias,
        fullName = customerModel.fullName,
        cpf = customerModel.cpf,
        birthDate = customerModel.birthDate,
        photo = customerModel.photo,
        status = CustomerStatus.EXCLUIDO,
        clientKey = customerModel.clientKey!!,
        createdAt = customerModel.createdAt
    )
}

fun PostPhoneRequest.toPhoneNumberModel(customerModel: CustomerModel): PhoneNumberModel {
    return PhoneNumberModel(
        type = this.type,
        ddd = this.ddd,
        number = this.number,
        customerModel = customerModel
    )
}

fun PutPhoneRequest.toPhoneNumberModel(previusPhone: PhoneNumberModel): PhoneNumberModel {
    return PhoneNumberModel(
        idPhone = previusPhone.idPhone,
        type = this.type ?: previusPhone.type,
        ddd = this.ddd ?: previusPhone.ddd,
        number = this.number ?: previusPhone.number,
        customerModel = previusPhone.customerModel
    )
}



