package com.soyummy.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.io.Decoders
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Value
import java.security.Key
import java.util.Date
import java.util.function.Function
import com.soyummy.auth.User

@Component
class JwtService {
    @Value("\${token.signing.key}")
    private lateinit var jwtSigningKey: String

    fun generateToken(email: String): String {
        val now = Date()
        val expirationDate = Date(now.time + 1000 * 60 * 60 * 2)

        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(getSigningKey())
            .compact()
    }

    private fun getSigningKey(): Key {
        val keyBytes = Decoders.BASE64.decode(jwtSigningKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun extractUserName(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = Jwts.parser()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .body

        return claimsResolver.apply(claims)
    }

    fun isTokenValid(token: String, user: User): Boolean {
        val username = extractUserName(token)
        return username == user.email && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractClaim(token, Claims::getExpiration).before(Date())
    }
}