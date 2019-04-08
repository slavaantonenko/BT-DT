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
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.R;
import com.msapplications.btdt.Utils;
import com.msapplications.btdt.activities.TravelCountryActivity;
import com.msapplications.btdt.adapters.CountriesAdapter;
//import com.msapplications.btdt.interfaces.OnCompleteLoadCountriesListener;
import com.msapplications.btdt.interfaces.OnCountryClickListener;
import com.msapplications.btdt.objects.itemTypes.travel.CountriesContent;
import com.msapplications.btdt.objects.itemTypes.travel.CountryModel;
import com.msapplications.btdt.room_storage.travel.CountryViewModel;

import java.util.List;

import okhttp3.internal.Util;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link TravelCountriesFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link TravelCountriesFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class TravelCountriesFragment extends Fragment implements OnCountryClickListener//, OnCompleteLoadCountriesListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProgressBar progressBar;
    private CountriesAdapter adapter;
    private RecyclerView recyclerView;
    private int clickedPosition;
    private CountryViewModel countryViewModel;
//    OnFragmentInteractionListener onFragmentInteractionListener;
//    OnCompleteLoadCountriesListener callback;

//    private OnFragmentInteractionListener mListener;

    public TravelCountriesFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TravelCountriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TravelCountriesFragment newInstance(String param1, String param2)
    {
        TravelCountriesFragment fragment = new TravelCountriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        progressBar = view.findViewById(R.id.pbCountries);
        recyclerView = view.findViewById(R.id.rvCountries);
        final TextView test = view.findViewById(R.id.posTest);
        test.post(new Runnable()
        {
            @Override
            public void run()
            {
                test.getX();
            }
        });

        int contentRowPixels = (int) getResources().getDimension(R.dimen.ic_width) + Utils.dpToPx(getResources(), 20);

//        int mNoOfColumns = Utils.calculateNoOfColumns(getContext(),
//                (int) getResources().getDimension(R.dimen.ic_width) + Utils.dpToPx(getResources(), 20));
        int mNoOfColumns = Utils.calculateNoOfColumns(getContext(), contentRowPixels);

        int margin = getResources().getDisplayMetrics().widthPixels % contentRowPixels;

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
//        gridLayoutManager.scrollHorizontallyBy()
//        GridLayout.LayoutParams params =
//                new GridLayout.LayoutParams(view.getLayoutParams());
//        params.width = 0;
//        params.height = 0;
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), mNoOfColumns);
//        gridLayoutManager.generateLayoutParams(params);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mNoOfColumns));
//        recyclerView.setLayoutManager(gridLayoutManager);


//        recyclerView.setPaddingRelative(margin / 2, 0, margin / 2, 0);
//        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
//        recyclerView.addItemDecoration(new Utils.SpacesItemDecoration(spacingInPixels));
//        Utils.ItemOffsetDecoration itemDecoration = new Utils.ItemOffsetDecoration(getContext(), R.dimen.recycler_view_grid_spacing);
//        recyclerView.addItemDecoration(itemDecoration);

        adapter = new CountriesAdapter(getContext(), CountriesContent.COUNTRIES, this);
        recyclerView.setAdapter(adapter);

        recyclerView.post(new Runnable()
        {
            @Override
            public void run()
            {
                recyclerView.getX();
            }
        });

        countryViewModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        countryViewModel.getTravelListCountries().observe(this, new Observer<List<CountryModel>>()
        {
            @Override
            public void onChanged(@Nullable List<CountryModel> countries)
            {
                boolean travelListChanged = Utils.getBooleanFromCache(getContext(), CommonValues.TRAVEL_LIST_CHANGED, false);

                if (travelListChanged)
                {
                    Utils.saveBooleanToCache(getContext(), CommonValues.TRAVEL_LIST_CHANGED, false);
                    adapter.updateTravelListCountries(countries);
//                    for (CountryModel country : countries)
//                    {
//                        for (CountryModel adapterCountry : adapter.getCountries())
//                        {
//                            if (country.getId() == adapterCountry.getId())
//                            {
//                                adapterCountry.setInTravelList(true);
//
//                                if (adapterCountry.getImage() == null)
//                                    adapterCountry.setImage(country.getImage());
//                            }
//                            else
//                                adapterCountry.setInTravelList(false);
//                        }
//                    }
                }
            }
        });

