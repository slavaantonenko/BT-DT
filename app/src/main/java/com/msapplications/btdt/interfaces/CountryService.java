package com.msapplications.btdt.interfaces;

import com.msapplications.btdt.rest.APIKeys;
import com.msapplications.btdt.objects.itemTypes.travel.Country;
import com.msapplications.btdt.objects.itemTypes.travel.CountryImagesList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CountryService
{
    String BASE_URL = "https://restcountries.eu";
    String BASE_FLAGS_URL = "https://www.countryflags.io/";
    String BASE_IMAGES_URL = "https://pixabay.com";

    String BASE_API_URL = BASE_URL + "/rest/v2/";
    String BASE_IMAGES_API_URL = BASE_IMAGES_URL + "/api/";

    String IMAGES_KEY_QUERY = "?key=" + APIKeys.IMAGES_API_KEY;
    String IMAGES_QUERY_PATH = IMAGES_KEY_QUERY + "&category=travel&image_type=photo&orientation=horizontal&per_page=3";

    @GET(".")
    Call<List<Country>> getCountries();

    @GET(IMAGES_QUERY_PATH)
    Call<CountryImagesList> getCountryImages(@Query("q") String capitalCity);
}
