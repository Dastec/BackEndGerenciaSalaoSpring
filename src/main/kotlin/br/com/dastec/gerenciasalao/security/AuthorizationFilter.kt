package br.com.dastec.gerenciasalao.security

import br.com.dastec.gerenciasalao.exceptions.AuthenticationException
import br.com.dastec.gerenciasalao.models.UserInformationModel
import com.google.gson.Gson
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(
    authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil
    ): BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorization = request.getHeader("Authorization")

        if (authorization != null && authorization.startsWith("Bearer ")){
            authorization.split("")
        }

    }

    private fun getAuthentication(token: String){
        if (!jwtUtil.isValidToken(token)){
            throw AuthenticationException("Token invalido", "999")
        }

        val subject = jwtUtil.getSubject(token)


    }
}