package com.msapplications.btdt.room_storage.recipe;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.msapplications.btdt.objects.itemTypes.recipes.Recipe;
import com.msapplications.btdt.room_storage.RoomDatabase;

import java.util.List;

public class RecipeRepository {

    private RecipeDao recipeDao;
    private LiveData<List<Recipe>> recipes;

    RecipeRepository(Application application)
    {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        recipeDao = db.recipeDao();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipeDao.getRecipes();
    }

    public Recipe getRecipe(int id) {
        return recipeDao.getRecipe(id);
    }

    public void insert(Recipe recipe) {
        recipeDao.insert(recipe);
    }

    public void delete(Recipe recipe) {
        recipeDao.delete(recipe);
    }
}
