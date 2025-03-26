package br.com.bird.servicebirdad.application.service

import br.com.bird.servicebirdad.infrastructure.adapter.database.entity.UserEntity
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant.now
import java.util.Date.from

@Service
class TokenService {

    @Value("\${api.security.token.secret}")
    private lateinit var secret: String

    val mapper = jacksonObjectMapper()

    companion object {
        fun mapper(user: UserEntity): TokenClaims {
            return TokenClaims(
                role = user.authorities.map { it.authority },
                id = user.id!!,
                companyId = user.company.id!!,
                name = user.name,
                email = user.email
            )
        }
    }

    fun validate(token: String): String {
        return try {
            getTokenSubject(token.replace("Bearer ", ""))
        } catch (ex: JWTVerificationException) {
            throw TokenExpiredException("Token expired", now())
        }
    }

    fun getTokenSubject(token: String): String {
        val algorithm = Algorithm.HMAC256(secret)
        return JWT.require(algorithm)
            .withIssuer("bird")
            .build()
            .verify(token.replace("Bearer ", ""))
            .subject
    }

    fun getPayload(token: String): String {
        val algorithm = Algorithm.HMAC256(secret)
        val decodedJWT = JWT.require(algorithm)
            .withIssuer("bird")
            .build()
            .verify(token.replace("Bearer ", ""))
        return decodedJWT.getClaim("data").asString()
    }

    fun generate(userEntity: UserEntity): String {
        val algorithm = Algorithm.HMAC256(secret)
        val claim = mapper(userEntity)
        val claimJson = mapper.writeValueAsString(claim)

        return JWT.create()
            .withIssuer("bird")
            .withSubject(userEntity.email)
            .withClaim("data", claimJson)
            .withExpiresAt(from(now().plusSeconds(3600)))
            .sign(algorithm)
    }

    fun getTokenData(token: String): TokenClaims {
        return mapper.readValue(getPayload(token), TokenClaims::class.java)
    }
}

data class TokenClaims(
    val role: List<String>,
    val id: Long,
    val name: String,
    val email: String,
    val companyId: Long,
)