package com.msapplications.btdt.room_storage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;
import com.msapplications.btdt.objects.itemTypes.cinema.CinemaHall;
import com.msapplications.btdt.room_storage.cinema.CinemaDao;
import com.msapplications.btdt.room_storage.cinema.CinemaHallsDao;

// version = 1 is CinemaHall hall and row in int and not String.
@Database(entities = {Cinema.class, CinemaHall.class}, version = 2)
public abstract class RoomDatabase extends android.arch.persistence.room.RoomDatabase
{
    public abstract CinemaDao cinemaDao();
    public abstract CinemaHallsDao cinemaHallsDao();

    private static volatile RoomDatabase INSTANCE;

    public static RoomDatabase getDatabase(final Context context)
    {
        if (INSTANCE == null)
        {
            synchronized (RoomDatabase.class)
            {
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDatabase.class, "room_database")
                            .build();
            }
        }

        return INSTANCE;
    }
}
