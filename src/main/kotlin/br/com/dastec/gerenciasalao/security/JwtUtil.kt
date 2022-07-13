package br.com.dastec.gerenciasalao.security

import br.com.dastec.gerenciasalao.exceptions.AuthenticationException
import br.com.dastec.gerenciasalao.exceptions.enums.Errors
import br.com.dastec.gerenciasalao.models.UserInformationModel
import br.com.dastec.gerenciasalao.models.UserModel
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class JwtUtil(@Value("\${jwt.expiration}")
              private val expirationMilliseconds: Long? = null,

              @Value("\${jwt.secret}")
              private val secret : String? = null,

              var expirationSeconds: Long? = null
) {

    init {
        expirationSeconds = expirationMilliseconds!! / 1000
    }

    fun generateToken(userModel: UserModel): String{
        val subject = userModel.userName + userModel.userKey
        return Jwts.builder()
            .setSubject(userModel.userName)
            .claim("idUser", userModel.idUser)
            .claim("name", userModel.beautySalon.name)
            .claim("userKey", userModel.userKey)
            .claim("email", userModel.email)
            .claim("phoneNumber", userModel.phoneNumber)
            .claim("personId", userModel.beautySalon.idSalon)
            .setExpiration(Date(System.currentTimeMillis()+ expirationMilliseconds!!))
            .signWith(SignatureAlgorithm.HS512, secret!!.toByteArray())
            .compact()
    }


    fun isValidToken(token: String): Boolean {
        val claims = getClaims(token)
        if (claims.subject == null || claims.expiration == null || Date().after(claims.expiration)) {
            return false
        }
        return true
    }

    private fun getClaims(token: String): Claims {
        try {
            return Jwts.parser().setSigningKey(secret!!.toByteArray()).parseClaimsJws(token).body

        }catch (ex: Exception){
            throw AuthenticationException(Errors.GSL003.message, Errors.GSL003.internalCode)
        }
    }

     fun getUserInformation(token: String): UserInformationModel{
        val body = getClaims(token)//Jwts.parser().setSigningKey(secret!!.toByteArray()).parseClaimsJws(token).body
        return UserInformationModel(
            idUser = body.get("idUser").toString().toLong(),
            sub = body.get("sub").toString(),
            name = body.get("name").toString(),
            userKey = body.get("userKey").toString(),
            email = body.get("email").toString(),
            phoneNumber = body.get("phoneNumber").toString(),
            exp = body.get("exp").toString().toLong(),
            salonId = body.get("personId").toString().toLong()
        )
    }

    fun getSubject(token: String): String {
        return getClaims(token).subject
    }

    fun convertTime(): String {
        print(expirationSeconds)
        val currentTime = LocalDateTime.now().plusSeconds(expirationSeconds!!)
        return SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
    }

    fun getExpirationMilliseconds(): Long{
        return expirationMilliseconds!!
    }
}