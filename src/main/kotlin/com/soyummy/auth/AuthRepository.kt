package com.soyummy.auth

import org.springframework.stereotype.Repository
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional

@Repository
interface AuthRepository : MongoRepository<User, String> {
    fun findByEmail(email: String): Optional<User>
    fun findByToken(token: String): Optional<User>
}