package com.msapplications.btdt.room_storage.recipe;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.msapplications.btdt.objects.itemTypes.recipes.Recipe;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private RecipeRepository repository;
    private LiveData<List<Recipe>> recipes;

    public RecipeViewModel(Application application) {
        super(application);
        repository = new RecipeRepository(application);
    }

    public LiveData<List<Recipe>> getRecipes() {
        return repository.getRecipes();
    }

    public Recipe getRecipe(int id) {
        return repository.getRecipe(id);
    }

    public void insert(Recipe recipe) { repository.insert(recipe); }

    public void delete(Recipe recipe) { repository.delete(recipe); }

    public void deleteRecipe(int recipeID) {
        Recipe recipe = getRecipe(recipeID);
        repository.delete(recipe);
    }
}
