package com.msapplications.btdt.room_storage.ingredient;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.msapplications.btdt.objects.itemTypes.NoteItem;
import com.msapplications.btdt.objects.itemTypes.recipes.RecipeIngredient;
import com.msapplications.btdt.room_storage.RoomDatabase;
import com.msapplications.btdt.room_storage.note.NoteItemDao;

import java.util.List;

public class IngredientRepository {

    private IngredientDao ingredientDao;

    IngredientRepository(Application application)
    {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        ingredientDao = db.ingredientDao();
    }

    public LiveData<List<RecipeIngredient>> getIngredients(int recipeID) {
        return ingredientDao.getIngredients(recipeID);
    }

    public void insertIngredient(RecipeIngredient ingredient) {
//        ingredientDao.insert(ingredient);
        new insertAsyncTask(ingredientDao).execute(ingredient);
    }

//    public void insertNoteItems(Collection<NoteItem> noteItems) {
//        noteItemDao.insertAll(noteItems);
//    }

    public void deleteIngredientByID(int itemID) {
        ingredientDao.deleteIngredientByID(itemID);
    }

    public void deleteIngredintsByRecipe(int recipeID) {
        new deleteAsyncTask(ingredientDao).execute(recipeID);
    }

    public void updateText(int id, String newText) {
        ingredientDao.updateText(newText, id);
    }

    public void increaseLineNumbers(int minLine, int recipeID) {
        ingredientDao.increaseLineNumbers(minLine, recipeID);
    }

    public void decreaseLineNumbers(int minLine, int recipeID) {
        ingredientDao.decreaseLineNumbers(minLine, recipeID);
    }

    public void deleteAll() {
        ingredientDao.deleteAll();
    }

    private static class insertAsyncTask extends AsyncTask<RecipeIngredient, Void, Void>
    {
        private IngredientDao asyncTaskDao;

        insertAsyncTask(IngredientDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final RecipeIngredient... ingredients) {
            asyncTaskDao.insert(ingredients[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void>
    {
        private IngredientDao asyncTaskDao;

        deleteAsyncTask(IngredientDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Integer... recipeIDs) {
            asyncTaskDao.deleteIngredientByRecipeID(recipeIDs[0]);
            return null;
        }
    }
}
