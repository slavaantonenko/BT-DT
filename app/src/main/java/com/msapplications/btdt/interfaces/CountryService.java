package com.msapplications.btdt.interfaces;

import com.msapplications.btdt.objects.itemTypes.travel.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CountryService
{
//    String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342";
//    String BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780";
//    String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";

    String BASE_URL = "https://restcountries.eu";
    String BASE_FLAGS_URL = "https://www.countryflags.io";

    String BASE_API_URL = BASE_URL + "/com/msapplications/btdt/rest/v2/";

    String COUNTRY_ID = "country_code";
    String FLAG_QUERY_PATH = BASE_FLAGS_URL + "/{" + COUNTRY_ID + "}/flat/48.png";

//    String POPULAR = "movie/popular";

    /* api key */
//    String apiKey = "d0faff448ac31079d756cb781c7e9727";
//    String keyQuery= "?api_key=" + apiKey;

//    String POPULAR_QUERY_PATH = POPULAR + keyQuery;

//    String MOVIE_ID_KEY = "movie_id";
//    String VIDEOS = "movie/{" + MOVIE_ID_KEY + "}/videos";

//    String VIDEOS_QUERY_PATH = VIDEOS + keyQuery;

//    @GET(POPULAR_QUERY_PATH)
//    Call<MovieListResult> searchImage();
    @GET(".")
    Call<List<Country>> getCountries();
}
