package com.msapplications.btdt.room_storage.cinema;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;

import java.util.List;

@Dao
public interface CinemaDao
{
    @Query("SELECT * from cinemas_table")
    LiveData<List<Cinema>> getCinemas();

    @Query("SELECT * FROM cinemas_table WHERE id=:id")
    Cinema getCinema(int id);

    @Query("SELECT COUNT(id) FROM cinemas_table WHERE cinema_name=:name AND cinema_city=:city")
    int cinemaExists(String name, String city);

    @Insert
    void insert(Cinema cinema);

    @Delete
    void delete(Cinema cinema);

    @Query("DELETE FROM cinemas_table")
    void deleteAll();

    @Query("DELETE FROM cinema_halls_table")
    void deleteAllHalls();
}
