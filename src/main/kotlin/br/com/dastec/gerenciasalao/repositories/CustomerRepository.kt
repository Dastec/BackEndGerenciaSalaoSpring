package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.CustomerModel
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerRepository: JpaRepository<CustomerModel, Long> {

    @Query(value = "select u from CustomerModel u where u.beautySalon = ?1 AND u.clientKey = ?2")
    fun findByClientKey(salon: BeautySalonModel, clientKey: String):Optional<CustomerModel>

    @Query(value = "select u from CustomerModel u where u.beautySalon = ?1 AND u.fullName like %?2%")
    fun findByFullNameAndSalon(salon: BeautySalonModel, name: String): List<CustomerModel>


    @Query(value = "select u from CustomerModel u where u.beautySalon = ?1 AND u.status = ?2")
    fun findByStatusAndSalonModel(salon: BeautySalonModel, name: String):List<CustomerModel>

    @Query(value = "select u from CustomerModel u where u.beautySalon = ?1 AND u.cpf = ?2")
    fun existsByCpf(salon: BeautySalonModel, cpf: String): CustomerModel

    @Query(value = "select u from CustomerModel u where u.beautySalon = ?1 AND u.idCustomer = ?2")
    fun findByIdAndSalon(salon: BeautySalonModel, idCustomer: Long): CustomerModel?

    fun findAllByBeautySalon(salon: BeautySalonModel, pageable: Pageable): Page<CustomerModel>
}
