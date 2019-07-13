package com.msapplications.btdt.rest;

import android.content.Context;

import com.msapplications.btdt.interfaces.CountryService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClientManager
{
    private static CountryService countryService;

    public static CountryService getCountryServiceInstance(String url)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        countryService = retrofit.create(CountryService.class);

        return countryService;
    }
}
