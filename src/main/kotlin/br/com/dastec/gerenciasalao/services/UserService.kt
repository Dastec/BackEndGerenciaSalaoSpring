package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.UserModel
import br.com.dastec.gerenciasalao.models.enums.Role
import br.com.dastec.gerenciasalao.repositories.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository,
                  private val bCrypt: BCryptPasswordEncoder) {

    fun createUser(user: UserModel) {
        val userCopy = user.copy(
            roles = setOf(Role.USER),
            password = bCrypt.encode(user.password)
        )
        userRepository.save(userCopy)
    }

    fun updateUser(user: UserModel) {
        userRepository.save(user)
    }

    fun findById(id: Long): UserModel {
        return userRepository.findById(id).orElseThrow {
            throw NotFoundException(Errors.GS101.message.format(id), Errors.GS101.internalCode)
        }
    }

    fun findAll(): List<UserModel> {
        return userRepository.findAll()
    }
}









