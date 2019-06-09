package com.msapplications.btdt.room_storage.ingredient;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.msapplications.btdt.objects.itemTypes.recipes.RecipeIngredient;

import java.util.Collection;
import java.util.List;

@Dao
public interface IngredientDao {
    @Query("SELECT * FROM ingredient_item_table WHERE recipe_id=:recipeID")
    LiveData<List<RecipeIngredient>> getIngredients(int recipeID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Collection<RecipeIngredient> ingredients);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RecipeIngredient ingredient);

    @Query("DELETE FROM ingredient_item_table")
    void deleteAll();

    @Query("DELETE FROM ingredient_item_table WHERE id=:itemID")
    void deleteIngredientByID(int itemID);

    @Query("DELETE FROM ingredient_item_table WHERE recipe_id=:recipeID")
    void deleteIngredientByRecipeID(int recipeID);

    @Query("UPDATE ingredient_item_table SET text=:newText WHERE id=:id")
    void updateText(String newText, int id);

    @Query("UPDATE ingredient_item_table SET line_number=line_number+1 WHERE recipe_id=:recipeID AND line_number>=:minLine")
    void increaseLineNumbers(int minLine, int recipeID);

    @Query("UPDATE ingredient_item_table SET line_number=line_number-1 WHERE recipe_id=:recipeID AND line_number>=:minLine")
    void decreaseLineNumbers(int minLine, int recipeID);
}
