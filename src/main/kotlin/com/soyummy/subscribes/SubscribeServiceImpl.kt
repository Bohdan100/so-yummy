package com.soyummy.subscribes

import org.springframework.stereotype.Service
import org.bson.types.ObjectId
import org.springframework.security.access.AccessDeniedException
import com.soyummy.subscribes.dto.SubscribeCreateDto
import com.soyummy.subscribes.dto.SubscribeUpdateDto
import com.soyummy.auth.User
import com.soyummy.auth.Role
import com.soyummy.exception.types.ResourceNotFoundException
import com.soyummy.exception.types.UnauthorizedException

@Service
class SubscribeServiceImpl(
    private val subscribeRepository: SubscribeRepository
) : SubscribeService {

    override fun getAllSubscribes(): List<Subscribe> = subscribeRepository.findAll()

    override fun getSubscribeById(id: String): Subscribe = subscribeRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Subscription not found with id: $id") }

    override fun findByEmail(email: String): List<Subscribe> =
        subscribeRepository.findByEmailContainingIgnoreCase(email)

    override fun isSubscribed(ownerId: String, email: String): Boolean =
        subscribeRepository.existsByOwnerIdAndEmail(ObjectId(ownerId), email)

    override fun getSubscribeById(id: String, currentUser: User?): Subscribe {
        if (id != currentUser?.id && currentUser?.role != Role.ADMIN)
            throw AccessDeniedException("User ID does not match the authenticated user's ID.")
        return getSubscribeById(id)
    }

    override fun isSubscribed(userId: String, email: String, currentUser: User?): Boolean {
        if (userId != currentUser?.id && currentUser?.role != Role.ADMIN)
            throw AccessDeniedException("User ID does not match the authenticated user's ID.")
        return isSubscribed(userId, email)
    }

    override fun createSubscribe(dto: SubscribeCreateDto, currentUser: User?): Subscribe {
        if (currentUser?.id == null) throw UnauthorizedException("User is not authenticated")
        dto.owner = currentUser.id

        if (dto.owner.isBlank() || dto.email.isBlank())
            throw IllegalArgumentException("Missing required fields when adding a new subscription")

        val subscribe = Subscribe(ownerId = dto.owner, email = dto.email)
        return subscribeRepository.save(subscribe)
    }

    override fun updateSubscribe(id: String, dto: SubscribeUpdateDto, currentUser: User?): Subscribe {
        if (currentUser?.id == null) throw UnauthorizedException("User is not authenticated")

        val existingSubscribe = getSubscribeById(id)
        if (existingSubscribe.ownerId != currentUser.id)
            throw AccessDeniedException("Subscription not found for current user")

        val updatedSubscribe = existingSubscribe.copy(
            email = dto.email ?: existingSubscribe.email
        )
        return subscribeRepository.save(updatedSubscribe)
    }

    override fun unsubscribe(userId: String, email: String, currentUser: User?): Void? {
        if (currentUser?.id == null) throw UnauthorizedException("User is not authenticated")

        if (!isSubscribed(currentUser.id, email))
            throw AccessDeniedException("Subscription not found for current user")

        subscribeRepository.deleteByOwnerIdAndEmail(ObjectId(userId), email)
        return null
    }

    override fun deleteSubscribe(id: String, currentUser: User?) {
        if (currentUser?.role != Role.ADMIN && id != currentUser?.id)
            throw AccessDeniedException("User ID does not match the authenticated user's ID.")

        if (!subscribeRepository.existsById(id)) {
            throw ResourceNotFoundException("Subscription not found with id: $id")
        }
        subscribeRepository.deleteById(id)
    }
}