package com.soyummy.subscribes

import org.springframework.stereotype.Repository
import org.springframework.data.mongodb.repository.MongoRepository
import org.bson.types.ObjectId

@Repository
interface SubscribeRepository: MongoRepository<Subscribe, String> {
    fun findByEmailContainingIgnoreCase(email: String): List<Subscribe>
    fun existsByOwnerIdAndEmail(ownerId: ObjectId, email: String): Boolean
    fun deleteByOwnerIdAndEmail(ownerId: ObjectId, email: String)
}