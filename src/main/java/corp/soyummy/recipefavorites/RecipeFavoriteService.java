package corp.soyummy.recipefavorites;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import java.util.List;

import corp.soyummy.recipefavorites.dto.RecipeFavoriteCreateDto;
import corp.soyummy.recipefavorites.dto.RecipeFavoriteUpdateDto;
import corp.soyummy.errors.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class RecipeFavoriteService {

    private final RecipeFavoriteRepository recipeFavoriteRepository;

    public List<RecipeFavorite> getAllFavorites() {
        return recipeFavoriteRepository.findAll();
    }

    public RecipeFavorite getFavoriteById(String id) {
        return recipeFavoriteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Favorite recipe not found with id: " + id));
    }

    public List<RecipeFavorite> getFavoritesByRecipeId(String recipeId) {
        ObjectId recipeIdObj = new ObjectId(recipeId);
        return recipeFavoriteRepository.findByRecipeId(recipeIdObj);
    }

    public List<RecipeFavorite> getFavoritesByAmount(Integer amount) {
        return recipeFavoriteRepository.findByAmount(amount);
    }

    public RecipeFavorite createFavorite(RecipeFavoriteCreateDto createDto) {
        if (createDto.getAmount() == null || createDto.getRecipeId() == null) {
            throw new IllegalArgumentException("All fields (recipeId, amount must be provided.");
        }

        RecipeFavorite favorite = new RecipeFavorite();
        favorite.setRecipeId(createDto.getRecipeId());
        favorite.setAmount(createDto.getAmount());

        return recipeFavoriteRepository.save(favorite);
    }

    public RecipeFavorite updateFavorite(String id, RecipeFavoriteUpdateDto updateDto) {
        RecipeFavorite existingFavorite = getFavoriteById(id); // Reuse existing method to handle potential exception
        if (existingFavorite == null) {
            throw new ResourceNotFoundException("Favorite recipe not found with id: " + id);
        }

        existingFavorite.setAmount(updateDto.getAmount());
        return recipeFavoriteRepository.save(existingFavorite);
    }

    public void deleteFavorite(String id) {
        if (!recipeFavoriteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Favorite recipe not found with id: " + id);
        }

        recipeFavoriteRepository.deleteById(id);
    }

    public List<RecipeFavorite> getFavoritesByRatingDesc() {
        return recipeFavoriteRepository.findAllByOrderByAmountDesc();
    }
}