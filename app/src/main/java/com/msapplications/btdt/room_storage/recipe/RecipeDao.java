package com.msapplications.btdt.room_storage.recipe;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.msapplications.btdt.objects.itemTypes.recipes.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipes_table")
    LiveData<List<Recipe>> getRecipes();

    @Query("SELECT * FROM recipes_table WHERE id=:id")
    Recipe getRecipe(int id);

    @Query("SELECT id FROM recipes_table WHERE recipe_name=:name")
    int getRecipeIdByName(String name);

    @Insert
    void insert(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

    @Query("UPDATE recipes_table SET recipe_name=:name WHERE id=:id")
    void rename(String name, int id);

    @Query("SELECT COUNT(recipe_name) FROM recipes_table WHERE recipe_name=:name")
    int recipeNameExists(String name);

    @Query("UPDATE recipes_table SET recipe_image=:imageUri where id=:id")
    void setImage(int id, String imageUri);
}
