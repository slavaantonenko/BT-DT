package com.msapplications.btdt.room_storage.cinema;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;
import com.msapplications.btdt.objects.itemTypes.cinema.CinemaHall;
import com.msapplications.btdt.room_storage.RoomDatabase;

import java.util.List;

public class CinemaHallsRepository
{
    private CinemaHallsDao cinemaHallsDao;

    CinemaHallsRepository(Application application)
    {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        cinemaHallsDao = db.cinemaHallsDao();
    }

    LiveData<List<CinemaHall>> getCinemaHalls(String cinemaName, String cinemaCity) {
        return cinemaHallsDao.getCinemaHalls(cinemaName, cinemaCity);
    }

    public void edit(CinemaHall cinemaHall) {
        new CinemaHallsRepository.editAsyncTask(cinemaHallsDao).execute(cinemaHall);
    }

    private static class editAsyncTask extends AsyncTask<CinemaHall, Void, Void>
    {
        private CinemaHallsDao asyncTaskDao;

        editAsyncTask(CinemaHallsDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CinemaHall... cinemaHalls) {
            asyncTaskDao.edit(cinemaHalls[0].getId(), cinemaHalls[0].getHall(), cinemaHalls[0].getRow());
            return null;
        }
    }

    public void insert(CinemaHall cinemaHall) {
        new CinemaHallsRepository.insertAsyncTask(cinemaHallsDao).execute(cinemaHall);
    }

    private static class insertAsyncTask extends AsyncTask<CinemaHall, Void, Void>
    {
        private CinemaHallsDao asyncTaskDao;

        insertAsyncTask(CinemaHallsDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CinemaHall... cinemaHalls) {
            asyncTaskDao.insert(cinemaHalls[0]);
            return null;
        }
    }

    public void delete(CinemaHall cinemaHall) {
        new CinemaHallsRepository.deleteAsyncTask(cinemaHallsDao).execute(cinemaHall);
    }

    private static class deleteAsyncTask extends AsyncTask<CinemaHall, Void, Void>
    {
        private CinemaHallsDao asyncTaskDao;

        deleteAsyncTask(CinemaHallsDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(CinemaHall... cinemaHalls) {
            asyncTaskDao.delete(cinemaHalls[0]);
            return null;
        }
    }

    public void deleteAll() {
        new CinemaHallsRepository.deleteAllAsyncTask(cinemaHallsDao).execute();
    }

    private static class deleteAllAsyncTask extends AsyncTask<CinemaHall, Void, Void>
    {
        private CinemaHallsDao asyncTaskDao;

        deleteAllAsyncTask(CinemaHallsDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(CinemaHall... cinemaHalls) {
            asyncTaskDao.deleteAll();
            return null;
        }
    }
}
