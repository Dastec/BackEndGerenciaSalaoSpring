package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.NotFoundException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.BeautySalonModel
import br.com.dastec.gerenciasalao.models.UserModel
import br.com.dastec.gerenciasalao.models.enums.Role
import br.com.dastec.gerenciasalao.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository,
                  private val bCrypt: BCryptPasswordEncoder) {

    val LOGGER = LoggerFactory.getLogger(javaClass)

    fun createUserAdmin(user: UserModel) {
        val userCopy = user.copy(
            roles = setOf(Role.USER, Role.ADMIN),
            password = bCrypt.encode(user.password)
        )
        userRepository.save(userCopy)
        LOGGER.info("Usuário administrador criado com sucesso!")
        LOGGER.info("Final do método de criação de usuário administrador!")
    }

    fun createUser(user: UserModel) {
        val userCopy = user.copy(
            roles = setOf(Role.USER),
            password = bCrypt.encode(user.password)
        )
        userRepository.save(userCopy)
        LOGGER.info("Usuário criado com sucesso!")
        LOGGER.info("Final do método de criação de usuário!")
    }

    fun updateInformation(user: UserModel) {
        userRepository.save(user)
        LOGGER.info("Usuário atualizado com sucesso!")
        LOGGER.info("Final do método de atualização de usuário!")
    }

    fun suspendUser(user: UserModel) {
        userRepository.save(user)
        LOGGER.info("Usuário suspenso com sucesso!")
        LOGGER.info("Final do método de suspender usuário!")
    }

    fun deleteUser(user: UserModel) {
        userRepository.save(user)
        LOGGER.info("Usuário excluído com sucesso!")
        LOGGER.info("Final do método de exclusão de usuário!")
    }

    fun activateUser(user: UserModel) {
        userRepository.save(user)
        LOGGER.info("Usuário ativado com sucesso!")
        LOGGER.info("Final do método de ativar usuário!")
    }

    fun addAdmin(user: UserModel) {
        val userCopy = user.copy(
            roles = setOf(Role.USER, Role.ADMIN)
        )
        userRepository.save(userCopy)
        LOGGER.info("Privilégio adminiatrador adicionado com sucesso!")
        LOGGER.info("Final do método de adicionar privilégio administrador!")
    }

    fun removeAdmin(user: UserModel) {
        val userCopy = user.copy(
            roles = setOf(Role.USER)
        )
        userRepository.save(userCopy)
        LOGGER.info("Privilégio adminiatrador removido com sucesso!")
        LOGGER.info("Final do método de remover privilégio administrador!")
    }

    fun updatePassword(user: UserModel) {
        val userCopy = user.copy(
            password = bCrypt.encode(user.password)
        )
        userRepository.save(user)
        LOGGER.info("Senha atualizada com sucesso!")
        LOGGER.info("Final do método de alteração de senha!")
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










