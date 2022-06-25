package br.com.dastec.gerenciasalao.repositories

import br.com.dastec.gerenciasalao.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserModel, Long> {

    fun findByUserName(userName: String): UserModel?

    fun findByIdUser(idUser: Long): UserModel

}