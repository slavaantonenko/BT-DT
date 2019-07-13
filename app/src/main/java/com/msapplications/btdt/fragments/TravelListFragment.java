package com.msapplications.btdt.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.R;
import com.msapplications.btdt.Utils;
import com.msapplications.btdt.activities.TravelActivity;
import com.msapplications.btdt.activities.TravelCountryActivity;
import com.msapplications.btdt.adapters.CountriesAdapter;
import com.msapplications.btdt.adapters.TravelListAdapter;
import com.msapplications.btdt.interfaces.CountryService;
import com.msapplications.btdt.interfaces.OnCompleteLoadCountriesListener;
import com.msapplications.btdt.interfaces.OnCountryClickListener;
import com.msapplications.btdt.objects.itemTypes.travel.CountriesContent;
import com.msapplications.btdt.objects.itemTypes.travel.Country;
import com.msapplications.btdt.objects.itemTypes.travel.CountryImage;
import com.msapplications.btdt.objects.itemTypes.travel.CountryModel;
import com.msapplications.btdt.objects.itemTypes.travel.CountryModelConverter;
import com.msapplications.btdt.rest.RestClientManager;
import com.msapplications.btdt.room_storage.RoomDatabase;
import com.msapplications.btdt.room_storage.travel.CountryViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TravelListFragment extends Fragment implements OnCountryClickListener
{
    private TravelListAdapter adapter;
    private TextView tvEmptyList;
    private RecyclerView recyclerView;
    private CountryViewModel countryViewModel;

    public TravelListFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TravelListFragment.
     */
    public static TravelListFragment newInstance()
    {
        TravelListFragment fragment = new TravelListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_travel_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        tvEmptyList = view.findViewById(R.id.tvEmptyList);
        recyclerView = view.findViewById(R.id.rvTravelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        adapter = new TravelListAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);

        countryViewModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        countryViewModel.getTravelListCountries().observe(this, new Observer<List<CountryModel>>()
        {
            @Override
            public void onChanged(@Nullable List<CountryModel> countries)
            {
                if (countries != null && countries.size() == 0)
                    tvEmptyList.setVisibility(View.VISIBLE);
                else if (tvEmptyList.getVisibility() != View.GONE)
                    tvEmptyList.setVisibility(View.GONE);

                adapter.setCountries(countries);
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_filter_countries).setVisible(false);
    }

    @Override
    public void onCountryClick(View view, int position)
    {
        CountryModel country = adapter.getItem(position);

        switch (view.getId())
        {
            case (R.id.ivBeenThere):
                if (country.isBeenThere())
                    country.setBeenThere(false);
                else
                    country.setBeenThere(true);

                countryViewModel.updateBeenThere(country);
                return;
            case (R.id.ivRemoveFromTravelList):
                Utils.saveBooleanToCache(getContext(), CommonValues.TRAVEL_LIST_CHANGED, true);
                country.setInTravelList(false);
                countryViewModel.updateIsInTravelList(country);
                return;
        }

        Intent intent = new Intent(getActivity(), TravelCountryActivity.class);
        intent.putExtra(CommonValues.COUNTRY_EXTRA, country);
        startActivityForResult(intent, CommonValues.ADDED_TO_TRAVEL_LIST);
    }
}
