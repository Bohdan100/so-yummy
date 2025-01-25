package corp.soyummy.subscribes;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.bson.types.ObjectId;
import java.util.List;

@Repository
public interface SubscribeRepository extends MongoRepository<Subscribe, String> {
    List<Subscribe> findByEmailContainingIgnoreCase(String email);
    boolean existsByOwnerIdAndEmail(ObjectId ownerId, String email);
    void deleteByOwnerIdAndEmail(ObjectId ownerId, String email);
}