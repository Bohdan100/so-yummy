package com.soyummy.subscribes

import org.springframework.web.bind.annotation.*
import org.springframework.security.access.annotation.Secured
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import jakarta.validation.Valid

import com.soyummy.subscribes.dto.SubscribeCreateDto
import com.soyummy.subscribes.dto.SubscribeUpdateDto
import com.soyummy.auth.User
import com.soyummy.constants.Constants.VERSION

@RestController
@RequestMapping("${VERSION}/subscribes")
class SubscribeController(private val subscribeService: SubscribeService) {

    // GET http://localhost:8080/api/v1/subscribes
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
    ): ResponseEntity<Subscribe> =
        ResponseEntity.ok(subscribeService.getSubscribeById(id, currentUser))

    // GET http://localhost:8080/api/v1/subscribes/isSubscribed?userId={userId}&email={email}
    @GetMapping("/isSubscribed")
    fun isSubscribed(
        @RequestParam userId: String,
        @RequestParam email: String,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<Boolean> =
        ResponseEntity.ok(subscribeService.isSubscribed(userId, email, currentUser))

    // POST http://localhost:8080/api/v1/subscribes
    @PostMapping
    fun createSubscribe(
        @Valid @RequestBody subscribeCreateDto: SubscribeCreateDto,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<Subscribe> =
        ResponseEntity.ok(subscribeService.createSubscribe(subscribeCreateDto, currentUser))

    // PUT http://localhost:8080/api/v1/subscribes/{id}
    @PutMapping("/{id}")
    fun updateSubscribe(
        @PathVariable id: String,
        @Valid @RequestBody subscribeUpdateDto: SubscribeUpdateDto,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<Subscribe> =
        ResponseEntity.ok(subscribeService.updateSubscribe(id, subscribeUpdateDto, currentUser))

    // POST http://localhost:8080/api/v1/subscribes/unsubscribe?userId={userId}&email={email}
    @PostMapping("/unsubscribe")
    fun unsubscribe(
        @RequestParam userId: String,
        @RequestParam email: String,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<Void> {
        subscribeService.unsubscribe(userId, email, currentUser)
        return ResponseEntity.noContent().build()
    }

    // DELETE http://localhost:8080/api/v1/subscribes/{id}
    @DeleteMapping("/{id}")
    fun deleteSubscribe(
        @PathVariable id: String,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<Void> {
        subscribeService.deleteSubscribe(id, currentUser)
        return ResponseEntity.ok().build()
    }
}