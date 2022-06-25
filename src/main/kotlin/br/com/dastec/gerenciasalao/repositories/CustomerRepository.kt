package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.CustomerModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CustomerRepository: JpaRepository<CustomerModel, Long> {

    fun findByClientKey(clientKey: String):Optional<CustomerModel>

    @Query(value = "select u from CustomerModel u where u.fullName like %?1%")
    fun findByFullNameContainingIgnoreCase(name: String): List<CustomerModel>

    fun findByFullNameLike(name: String): List<CustomerModel>

    fun findByStatus(status: String):List<CustomerModel>

    fun existsByCpf(cpf: String): Boolean
}