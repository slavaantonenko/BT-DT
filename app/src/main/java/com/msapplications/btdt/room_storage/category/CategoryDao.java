package com.msapplications.btdt.room_storage.category;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.msapplications.btdt.objects.Category;

import java.util.List;

@Dao
public interface CategoryDao
{
    @Query("SELECT * FROM categories_table")
    LiveData<List<Category>> getCategories();

    @Query("SELECT * FROM categories_table WHERE id=:id")
    Category getCategory(int id);

    @Query("SELECT COUNT(category_name) FROM categories_table WHERE category_name=:name")
    int categoryNameExists(String name);

    @Query("SELECT COUNT(category_type) FROM categories_table WHERE category_type=:type")
    int categoryTypeExists(int type);

    @Query("UPDATE categories_table SET category_name=:name WHERE id=:id")
    void rename(String name, int id);

    @Insert
    void insert(Category category);

    @Delete
    void delete(Category category);

    @Query("DELETE FROM note_item_table WHERE id=:itemID")
    void deleteNoteItemsByCategory(int itemID);

    @Query("SELECT MIN(id) FROM categories_table WHERE category_name=:name")
    int getIdByName(String name);

    @Query("SELECT category_type FROM categories_table WHERE id=:id")
    int getType(int id);
}
