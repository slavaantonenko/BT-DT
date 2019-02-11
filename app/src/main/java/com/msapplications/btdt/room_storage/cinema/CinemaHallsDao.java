package com.msapplications.btdt.room_storage.cinema;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;
import com.msapplications.btdt.objects.itemTypes.cinema.CinemaHall;

import java.util.List;

@Dao
public interface CinemaHallsDao
{
    @Query("SELECT * from cinema_halls_table WHERE cinema_name=:cinemaName AND cinema_city=:cinemaCity")
    LiveData<List<CinemaHall>> getCinemaHalls(String cinemaName, String cinemaCity);

    @Query("UPDATE cinema_halls_table SET cinema_hall=:hall, cinema_hall_row=:row WHERE id=:id")
    void edit(int id, String hall, String row);

    @Insert
    void insert(CinemaHall cinemaHall);

    @Delete
    void delete(CinemaHall cinemaHall);

    @Query("DELETE FROM cinema_halls_table WHERE cinema_name=:cinemaName AND cinema_city=:cinemaCity")
    void deleteCinemaHalls(String cinemaName, String cinemaCity);

    @Query("DELETE FROM cinema_halls_table")
    void deleteAll();
}
