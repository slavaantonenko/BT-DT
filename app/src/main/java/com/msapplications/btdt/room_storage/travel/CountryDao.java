package com.msapplications.btdt.room_storage.travel;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.msapplications.btdt.objects.itemTypes.travel.CountryModel;

import java.util.Collection;
import java.util.List;

@Dao
public interface CountryDao
{
    @Query("SELECT * from countries_table WHERE isInTravelList=1 ORDER BY beenThere")
    LiveData<List<CountryModel>> getTravelListCountries();

    @Query("SELECT * from countries_table WHERE code NOT LIKE 'PS'")
    List<CountryModel> getCountries();

    @Query("UPDATE countries_table SET isInTravelList=:isInTravelList, beenThere=:isBeenThere, image=:image WHERE id=:id")
    void updateIsInTravelList(int id, boolean isInTravelList, boolean isBeenThere, String image);

    @Query("UPDATE countries_table SET beenThere=:beenThere WHERE id=:id")
    void updateBeenThere(int id, boolean beenThere);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Collection<CountryModel> countries);

    @Query("DELETE FROM countries_table")
    void deleteAll();
}
