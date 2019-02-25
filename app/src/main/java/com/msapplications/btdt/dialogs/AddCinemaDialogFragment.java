package com.msapplications.btdt.dialogs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.common.data.ObjectExclusionFilterable;
import com.msapplications.btdt.room_storage.cinema.CinemaViewModel;
import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.MyLocation;
import com.msapplications.btdt.R;
import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AddCinemaDialogFragment extends DialogFragment
{
    MyLocation myLocation;
    private String countryName = "Israel";
    private CinemaViewModel cinemaViewModel = null;
    private List<Cinema> cinemaList = new ArrayList<>();

    public AddCinemaDialogFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckListFragment.
     */
    public static AddCinemaDialogFragment newInstance(String param1, String param2)
    {
        AddCinemaDialogFragment fragment = new AddCinemaDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_add_cinema, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initializeDialog(view);

        cinemaViewModel = ViewModelProviders.of(this).get(CinemaViewModel.class);
        cinemaViewModel.getCinemas().observe(this, new Observer<List<Cinema>>()
        {
            @Override
            public void onChanged(@Nullable List<Cinema> cinemas)
            {
                cinemaList = cinemas;
            }
        });
    }

    @Override
    public void onPause()
    {
        super.onPause();

        // If the user backs out of the activity before the location is returned the application would crash.
        if (myLocation != null)
            myLocation.cancelTimer();
    }

    private void initializeDialog(View view)
    {
        final Spinner spCinemas = view.findViewById(R.id.spCinema);
        final Spinner spCinemaCities = view.findViewById(R.id.spCinemaCities);
        final Button btnSaveCinema = view.findViewById(R.id.btnSaveCinema);

        int cinemasID = getResources().getIdentifier( countryName.toLowerCase() + "_cinemas_name", "array", getActivity().getPackageName());
        setSpinnerEntries(view, spCinemas, null, cinemasID);

        spCinemas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = spCinemas.getSelectedItem().toString();
                boolean areEntriesSet;

                switch (selectedItem)
                {
                    case (CommonValues.CINEMA_CITY):
                        areEntriesSet = setSpinnerEntries(view, spCinemaCities, CommonValues.CINEMA_CITY, R.array.cinema_city_cities);
                        if (areEntriesSet)
                            break;
                    case (CommonValues.GLOBUS_MAX):
                        spCinemas.setSelection(1);
                        areEntriesSet = setSpinnerEntries(view, spCinemaCities, CommonValues.GLOBUS_MAX, R.array.globus_max_cities);
                        if (areEntriesSet)
                            break;
                    case (CommonValues.YES_PLANET):
                        spCinemas.setSelection(2);
                        setSpinnerEntries(view, spCinemaCities, CommonValues.YES_PLANET, R.array.yes_planet_cities);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnSaveCinema.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int logoID = 0;
                String cinemaName = spCinemas.getSelectedItem().toString();
                Object cinemaCity = spCinemaCities.getSelectedItem();

                if (cinemaCity == null)
                    return;

                switch (cinemaName)
                {
                    case (CommonValues.CINEMA_CITY):
                        logoID = R.drawable.cinema_city;
                        break;
                    case (CommonValues.GLOBUS_MAX):
                        logoID = R.drawable.globus_max;
                        break;
                    case (CommonValues.YES_PLANET):
                        logoID = R.drawable.yes_planet;
                        break;
                }

                getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, new Intent());
                cinemaViewModel.insert(new Cinema(0, cinemaName, cinemaCity.toString(), logoID));
                dismiss();
            }
        });
    }

    private void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    private boolean setSpinnerEntries(View view, Spinner sp, String cinemaName, int id)
    {
        String[] cities = getResources().getStringArray(id);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, new ArrayList(Arrays.asList(cities)));

        if (cinemaName != null)
        {
            for (int i = 0; i < adapter.getCount(); i++)
            {
                CharSequence cinemaCity = adapter.getItem(i);
                if (cinemaCity != null) {
                    if (cinemaViewModel.cinemaExists(cinemaName, cinemaCity.toString()) > 0) {
                        adapter.remove(cinemaCity);
                        i--;
                    }
                }
            }

            if (adapter.getCount() == 0)
                return false;
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        return true;
    }
}
