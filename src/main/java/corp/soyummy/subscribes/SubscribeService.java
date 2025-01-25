package corp.soyummy.subscribes;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import java.util.List;

import corp.soyummy.subscribes.dto.SubscribeCreateDto;
import corp.soyummy.subscribes.dto.SubscribeUpdateDto;
import corp.soyummy.errors.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;

    public List<Subscribe> getAllSubscribes() {
        return subscribeRepository.findAll();
    }

    public Subscribe getSubscribeById(String id) {
        return subscribeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + id));
    }

    public List<Subscribe> findByEmail(String email) {
        return subscribeRepository.findByEmailContainingIgnoreCase(email);
    }

    public boolean isSubscribed(String ownerId, String email) {
        ObjectId ownerIdObj = new ObjectId(ownerId);
        return subscribeRepository.existsByOwnerIdAndEmail(ownerIdObj, email);
    }

    public Subscribe createSubscribe(SubscribeCreateDto subscribeCreateDto) {
        if (ObjectUtils.isEmpty(subscribeCreateDto.getOwner()) || ObjectUtils.isEmpty(subscribeCreateDto.getEmail())) {
            throw new IllegalArgumentException("Missing required fields when adding a new subscription");
        }

        Subscribe subscribe = Subscribe.builder()
                .ownerId(subscribeCreateDto.getOwner())
                .email(subscribeCreateDto.getEmail())
                .build();

        return subscribeRepository.save(subscribe);
    }

    public Subscribe updateSubscribe(String id, SubscribeUpdateDto subscribeUpdateDto) {
        Subscribe existingSubscribe = getSubscribeById(id);
        if (existingSubscribe == null) {
            throw new ResourceNotFoundException("Subscription not found with id: " + id);
        }

        if (subscribeUpdateDto.getEmail() != null && !subscribeUpdateDto.getEmail().isEmpty()) {
            existingSubscribe.setEmail(subscribeUpdateDto.getEmail());
        }

        return subscribeRepository.save(existingSubscribe);
    }

    public void unsubscribe(String userId, String email) {
        ObjectId userIdObj = new ObjectId(userId);
        subscribeRepository.deleteByOwnerIdAndEmail(userIdObj, email);
    }

    public void deleteSubscribe(String id) {
        if (!subscribeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subscription not found with id: " + id);
        }
        subscribeRepository.deleteById(id);
    }
}