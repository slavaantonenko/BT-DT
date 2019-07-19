package com.msapplications.btdt.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.R;
import com.msapplications.btdt.Utils;
import com.msapplications.btdt.activities.TravelCountryActivity;
import com.msapplications.btdt.adapters.CountriesAdapter;
import com.msapplications.btdt.interfaces.OnCountryClickListener;
import com.msapplications.btdt.objects.itemTypes.travel.CountriesContent;
import com.msapplications.btdt.objects.itemTypes.travel.CountryModel;
import com.msapplications.btdt.room_storage.travel.CountryViewModel;

import java.util.List;

//import com.msapplications.btdt.interfaces.OnCompleteLoadCountriesListener;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link TravelCountriesFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link TravelCountriesFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class TravelCountriesFragment extends Fragment implements OnCountryClickListener
{
    private CountriesAdapter adapter;
    private RecyclerView recyclerView;
    private CountryViewModel countryViewModel;

    public TravelCountriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_travel_countries, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rvCountries);

        initializeRecyclerView();

        countryViewModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        countryViewModel.getTravelListCountries().observe(this, new Observer<List<CountryModel>>()
        {
            @Override
            public void onChanged(@Nullable List<CountryModel> countries)
            {
                boolean travelListChanged = Utils.getBooleanFromCache(getContext(), CommonValues.TRAVEL_LIST_CHANGED, false);

                if (travelListChanged) {
                    Utils.saveBooleanToCache(getContext(), CommonValues.TRAVEL_LIST_CHANGED, false);
                    adapter.updateTravelListCountries(countries);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_travel, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_filter_countries).getActionView();
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.filter(s);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCountryClick(View view, int position)
    {
        Intent intent = new Intent(getActivity(), TravelCountryActivity.class);
        intent.putExtra(CommonValues.COUNTRY_EXTRA, adapter.getItem(position));
        startActivity(intent);
    }

    private void initializeRecyclerView()
    {
        int contentRowPixels = (int) getResources().getDimension(R.dimen.ic_width)
                + Utils.pxToDP(getResources(), getResources().getDimensionPixelSize(R.dimen.recycler_view_grid_spacing));

        int mNoOfColumns = Utils.calculateNoOfColumns(getResources(), contentRowPixels);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mNoOfColumns));
        recyclerView.addItemDecoration(
                new Utils.GridSpacingItemDecoration(mNoOfColumns,
                        Utils.pxToDP(getResources(), getResources().getDimensionPixelSize(R.dimen.recycler_view_grid_spacing)),
                        true));

        adapter = new CountriesAdapter(getContext(), CountriesContent.COUNTRIES, this);
        recyclerView.setAdapter(adapter);
    }
}