//        progressBar.setVisibility(View.VISIBLE);
//        TravelActivity.loadCountries(getContext(), this);
//        onFragmentInteractionListener.onFragmentInteraction();
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
        clickedPosition = position;
        Intent intent = new Intent(getActivity(), TravelCountryActivity.class);
        intent.putExtra(CommonValues.COUNTRY_EXTRA, adapter.getItem(position));
//        startActivityForResult(intent, CommonValues.ADDED_TO_TRAVEL_LIST);
        startActivity(intent);
//        TravelCountryFragment fragment = new TravelCountryFragment();//.newInstance(CountriesContent.COUNTRIES.get(position));
//        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
////                .beginTransaction()
//                ft.add(R.id.vpTravelContainer, fragment);
//                ft.addToBackStack(this.getClass().getName());
//                ft.commit();
    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == resultCode)
//        {
//            CountryModel country = data.getParcelableExtra(CommonValues.COUNTRY_EXTRA);
//            adapter.getItem(clickedPosition).setInTravelList(country.isInTravelList());
//            adapter.getItem(clickedPosition).setImage(country.getImage());
//        }
//    }

    //    @Override
//    public void onCompleteLoadCountries()
//    {
//
//    }

//
//    @Override
//    public void onAttach(Context context)
//    {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        onFragmentInteractionListener = null;
//    }


//    @Override
//    public void onCompleteLoadCountries() {
//        adapter = new CountriesAdapter(getContext(), CountriesContent.COUNTRIES, this);
//        recyclerView.setAdapter(adapter);
//        progressBar.setVisibility(View.GONE);
//    }

//    public void setOnCompleteLoadCountriesListener(OnCompleteLoadCountriesListener callback) {
//        this.callback = callback;
//    }
//


//    @Override
//    public void onCompleteLoadCountries()
//    {
//        adapter = new CountriesAdapter(getContext(), CountriesContent.COUNTRIES, this);
//        recyclerView.setAdapter(adapter);
//        progressBar.setVisibility(View.GONE);
//    }

//    public void setOnCompleteLoadCountriesListener(OnCompleteLoadCountriesListener callback) {
//        this.callback = callback;
//    }

//    public interface OnCompleteLoadCountriesListener {
//        void onCompleteLoadCountries();
//    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener
//    {
//        // TODO: Update argument type and name
//        List<CountryModel> onFragmentInteraction();
//    }


//    private void loadCountries()
//    {
//        TravelActivity.loadCountries(getContext(), progressBar);

//        adapter = new CountriesAdapter(getContext(), CountriesContent.COUNTRIES, this);
//        recyclerView.setAdapter(adapter);
//    }

//    private void loadCountries()
//    {
//        CountriesContent.clear();
//        List<CountryModel> cachedMovies = RoomDatabase.getDatabase(getActivity()).countryDao().getCountries();
//        CountriesContent.COUNTRIES.addAll(cachedMovies);
//
//        if (CountriesContent.COUNTRIES.size() == 0)
//        {
//            progressBar.setVisibility(View.VISIBLE);
//
//            CountryService countryService = RestClientManager.getCountryServiceInstance(CountryService.BASE_API_URL);
//            countryService.getCountries().enqueue(new Callback<List<Country>>()
//            {
//                @Override
//                public void onResponse(Call<List<Country>> call, Response<List<Country>> response)
//                {
//                    Log.i("response", "response");
//
//                    if (response.code() == 200 && response.body() != null)
//                    {
//                        CountriesContent.clear();
//                        CountriesContent.COUNTRIES.addAll(CountryModelConverter.convertResult(response.body()));
//                        adapter.setData(CountriesContent.COUNTRIES);
//                        RoomDatabase.getDatabase(getActivity()).countryDao().deleteAll();
//                        RoomDatabase.getDatabase(getActivity()).countryDao().insertAll(CountriesContent.COUNTRIES);
//                    }
//
//                    progressBar.setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onFailure(Call<List<Country>> call, Throwable t)
//                {
//                    progressBar.setVisibility(View.GONE);
//                    Log.i("failure", t.getMessage());
//                }
//            });
//        }
//        else {
//            adapter = new CountriesAdapter(getContext(), CountriesContent.COUNTRIES, this);
//            recyclerView.setAdapter(adapter);
//        }
//    }
}
