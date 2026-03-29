package com.soyummy.token

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Value
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys

import java.util.Date
import javax.crypto.SecretKey
import com.soyummy.auth.User

@Component
class JwtService {
    @Value("\${token.signing.key}")
    private lateinit var jwtSigningKey: String

    fun generateToken(email: String): String {
        val now = Date()
        val expirationDate = Date(now.time + 1000 * 60 * 60 * 2)

        return Jwts.builder()
            .subject(email)
            .issuedAt(now)
            .expiration(expirationDate)
            .signWith(getSigningKey())
            .compact()
    }

    private fun getSigningKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(jwtSigningKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun extractUserName(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .payload

        return claimsResolver(claims)
    }

    fun isTokenValid(token: String, user: User): Boolean {
        val username = extractUserName(token)
        return (username == user.email) && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractClaim(token, Claims::getExpiration).before(Date())
    }
}