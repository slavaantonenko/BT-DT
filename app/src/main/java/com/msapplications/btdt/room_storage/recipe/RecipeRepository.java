package com.msapplications.btdt.room_storage.recipe;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

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

    public int getRecipeIdByName(String name) {
        return recipeDao.getRecipeIdByName(name);
    }


    public void insert(Recipe recipe) {
        recipeDao.insert(recipe);
    }

    public void delete(Recipe recipe) {
        recipeDao.delete(recipe);
    }

    public void rename(Recipe recipe) {
        new renameAsyncTask(recipeDao).execute(recipe);
    }

    public int recipeNameExists(String name) { return recipeDao.recipeNameExists(name); }

    public void setImage(int id, String imageUri) {
        recipeDao.setImage(id, imageUri);
    }

    private static class renameAsyncTask extends AsyncTask<Recipe, Void, Void>
    {
        private RecipeDao asyncTaskDao;

        renameAsyncTask(RecipeDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Recipe... recipes) {
            asyncTaskDao.rename(recipes[0].getName(), recipes[0].getId());
            return null;
        }
    }

}
