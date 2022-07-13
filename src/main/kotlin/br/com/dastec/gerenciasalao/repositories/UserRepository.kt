package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository: JpaRepository<UserModel, Long> {

    fun findByUserName(userName: String): UserModel?

    @Query(value = "SELECT u FROM UserModel u WHERE u.beautySalon = ?1 AND u.idUser = ?2")
    fun findByIdUser(salon: BeautySalonModel, idUser: Long): UserModel?

}