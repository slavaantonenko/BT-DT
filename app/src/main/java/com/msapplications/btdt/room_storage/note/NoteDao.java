package com.msapplications.btdt.room_storage.note;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.msapplications.btdt.objects.itemTypes.NoteItem;
import com.msapplications.btdt.objects.itemTypes.travel.CountryModel;

import java.util.Collection;
import java.util.List;

public interface NoteDao
{
    @Query("SELECT * from note_item_table")
    List<CountryModel> getNote();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Collection<NoteItem> noteItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteItem noteItems);

    @Query("DELETE FROM note_item_table")
    void deleteAll();
}
