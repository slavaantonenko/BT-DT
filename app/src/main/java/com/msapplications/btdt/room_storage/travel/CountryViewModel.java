package com.msapplications.btdt.room_storage.travel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.msapplications.btdt.objects.itemTypes.travel.CountryModel;

import java.util.List;

public class CountryViewModel extends AndroidViewModel
{
    private CountryRepository repository;

    public CountryViewModel(Application application) {
        super(application);
        repository = new CountryRepository(application);
    }

    public LiveData<List<CountryModel>> getTravelListCountries() {
        return repository.getTravelListCountries();
    }

    public void updateIsInTravelList(CountryModel country) { repository.updateIsInTravelList(country); }

    public void updateBeenThere(CountryModel country) { repository.updateBeenThere(country); }
}
