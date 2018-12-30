package com.msapplications.btdt.room_storage.cinema;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;

import java.util.List;

public class CinemaViewModel extends AndroidViewModel
{
    private CinemaRepository repository;

    private LiveData<List<Cinema>> cinemas;

    public CinemaViewModel(Application application)
    {
        super(application);
        repository = new CinemaRepository(application);
        cinemas = repository.getCinemas();
    }

    public LiveData<List<Cinema>> getCinemas() { return cinemas; }

    public void insert(Cinema cinema) { repository.insert(cinema); }

    public void delete(Cinema cinema) { repository.delete(cinema); }
}