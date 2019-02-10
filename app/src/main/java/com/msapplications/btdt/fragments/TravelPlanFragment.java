package com.msapplications.btdt.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.msapplications.btdt.R;
import com.msapplications.btdt.Utils;
import com.msapplications.btdt.adapters.CountriesAdapter;
import com.msapplications.btdt.interfaces.CountryService;
import com.msapplications.btdt.objects.itemTypes.travel.CountriesContent;
import com.msapplications.btdt.objects.itemTypes.travel.Country;
import com.msapplications.btdt.objects.itemTypes.travel.CountryModel;
import com.msapplications.btdt.objects.itemTypes.travel.CountryModelConverter;
import com.msapplications.btdt.room_storage.RoomDatabase;

import java.util.List;

import rest.RestClientManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link TravelPlanFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link TravelPlanFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class TravelPlanFragment extends Fragment
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

//    private OnFragmentInteractionListener mListener;

    public TravelPlanFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TravelPlanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TravelPlanFragment newInstance(String param1, String param2)
    {
        TravelPlanFragment fragment = new TravelPlanFragment();
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
        return inflater.inflate(R.layout.fragment_travel_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rvCountries);
        int mNoOfColumns = Utils.calculateNoOfColumns(getContext());

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
//        gridLayoutManager.scrollHorizontallyBy()

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mNoOfColumns));
//        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
//        recyclerView.addItemDecoration(new Utils.SpacesItemDecoration(spacingInPixels));
        Utils.ItemOffsetDecoration itemDecoration = new Utils.ItemOffsetDecoration(getContext(), R.dimen.spacing);
        recyclerView.addItemDecoration(itemDecoration);
        progressBar = view.findViewById(R.id.pbCountries);

        loadCountries();
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri)
//    {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context)
//    {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach()
//    {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener
//    {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }

    private void loadCountries()
    {
        CountriesContent.clear();
        List<CountryModel> cachedMovies = RoomDatabase.getDatabase(getActivity()).countryDao().getCountries();
        CountriesContent.COUNTRIES.addAll(cachedMovies);
        adapter = new CountriesAdapter(getContext(), CountriesContent.COUNTRIES);
        recyclerView.setAdapter(adapter);

        if (cachedMovies.size() == 0)
            progressBar.setVisibility(View.VISIBLE);

        CountryService countryService = RestClientManager.getCountryServiceInstance(getContext());
        countryService.getCountries().enqueue(new Callback<List<Country>>()
        {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response)
            {
                Log.i("response", "response");
                progressBar.setVisibility(View.GONE);

                if (response.code() == 200) {
                    CountriesContent.clear();
                    CountriesContent.COUNTRIES.addAll(CountryModelConverter.convertResult(response.body()));
                    adapter.setData(CountriesContent.COUNTRIES);
//                    RoomDatabase.getDatabase(getActivity()).countryDao().deleteAll();
//                    RoomDatabase.getDatabase(getActivity()).countryDao().insertAll(CountriesContent.COUNTRIES);
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t)
            {
                progressBar.setVisibility(View.GONE);
                Log.i("failure", t.getMessage());
            }
        });
//        countryService.getCountries().enqueue(new Callback<List<Country>>()
//        {
//            @Override
//            public void onResponse(Call<List<Country>> call, Response<List<Country>> response)
//            {
//                Log.i("response", "response");
//                progressBar.setVisibility(View.GONE);
//
//                if (response.code() == 200) {
//                    CountriesContent.clear();
//                    CountriesContent.COUNTRIES.addAll(CountryModelConverter.convertResult(response.body()));
//                    adapter.setData(CountriesContent.COUNTRIES);
//                    RoomDatabase.getDatabase(getActivity()).countryDao().deleteAll();
//                    RoomDatabase.getDatabase(getActivity()).countryDao().insertAll(CountriesContent.COUNTRIES);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Country>> call, Throwable t)
//            {
//                progressBar.setVisibility(View.GONE);
//                Log.i("failure", t.getMessage());
//            }
//        });
//        countryService.getCountries().enqueue(new Callback<Country>()
//        {
//            @Override
//            public void onResponse(Call<Country> call, Response<Country> response)
//            {
//                Log.i("response", "response");
//                progressBar.setVisibility(View.GONE);
//
//                if (response.code() == 200) {
//                    CountriesContent.clear();
//                    CountriesContent.COUNTRIES.addAll(CountryModelConverter.convertResult(response.body()));
//                    adapter.setData(CountriesContent.COUNTRIES);
//                    RoomDatabase.getDatabase(getActivity()).countryDao().deleteAll();
//                    RoomDatabase.getDatabase(getActivity()).countryDao().insertAll(CountriesContent.COUNTRIES);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Country> call, Throwable t)
//            {
//                progressBar.setVisibility(View.GONE);
//                Log.i("failure", t.getMessage());
//            }
//        });
//        countryService.getCountries().enqueue(new Callback<CountryResults>()
//        {
//            @Override
//            public void onResponse(Call<CountryResults> call, Response<CountryResults> response)
//            {
//                Log.i("response", "response");
//                progressBar.setVisibility(View.GONE);
//
//                if (response.code() == 200) {
//                    CountriesContent.clear();
//                    CountriesContent.COUNTRIES.addAll(CountryModelConverter.convertResult(response.body()));
//                    adapter.setData(CountriesContent.COUNTRIES);
//                    RoomDatabase.getDatabase(getActivity()).countryDao().deleteAll();
//                    RoomDatabase.getDatabase(getActivity()).countryDao().insertAll(CountriesContent.COUNTRIES);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CountryResults> call, Throwable t)
//            {
//                progressBar.setVisibility(View.GONE);
//                Log.i("failure", "failure");
////                Toast.makeText(MoviesActivity.this, R.string.something_went_wrong_text, Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
