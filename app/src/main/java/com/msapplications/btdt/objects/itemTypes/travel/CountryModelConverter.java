package com.msapplications.btdt.objects.itemTypes.travel;

import com.msapplications.btdt.interfaces.CountryService;

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
                        getCurrenciesFromList(country.getCurrencies()),
                        getLanguages(country.getLanguages(), false),
                        getLanguages(country.getLanguages(), true),
                        CountryService.BASE_FLAGS_URL + country.getAlpha2Code() + "/flat/64.png"));
        }

        return result;
    }

    /**
     * This method builds a string of all country currency.
     * @param currenciesList
     * @return
     */
    private static String getCurrenciesFromList(List<Currency> currenciesList)
    {
        StringBuilder currencies = new StringBuilder();

        for (int i = 0; i < currenciesList.size(); i++)
        {
            Currency currency = currenciesList.get(i);

            if (currency.getSymbol() != null)
                currencies.append(currency.getName() + " (" + currency.getSymbol() + ")" + " (" + currency.getCode() + ")");
            else
                currencies.append(currency.getName() + " (" + currency.getCode() + ")");

            if (i < currenciesList.size() - 1)
                currencies.append(", ");
        }

        return currencies.toString();
    }

    /**
     * This method builds a string of all country language.
     * @param languagesList
     * @param nativeName if false build only language names otherwise only native names.
     * @return
     */
    private static String getLanguages(List<Language> languagesList, boolean nativeName)
    {
        StringBuilder languages = new StringBuilder();

        for (int i = 0; i < languagesList.size(); i++)
        {
            Language language = languagesList.get(i);

            if (!nativeName)
                languages.append(language.getName());
            else
                languages.append(language.getNativeName());

            if (i < languagesList.size() - 1)
                languages.append(", ");
        }

        return languages.toString();
    }
}
