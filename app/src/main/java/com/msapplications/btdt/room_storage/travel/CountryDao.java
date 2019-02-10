package com.msapplications.btdt.room_storage.travel;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.msapplications.btdt.objects.itemTypes.travel.CountryModel;

import java.util.Collection;
import java.util.List;

@Dao
public interface CountryDao
{
    @Query("SELECT * from countries_table")
    List<CountryModel> getCountries();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Collection<CountryModel> countries);

    @Query("DELETE FROM countries_table")
    void deleteAll();
}
