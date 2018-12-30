package com.msapplications.btdt.room_storage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;
import com.msapplications.btdt.room_storage.cinema.CinemaDao;

@Database(entities = {Cinema.class}, version = 1)
public abstract class RoomDatabase extends android.arch.persistence.room.RoomDatabase
{
    public abstract CinemaDao cinemaDao();

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
