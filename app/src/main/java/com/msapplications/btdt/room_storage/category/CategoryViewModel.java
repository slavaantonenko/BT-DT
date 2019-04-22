package com.msapplications.btdt.room_storage.category;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.msapplications.btdt.interfaces.Renamable;
import com.msapplications.btdt.objects.Category;
import com.msapplications.btdt.objects.CategoryType;
import com.msapplications.btdt.room_storage.ViewModelDeletable;
import com.msapplications.btdt.room_storage.ViewModelRenamable;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel implements ViewModelDeletable, ViewModelRenamable
{
    private CategoryRepository repository;
    private LiveData<List<Category>> categories;

    public CategoryViewModel(Application application) {
        super(application);
        repository = new CategoryRepository(application);
    }

    public LiveData<List<Category>> getCategories() {
        return repository.getCategories();
    }

    public Category getCategory(int id) {
        return repository.getCategory(id);
    }

    public int nameExists(String name) { return repository.categoryNameExists(name); }

    public int categoryTypeExists(int typeCode) { return repository.categoryTypeExists(typeCode); }

    public void rename(Renamable category) { repository.rename((Category)category); }

    public void updateColor(int color, int id) { repository.updateColor(color, id); }

    public void insert(Category category) { repository.insert(category); }

    public void deleteCategory(int categoryID) {
        Category category = getCategory(categoryID);
        repository.delete(category);
    }

    public int getIdByName(String name) { return repository.getIdByName(name); }

    public int getType(int id) { return repository.getType(id); }


}
