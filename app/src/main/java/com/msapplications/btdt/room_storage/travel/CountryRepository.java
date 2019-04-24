package com.msapplications.btdt.room_storage.travel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.msapplications.btdt.objects.itemTypes.travel.CountryModel;
import com.msapplications.btdt.room_storage.RoomDatabase;

import java.util.List;

public class CountryRepository
{
    private CountryDao countryDao;

    CountryRepository(Application application)
    {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        countryDao = db.countryDao();
    }

    public LiveData<List<CountryModel>> getTravelListCountries() {
        return countryDao.getTravelListCountries();
    }

    public void updateIsInTravelList(CountryModel country) {
        new updateIsInTravelListAsyncTask(countryDao).execute(country);
    }

    public void updateBeenThere(CountryModel country) {
        new updateBeenThereAsyncTask(countryDao).execute(country);
    }

    private static class updateIsInTravelListAsyncTask extends AsyncTask<CountryModel, Void, Void>
    {
        private CountryDao asyncTaskDao;

        updateIsInTravelListAsyncTask(CountryDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CountryModel... countries) {
            asyncTaskDao.updateIsInTravelList(countries[0].getId(), countries[0].isInTravelList(),
                    countries[0].isBeenThere(), countries[0].getImage());
            return null;
        }
    }

    private static class updateBeenThereAsyncTask extends AsyncTask<CountryModel, Void, Void>
    {
        private CountryDao asyncTaskDao;

        updateBeenThereAsyncTask(CountryDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CountryModel... countries) {
            asyncTaskDao.updateBeenThere(countries[0].getId(), countries[0].isBeenThere());
            return null;
        }
    }
}
