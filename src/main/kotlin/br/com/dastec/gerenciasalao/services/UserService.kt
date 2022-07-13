package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.models.UserModel
import br.com.dastec.gerenciasalao.models.enums.Role
import br.com.dastec.gerenciasalao.repositories.UserRepository
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository,
                  private val bCrypt: BCryptPasswordEncoder) {

    fun createUserAdmin(user: UserModel) {
        val userCopy = user.copy(
            roles = setOf(Role.USER, Role.ADMIN),
            password = bCrypt.encode(user.password)
        )
        userRepository.save(userCopy)
    }

    fun createUser(user: UserModel) {
        val userCopy = user.copy(
            roles = setOf(Role.USER),
            password = bCrypt.encode(user.password)
        )
        userRepository.save(userCopy)
    }

    fun updateInformation(user: UserModel) {
        userRepository.save(user)
    }

    fun suspendUser(user: UserModel) {
        userRepository.save(user)
    }

    fun deleteUser(user: UserModel) {
        userRepository.save(user)
    }

    fun activateUser(user: UserModel) {
        userRepository.save(user)
    }

    fun addAdmin(user: UserModel) {
        val userCopy = user.copy(
            roles = setOf(Role.USER, Role.ADMIN)
        )
        userRepository.save(userCopy)
    }

    fun removeAdmin(user: UserModel) {
        val userCopy = user.copy(
            roles = setOf(Role.USER)
        )
        userRepository.save(userCopy)
    }

    fun updatePassword(user: UserModel) {
        val userCopy = user.copy(
            password = bCrypt.encode(user.password)
        )
        userRepository.save(user)
    }

    fun findById(salon: BeautySalonModel, id: Long): UserModel {
        return userRepository.findByIdUser(salon, id) ?:
            throw NotFoundException(Errors.GS101.message.format(id), Errors.GS101.internalCode)
    }

    fun findAll(): List<UserModel> {
        return userRepository.findAll()
    }

    fun findByUserName(userName: String): UserModel? {
        return userRepository.findByUserName(userName)
    }
}










