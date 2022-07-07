package br.com.dastec.gerenciasalao.services

import br.com.dastec.gerenciasalao.exceptions.AuthenticationException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.repositories.UserRepository
import br.com.dastec.gerenciasalao.security.UserModelDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsUserModelService(
    private val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(userName: String): UserDetails {
        val userModel = userRepository.findByUserName(userName)

        if (userModel == null){
            throw AuthenticationException(Errors.GSL001.message.format(userName), Errors.GSL001.internalCode)
        }

        return UserModelDetails(userModel)
    }

}