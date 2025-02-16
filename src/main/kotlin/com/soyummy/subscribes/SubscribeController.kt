package com.soyummy.subscribes

import org.springframework.web.bind.annotation.*
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.AccessDeniedException
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import jakarta.validation.Valid

import com.soyummy.subscribes.dto.SubscribeCreateDto
import com.soyummy.subscribes.dto.SubscribeUpdateDto
import com.soyummy.auth.User
import com.soyummy.auth.Role
import com.soyummy.errors.UnauthorizedException
import com.soyummy.constants.Constants.VERSION

@RestController
@RequestMapping("${VERSION}/subscribes")
class SubscribeController(private val subscribeService: SubscribeService) {
    // GET http://localhost:8080/api/v1/subscribes/
    @Secured("ROLE_ADMIN")
    @GetMapping
    fun getAllSubscribes(): ResponseEntity<List<Subscribe>> =
        ResponseEntity.ok(subscribeService.getAllSubscribes())

    // GET http://localhost:8080/api/v1/subscribes/search?email=anna
    @Secured("ROLE_ADMIN")
    @GetMapping("/search")
    fun findByEmail(@RequestParam email: String): ResponseEntity<List<Subscribe>> =
        ResponseEntity.ok(subscribeService.findByEmail(email))

    // GET http://localhost:8080/api/v1/subscribes/{id}
    @GetMapping("/{id}")
    fun getSubscribeById(
        @PathVariable id: String,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<Subscribe> {
        if (id != currentUser?.id && currentUser?.role != Role.ADMIN)
            throw AccessDeniedException("User ID does not match the authenticated user's ID.")

        val subscribe = subscribeService.getSubscribeById(id)
        return ResponseEntity.ok(subscribe)
    }

    // GET http://localhost:8080/api/v1/subscribes/isSubscribed?userId={userId}&email={email}
    @GetMapping("/isSubscribed")
    fun isSubscribed(
        @RequestParam userId: String,
        @RequestParam email: String,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<Boolean> {
        if (userId != currentUser?.id && currentUser?.role != Role.ADMIN)
            throw AccessDeniedException("User ID does not match the authenticated user's ID.")

        val isSubscribed = subscribeService.isSubscribed(userId, email)
        return ResponseEntity.ok(isSubscribed)
    }

    // POST http://localhost:8080/api/v1/subscribes
    @PostMapping
    fun createSubscribe(
        @Valid @RequestBody subscribeCreateDto: SubscribeCreateDto,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<Subscribe> {
        if (currentUser?.id == null) throw UnauthorizedException("User is not authenticated")

        subscribeCreateDto.owner = currentUser.id
        val subscribe = subscribeService.createSubscribe(subscribeCreateDto)
        return ResponseEntity.ok(subscribe)
    }

    // PUT http://localhost:8080/api/v1/subscribes/{id}
    @PutMapping("/{id}")
    fun updateSubscribe(
        @PathVariable id: String,
        @Valid @RequestBody subscribeUpdateDto: SubscribeUpdateDto,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<Subscribe> {
        if (currentUser?.id == null) throw UnauthorizedException("User is not authenticated")

        val existingSubscribe = subscribeService.getSubscribeById(id)
        if (existingSubscribe.ownerId != currentUser.id)
            throw AccessDeniedException("Subscription not found for current user")

        val subscribe = subscribeService.updateSubscribe(id, subscribeUpdateDto)
        return ResponseEntity.ok(subscribe)
    }

    // POST http://localhost:8080/api/v1/subscribes/unsubscribe?userId={userId}&email={email}
    @PostMapping("/unsubscribe")
    fun unsubscribe(
        @RequestParam userId: String,
        @RequestParam email: String,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<Void> {
        if (currentUser?.id == null) throw UnauthorizedException("User is not authenticated")

        if (!subscribeService.isSubscribed(currentUser.id, email))
            throw AccessDeniedException("Subscription not found for current user")

        subscribeService.unsubscribe(userId, email)
        return ResponseEntity.noContent().build()
    }

    // DELETE http://localhost:8080/api/v1/subscribes/{id}
    @DeleteMapping("/{id}")
    fun deleteSubscribe(
        @PathVariable id: String,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<Void> {
        if (currentUser?.role != Role.ADMIN && id != currentUser?.id)
            throw AccessDeniedException("User ID does not match the authenticated user's ID.")

        subscribeService.deleteSubscribe(id)
        return ResponseEntity.ok().build()
    }
}