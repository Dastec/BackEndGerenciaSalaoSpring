package br.com.dastec.gerenciasalao.security

import br.com.dastec.gerenciasalao.models.UserModel
import br.com.dastec.gerenciasalao.models.enums.SalonStatus
import br.com.dastec.gerenciasalao.models.enums.UserStatus
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserModelDetails(val userModel: UserModel): UserDetails{
    val userName: String = userModel.userName

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = userModel.roles.map {
        SimpleGrantedAuthority(it.description)
    }.toMutableList()

    override fun getPassword(): String = userModel.password

    override fun getUsername(): String = userModel.userName

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean =  userModel.beautySalon.status == SalonStatus.ACTIVE && userModel.status == UserStatus.ACTIVE
}
