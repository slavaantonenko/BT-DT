package com.msapplications.btdt.room_storage.note;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.msapplications.btdt.objects.itemTypes.NoteItem;
import com.msapplications.btdt.objects.itemTypes.travel.CountryModel;

import java.util.Collection;
import java.util.List;

@Dao
public interface NoteItemDao
{
    @Query("SELECT * FROM note_item_table WHERE category_id=:categoryID")
    LiveData<List<NoteItem>> getNoteItems(int categoryID);

    @Query("SELECT * FROM note_item_table WHERE category_id=:categoryID and recipeID=:recipeID")
    LiveData<List<NoteItem>> getRecipeNoteItems(int categoryID, int recipeID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Collection<NoteItem> noteItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteItem noteItems);

    @Query("DELETE FROM note_item_table")
    void deleteAll();

    @Query("DELETE FROM note_item_table WHERE id=:itemID")
    void deleteNoteItemsByCategory(int itemID);

    @Query("UPDATE note_item_table SET text=:newText WHERE id=:id")
    void updateText(String newText, int id);

    @Query("UPDATE note_item_table SET line_number=line_number+1 WHERE category_id=:catID AND line_number>=:minLine")
    void increaseLineNumbers(int minLine, int catID);

    @Query("UPDATE note_item_table SET line_number=line_number-1 WHERE category_id=:catID AND line_number>=:minLine")
    void decreaseLineNumbers(int minLine, int catID);

    @Query("UPDATE note_item_table SET is_bold=:isBold WHERE id=:id")
    void updateBold(int id, boolean isBold);

    @Query("UPDATE note_item_table SET is_italic=:isItalic WHERE id=:id")
    void updateItalic(int id, boolean isItalic);

    @Query("UPDATE note_item_table SET is_checked=:isChecked WHERE id=:id")
    void updateChecked(int id, boolean isChecked);

    @Query("UPDATE note_item_table SET is_checkBox=:isCheckBox WHERE id=:id")
    void updateCheckBox(int id, boolean isCheckBox);
}
