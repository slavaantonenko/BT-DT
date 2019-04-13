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

    @Insert
    void insert(Recipe recipe);

    @Delete
    void delete(Recipe recipe);
}
