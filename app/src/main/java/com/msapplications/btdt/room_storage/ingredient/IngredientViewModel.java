package com.msapplications.btdt.room_storage.ingredient;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.msapplications.btdt.objects.itemTypes.NoteItem;
import com.msapplications.btdt.objects.itemTypes.recipes.RecipeIngredient;
import com.msapplications.btdt.room_storage.note.NoteItemRepository;

import java.util.List;

public class IngredientViewModel extends AndroidViewModel {
    private IngredientRepository repository;

    public IngredientViewModel(Application application)
    {
        super(application);
        repository = new IngredientRepository(application);
    }

    public LiveData<List<RecipeIngredient>> getRecipeIngredients(int recipeID)
    {
        return repository.getIngredients(recipeID);
    }

    public void insert(RecipeIngredient ingredient) { repository.insertIngredient(ingredient); }

    public void delete(int itemID) { repository.deleteIngredientByID(itemID); }

    public void deleteAllIngredientByRecipeID(int recipeID) { repository.deleteIngredintsByRecipe(recipeID); }


    public void updateText(int id, String newText) { repository.updateText(id, newText); }

    public void increaseLineNumbers(int minLine, int categoryID) { repository.increaseLineNumbers(minLine, categoryID); }

    public void decreaseLineNumbers(int minLine, int categoryID) { repository.decreaseLineNumbers(minLine, categoryID); }
}
