package com.soyummy.subscribes

import com.soyummy.subscribes.dto.SubscribeCreateDto
import com.soyummy.subscribes.dto.SubscribeUpdateDto
import com.soyummy.auth.User

interface SubscribeService {
    fun getAllSubscribes(): List<Subscribe>
    fun getSubscribeById(id: String): Subscribe
    fun findByEmail(email: String): List<Subscribe>
    fun isSubscribed(ownerId: String, email: String): Boolean
    fun getSubscribeById(id: String, currentUser: User?): Subscribe
    fun isSubscribed(userId: String, email: String, currentUser: User?): Boolean
    fun createSubscribe(dto: SubscribeCreateDto, currentUser: User?): Subscribe
    fun updateSubscribe(id: String, dto: SubscribeUpdateDto, currentUser: User?): Subscribe
    fun unsubscribe(userId: String, email: String, currentUser: User?): Void?
    fun deleteSubscribe(id: String, currentUser: User?)
}