package corp.soyummy.subscribes;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;

import corp.soyummy.subscribes.dto.SubscribeCreateDto;
import corp.soyummy.subscribes.dto.SubscribeUpdateDto;
import corp.soyummy.auth.User;
import corp.soyummy.errors.ResourceNotFoundException;
import static corp.soyummy.constants.Constants.VERSION;

@RestController
@RequestMapping(VERSION + "subscribes")
@RequiredArgsConstructor
public class SubscribeController {

    private final SubscribeService subscribeService;

    // GET http://localhost:8080/api/v1/subscribes/
    @GetMapping
    public ResponseEntity<List<Subscribe>> getAllSubscribes() {
        List<Subscribe> subscribes = subscribeService.getAllSubscribes();
        return ResponseEntity.ok(subscribes);
    }

    // GET http://localhost:8080/api/v1/subscribes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Subscribe> getSubscribeById(@PathVariable String id) {
        Subscribe subscribe = subscribeService.getSubscribeById(id);
        return ResponseEntity.ok(subscribe);
    }

    // GET http://localhost:8080/api/v1/subscribes/search?email=anna
    @GetMapping("/search")
    public ResponseEntity<List<Subscribe>> findByEmail(@RequestParam String email) {
        List<Subscribe> subscribes = subscribeService.findByEmail(email);
        return ResponseEntity.ok(subscribes);
    }

    // GET http://localhost:8080/api/v1/subscribes/isSubscribed?userId={userId}&email={email}
    @GetMapping("/isSubscribed")
    public ResponseEntity<Boolean> isSubscribed(
            @RequestParam String userId,
            @RequestParam String email) {
        boolean isSubscribed = subscribeService.isSubscribed(userId, email);
        return ResponseEntity.ok(isSubscribed);
    }

    // POST http://localhost:8080/api/v1/subscribes
    @PostMapping
    public ResponseEntity<Subscribe> createSubscribe(
            @Valid @RequestBody SubscribeCreateDto subscribeCreateDto,
            @AuthenticationPrincipal User currentUser) {
        subscribeCreateDto.setOwner(currentUser.getId());
        Subscribe subscribe = subscribeService.createSubscribe(subscribeCreateDto);
        return ResponseEntity.ok(subscribe);
    }

    // PUT http://localhost:8080/api/v1/subscribes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Subscribe> updateSubscribe(
            @PathVariable String id,
            @Valid @RequestBody SubscribeUpdateDto subscribeUpdateDto,
            @AuthenticationPrincipal User currentUser) {
        Subscribe existingSubscribe = subscribeService.getSubscribeById(id);
        if (!existingSubscribe.getOwnerId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("Subscription not found for current user");
        }

        Subscribe subscribe = subscribeService.updateSubscribe(id, subscribeUpdateDto);
        return ResponseEntity.ok(subscribe);
    }

    // POST http://localhost:8080/api/v1/subscribes/unsubscribe?userId={userId}&email={email}
    @PostMapping("/unsubscribe")
    public ResponseEntity<Void> unsubscribe(
            @RequestParam String userId,
            @RequestParam String email,
            @AuthenticationPrincipal User currentUser) {
        if (!subscribeService.isSubscribed(currentUser.getId(), email)) {
            throw new ResourceNotFoundException("Subscription not found for current user");
        }

        subscribeService.unsubscribe(userId, email);
        return ResponseEntity.noContent().build();
    }

    // DELETE http://localhost:8080/api/v1/subscribes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscribe(@PathVariable String id) {
        subscribeService.deleteSubscribe(id);
        return ResponseEntity.ok().build();
    }
}
