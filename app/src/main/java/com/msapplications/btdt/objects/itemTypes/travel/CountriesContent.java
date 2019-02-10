package com.msapplications.btdt.objects.itemTypes.travel;

import java.util.ArrayList;

public class CountriesContent
{
    public static final ArrayList<CountryModel> COUNTRIES = new ArrayList<>();

    public static void clear() {
        COUNTRIES.clear();
    }

    public static void addMovie(CountryModel country) {
        COUNTRIES.add(country);
    }
}
