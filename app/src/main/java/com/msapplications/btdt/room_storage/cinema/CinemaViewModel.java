package com.msapplications.btdt.room_storage.cinema;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;
import com.msapplications.btdt.room_storage.ViewModelDeletable;

import java.util.List;

public class CinemaViewModel extends AndroidViewModel implements ViewModelDeletable
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

    public Cinema getCinema(int id) { return repository.getCinema(id); }

    public int cinemaExists(String name, String city) { return repository.cinemaExists(name, city); }

    public void insert(Cinema cinema) { repository.insert(cinema); }

    public void delete(Cinema cinema) { repository.delete(cinema); }

    public void deleteAll() { repository.deleteAll(); }

    public void deleteCategory(int id) { repository.deleteAll(); }
}