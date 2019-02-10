package com.msapplications.btdt.objects.itemTypes.travel;

import java.util.ArrayList;
import java.util.List;

public class CountryModelConverter
{
    public static ArrayList<CountryModel> convertResult(List<Country> countries)
    {
        ArrayList<CountryModel> result = new ArrayList<>();
        for (Country country : countries)
        {
            if (country.getLatlng().size() == 2)
                result.add(new CountryModel(0,
                        country.getName(),
                        country.getNativeName(),
                        country.getAlpha2Code(),
                        country.getAlpha3Code(),
                        country.getCallingCodes().get(0),
                        country.getCapital(),
                        country.getRegion(),
                        country.getPopulation(),
                        country.getLatlng().get(0),
                        country.getLatlng().get(1),
                        country.getArea(),
                        country.getTimezones().get(0),
                        country.getCurrencies().get(0).getName(),
                        country.getLanguages().get(0).getName(),
                        country.getLanguages().get(0).getNativeName(),
                        "https://www.countryflags.io/" + country.getAlpha2Code() + "/flat/64.png"));
//                        country.getFlag()));
        }

        return result;
    }
}
