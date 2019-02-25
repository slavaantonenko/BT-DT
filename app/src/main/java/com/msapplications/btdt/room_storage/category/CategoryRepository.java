package com.msapplications.btdt.room_storage.category;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.msapplications.btdt.objects.Category;
import com.msapplications.btdt.objects.CategoryType;
import com.msapplications.btdt.room_storage.RoomDatabase;

import java.util.List;

public class CategoryRepository
{
    private CategoryDao categoryDao;
    private LiveData<List<Category>> categories;

    CategoryRepository(Application application)
    {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        categoryDao = db.categoryDao();
    }

    public LiveData<List<Category>> getCategories() {
        return categoryDao.getCategories();
    }

    public Category getCategory(int id) {
        return categoryDao.getCategory(id);
    }

    public int categoryNameExists(String name) { return categoryDao.categoryNameExists(name); }

    public int categoryTypeExists(int typeCode) { return categoryDao.categoryTypeExists(typeCode); }

    public void rename(Category category) {
        new renameAsyncTask(categoryDao).execute(category);
    }

    public void insert(Category category) {
        categoryDao.insert(category);
        //new insertAsyncTask(categoryDao).execute(category);
    }

    public void delete(Category category) {
        new deleteAsyncTask(categoryDao).execute(category);
    }

    public int getIdByName(String name) {
       return categoryDao.getIdByName(name);
    }

    public int getType(int id) {
        return categoryDao.getType(id);
    }

    private static class renameAsyncTask extends AsyncTask<Category, Void, Void>
    {
        private CategoryDao asyncTaskDao;

        renameAsyncTask(CategoryDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Category... categories) {
            asyncTaskDao.rename(categories[0].getName(), categories[0].getId());
            return null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<Category, Void, Void>
    {
        private CategoryDao asyncTaskDao;

        insertAsyncTask(CategoryDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Category... categories) {
            asyncTaskDao.insert(categories[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Category, Void, Void>
    {
        private CategoryDao asyncTaskDao;

        deleteAsyncTask(CategoryDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            asyncTaskDao.delete(categories[0]);
            asyncTaskDao.deleteNoteItemsByCategory(categories[0].getId());
            return null;
        }
    }
}
