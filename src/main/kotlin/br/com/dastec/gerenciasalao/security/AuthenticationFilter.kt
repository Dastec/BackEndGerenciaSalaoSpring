package br.com.dastec.gerenciasalao.security

import br.com.dastec.gerenciasalao.controllers.requests.security.LoginRequest
import br.com.dastec.gerenciasalao.controllers.responses.LoginResponse
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.repositories.UserRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.PrintWriter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil,

): UsernamePasswordAuthenticationFilter(authenticationManager){

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val loginRequest = jacksonObjectMapper().readValue(request.inputStream, LoginRequest::class.java)
            val authToken = UsernamePasswordAuthenticationToken(loginRequest.userName, loginRequest.password)
            return authenticationManager.authenticate(authToken)
        }catch (ex: Exception){
            print(ex.toString())
            throw InternalAuthenticationServiceException(Errors.GSL002.message)
        }
    }

    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication) {
        val userName = (authResult.principal as UserModelDetails).username
        val userModel = userRepository.findByUserName(userName)
        val token = jwtUtil.generateToken(userModel!!)
        response.addHeader("Authorization", "Bearer $token")
        val loginResponse = LoginResponse(
            accessToken = token,
            expiresIn = jwtUtil.getExpirationMilliseconds().toString() + " ms",
            dueDateTime = jwtUtil.convertTime().toString(),
            tokenType = "Bearer",
        )

        val loginResponseString = Gson().toJson(loginResponse)

        val out: PrintWriter = response.writer

        response.setContentType("application/json")
        response.setCharacterEncoding("UTF-8")

        out.print(loginResponseString)
        out.flush()

    }
}