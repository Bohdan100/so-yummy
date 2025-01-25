package corp.soyummy.ingredients;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import corp.soyummy.errors.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

import corp.soyummy.ingredients.dto.IngredientCreateDto;
import corp.soyummy.ingredients.dto.IngredientUpdateDto;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public List<Ingredient> getAllIngredients() {
        return new ArrayList<>(ingredientRepository.findAll());
    }

    public Ingredient getIngredientById(String id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found with id: " + id));
    }

    public List<Ingredient> searchIngredientsByTitle(String title) {
        return new ArrayList<>(ingredientRepository.findByTitleContainingIgnoreCase(title));
    }

    public Ingredient createIngredient(IngredientCreateDto ingredientCreateDto) {
        Ingredient ingredient = buildNewIngredient(ingredientCreateDto);

        return ingredientRepository.save(ingredient);
    }

    public Ingredient updateIngredient(String id, IngredientUpdateDto ingredientUpdateDto) {
        Ingredient existingIngredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with id: " + id));

        Ingredient updatedIngredient = updateCurrentIngredient(ingredientUpdateDto, existingIngredient);

        return ingredientRepository.save(updatedIngredient);
    }

    public void deleteIngredient(String id) {
        if (!ingredientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ingredient not found with id: " + id);
        }
        ingredientRepository.deleteById(id);
    }

    private Ingredient buildNewIngredient (IngredientCreateDto ingredientCreateDto) {
        return Ingredient.builder()
                .title(ingredientCreateDto.getTitle())
                .description(ingredientCreateDto.getDescription())
                .thumbnail(ingredientCreateDto.getThumbnail())
                .build();
    }

    private Ingredient updateCurrentIngredient (IngredientUpdateDto ingredientUpdateDto, Ingredient existingIngredient) {
        return Ingredient.builder()
                .id(existingIngredient.getId())
                .title(ingredientUpdateDto.getTitle() != null && !ingredientUpdateDto.getTitle().isBlank()
                        ? ingredientUpdateDto.getTitle()
                        : existingIngredient.getTitle())
                .description(ingredientUpdateDto.getDescription() != null && !ingredientUpdateDto.getDescription().isBlank()
                        ? ingredientUpdateDto.getDescription()
                        : existingIngredient.getDescription())
                .thumbnail(ingredientUpdateDto.getThumbnail() != null && !ingredientUpdateDto.getThumbnail().isBlank()
                        ? ingredientUpdateDto.getThumbnail()
                        : existingIngredient.getThumbnail())
                .build();
    }
}
