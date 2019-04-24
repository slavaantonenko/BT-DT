package com.msapplications.btdt.activities;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.msapplications.btdt.R;
import com.msapplications.btdt.fragments.TravelCountriesFragment;
import com.msapplications.btdt.fragments.TravelListFragment;
import com.msapplications.btdt.fragments.TravelMapFragment;
import com.msapplications.btdt.interfaces.CountryService;
import com.msapplications.btdt.objects.itemTypes.travel.CountriesContent;
import com.msapplications.btdt.objects.itemTypes.travel.Country;
import com.msapplications.btdt.objects.itemTypes.travel.CountryModel;
import com.msapplications.btdt.objects.itemTypes.travel.CountryModelConverter;
import com.msapplications.btdt.rest.RestClientManager;
import com.msapplications.btdt.room_storage.RoomDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TravelActivity extends AppCompatActivity //implements TravelCountriesFragment.OnCompleteLoadCountriesListener
{
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter sectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ProgressBar pbTravel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pbTravel = findViewById(R.id.pbTravel);
        pbTravel.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = findViewById(R.id.vpTravelContainer);
        tabLayout = findViewById(R.id.tabs);

        loadCountries(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_travel, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadViewPager()
    {
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        pbTravel.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment = null;

            switch (position)
            {
                case 0:
                    fragment = new TravelListFragment();
                    break;
                case 1:
                    fragment = new TravelCountriesFragment();
                    break;
                case 2:
                    fragment = new TravelMapFragment();
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount()
        {
            // Show 3 total pages.
            return 3;
        }
    }

    private void loadCountries(final Context context)
    {
        CountriesContent.clear();
        List<CountryModel> cachedMovies = RoomDatabase.getDatabase(context).countryDao().getCountries();
        CountriesContent.COUNTRIES.addAll(cachedMovies);

        if (CountriesContent.COUNTRIES.size() == 0)
        {
            CountryService countryService = RestClientManager.getCountryServiceInstance(CountryService.BASE_API_URL);
            countryService.getCountries().enqueue(new Callback<List<Country>>()
            {
                @Override
                public void onResponse(Call<List<Country>> call, Response<List<Country>> response)
                {
                    Log.i("response", "response");

                    if (response.code() == 200 && response.body() != null) {
                        RoomDatabase.getDatabase(context).countryDao().insertAll(CountryModelConverter.convertResult(response.body()));
                        CountriesContent.COUNTRIES.addAll(RoomDatabase.getDatabase(context).countryDao().getCountries());

                        loadViewPager();
                    }
                }

                @Override
                public void onFailure(Call<List<Country>> call, Throwable t) {
                    Log.i("failure", t.getMessage());
                }
            });
        }
        else
            loadViewPager();
    }
}
