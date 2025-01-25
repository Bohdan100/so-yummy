package corp.soyummy.userfavorites;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import java.util.List;

import corp.soyummy.userfavorites.dto.UserFavoriteCreateDto;
import corp.soyummy.userfavorites.dto.UserFavoriteUpdateDto;
import corp.soyummy.errors.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class UserFavoriteService {

    private final UserFavoriteRepository userFavoriteRepository;

    public List<UserFavorite> getAllFavorites() {
        return userFavoriteRepository.findAll();
    }

    public UserFavorite getFavoriteById(String id) {
        return userFavoriteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Favorite not found with id: " + id));
    }

    public List<UserFavorite> getFavoritesByRecipeId(String recipeId) {
        ObjectId recipeIdObj = new ObjectId(recipeId);
        return userFavoriteRepository.findByRecipeId(recipeIdObj);
    }

    public List<UserFavorite> getFavoritesByUserId(String userId) {
        ObjectId userIdObj = new ObjectId(userId);
        return userFavoriteRepository.findByUserId(userIdObj);
    }

    public UserFavorite createFavorite(UserFavoriteCreateDto createDto) {
        UserFavorite favorite = new UserFavorite();
        favorite.setRecipeId(createDto.getRecipeId());
        favorite.setUserId(createDto.getUserId());
        return userFavoriteRepository.save(favorite);
    }

    public UserFavorite updateFavorite(String id, UserFavoriteUpdateDto updateDto) {
        UserFavorite existingFavorite = getFavoriteById(id);
        if (existingFavorite == null) {
            throw new ResourceNotFoundException("Favorite not found with id: " + id);
        }

        existingFavorite.setRecipeId(updateDto.getRecipeId());
        return userFavoriteRepository.save(existingFavorite);
    }

    public void deleteFavorite(String id) {
        if (!userFavoriteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Favorite not found with id: " + id);
        }

        userFavoriteRepository.deleteById(id);
    }
}
