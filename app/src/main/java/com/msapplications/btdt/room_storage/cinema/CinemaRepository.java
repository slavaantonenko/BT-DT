package com.msapplications.btdt.room_storage.cinema;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;
import com.msapplications.btdt.room_storage.RoomDatabase;

import java.util.List;

public class CinemaRepository
{
    private CinemaDao cinemaDao;
    private LiveData<List<Cinema>> cinemas;

    CinemaRepository(Application application)
    {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        cinemaDao = db.cinemaDao();
        cinemas = cinemaDao.getCinemas();
    }

    LiveData<List<Cinema>> getCinemas() {
        return cinemas;
    }

    public void insert(Cinema cinema) {
        new insertAsyncTask(cinemaDao).execute(cinema);
    }

    private static class insertAsyncTask extends AsyncTask<Cinema, Void, Void>
    {
        private CinemaDao asyncTaskDao;

        insertAsyncTask(CinemaDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Cinema... cinemas) {
            asyncTaskDao.insert(cinemas[0]);
            return null;
        }
    }

    public void delete(Cinema cinema) {
        new deleteAsyncTask(cinemaDao).execute(cinema);
    }

    private static class deleteAsyncTask extends AsyncTask<Cinema, Void, Void>
    {
        private CinemaDao asyncTaskDao;

        deleteAsyncTask(CinemaDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Cinema... cinemas) {
            asyncTaskDao.delete(cinemas[0]);
            return null;
        }
    }
}
