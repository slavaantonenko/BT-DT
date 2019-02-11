package com.msapplications.btdt.rest;

import android.content.Context;

import com.msapplications.btdt.interfaces.CountryService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClientManager
{
    private static CountryService countryService;

    public static CountryService getCountryServiceInstance(final Context context)
    {
        if (countryService == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CountryService.BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            countryService = retrofit.create(CountryService.class);
        }

        return countryService;
    }
}
