package com.soyummy.subscribes

import org.springframework.stereotype.Service
import org.bson.types.ObjectId
import com.soyummy.subscribes.dto.SubscribeCreateDto
import com.soyummy.subscribes.dto.SubscribeUpdateDto
import com.soyummy.errors.ResourceNotFoundException

@Service
class SubscribeService(private val subscribeRepository: SubscribeRepository) {

    fun getAllSubscribes(): List<Subscribe> = subscribeRepository.findAll()

    fun getSubscribeById(id: String): Subscribe = subscribeRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Subscription not found with id: $id") }


    fun findByEmail(email: String): List<Subscribe> =
        subscribeRepository.findByEmailContainingIgnoreCase(email)

    fun isSubscribed(ownerId: String, email: String): Boolean =
        subscribeRepository.existsByOwnerIdAndEmail(ObjectId(ownerId), email)

    fun createSubscribe(subscribeCreateDto: SubscribeCreateDto): Subscribe {
        if (subscribeCreateDto.owner.isBlank() || subscribeCreateDto.email.isBlank())
            throw IllegalArgumentException("Missing required fields when adding a new subscription")

        val subscribe = Subscribe(
            ownerId = subscribeCreateDto.owner,
            email = subscribeCreateDto.email
        )

        return subscribeRepository.save(subscribe)
    }

    fun updateSubscribe(id: String, subscribeUpdateDto: SubscribeUpdateDto): Subscribe {
        val existingSubscribe = getSubscribeById(id)
        val updatedSubscribe = existingSubscribe.copy(
            email = subscribeUpdateDto.email ?: existingSubscribe.email
        )

        return subscribeRepository.save(updatedSubscribe)
    }

    fun unsubscribe(userId: String, email: String) =
        subscribeRepository.deleteByOwnerIdAndEmail(ObjectId(userId), email)

    fun deleteSubscribe(id: String) {
        if (!subscribeRepository.existsById(id)) {
            throw ResourceNotFoundException("Subscription not found with id: $id")
        }
        subscribeRepository.deleteById(id)
    }
}