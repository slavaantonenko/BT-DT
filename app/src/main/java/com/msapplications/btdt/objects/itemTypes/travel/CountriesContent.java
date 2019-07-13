package com.msapplications.btdt.objects.itemTypes.travel;

import android.os.Build;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CountriesContent
{
    public static final List<CountryModel> COUNTRIES = new ArrayList<>();

    public static void clear() {
        COUNTRIES.clear();
    }
//
//    public static List<CountryModel> getTravelListCountries()
//    {
//        //TODO Enable This when upgrade to Java 1.8 and API 24.
////        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
////            return COUNTRIES.stream().filter(CountryModel::isInTravelList).collect(Collectors.toList());
////        else
////        {
//            List<CountryModel> countries = new ArrayList<>();
//            for (CountryModel country : COUNTRIES)
//                if (country.isInTravelList())
//                    countries.add(country);
//
//            return countries;
////        }
//    }
}
