package com.msapplications.btdt.room_storage.cinema;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;
import com.msapplications.btdt.objects.itemTypes.cinema.CinemaHall;

import java.util.List;

public class CinemaHallsViewModel extends AndroidViewModel
{
    private CinemaHallsRepository repository;

    public CinemaHallsViewModel(Application application)
    {
        super(application);
        repository = new CinemaHallsRepository(application);
    }

    public LiveData<List<CinemaHall>> getCinemaHalls(String cinemaName, String cinemaCity) {
        return repository.getCinemaHalls(cinemaName, cinemaCity);
    }

    public void edit(CinemaHall cinemaHall) { repository.edit(cinemaHall); }

    public void insert(CinemaHall cinemaHall) { repository.insert(cinemaHall); }

    public void delete(CinemaHall cinemaHall) { repository.delete(cinemaHall); }

    public void deleteCinemaHalls(Cinema cinema) { repository.deleteCinemaHalls(cinema); }

    public void deleteAll() { repository.deleteAll(); }
}
