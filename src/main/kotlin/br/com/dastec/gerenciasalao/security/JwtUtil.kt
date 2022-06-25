package br.com.dastec.gerenciasalao.security

import br.com.dastec.gerenciasalao.exceptions.AuthenticationException
import br.com.dastec.gerenciasalao.models.UserInformationModel
import br.com.dastec.gerenciasalao.models.UserModel
import com.google.gson.Gson
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtUtil {

    @Value("\${jwt.expiration}")
    private val expiration: Long? = null

    @Value("\${jwt.secret}")
    private val secret : String? = null


    fun isValidToken(token: String): Boolean {
        val claims = getClain(token)
        if (claims.subject == null || claims.expiration == null || Date().after(claims.expiration)) {
            return false
        }
        return true
    }

    private fun getClain(token: String): Claims {
        try {
            return Jwts.parser().setSigningKey(secret!!.toByteArray()).parseClaimsJws(token).body
        }catch (ex: Exception){
            throw AuthenticationException("Token Invalido", "999")
        }
    }

    private fun getUserInformation(token: String): UserInformationModel{
        return Gson().fromJson(getClain(token).subject, UserInformationModel::class.java)
    }

    fun getSubject(token: String): String {
        return getClain(token).subject
    }

    fun generateToken(userModel: UserModel): String{
        val subject = userModel.userName + userModel.userKey
        return Jwts.builder()
            .claim("name", userModel.person.name)
            .claim("fiscalIdentification", userModel.userName)
            .claim("userKey", userModel.userKey)
            .claim("email", userModel.email)
            .claim("phoneNumber", userModel.phoneNumber)
            .setExpiration(Date(System.currentTimeMillis()+ expiration!!))
            .signWith(SignatureAlgorithm.HS512, secret!!.toByteArray())
            .compact()
    }


}