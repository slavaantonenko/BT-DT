package com.msapplications.btdt.objects.itemTypes.travel;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "countries_table")
public class CountryModel
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String nativeName;
    private String code;
    private String secondaryCode;
    private String callingCode;
    private String capital;
    private String region;
    private Integer population;
    private Double latitude;
    private Double longitude;
    private Double area;
    private String timezone;
    private String currency;
    private String language;
    private String nativeLanguage;
    private String flag;

    public CountryModel(int id, String name, String nativeName, String code, String secondaryCode, String callingCode,
                        String capital, String region, Integer population, Double latitude, Double longitude, Double area,
                        String timezone, String currency, String language, String nativeLanguage, String flag)
    {
        this.id = id;
        this.name = name;
        this.nativeName = nativeName;
        this.code = code;
        this.secondaryCode = secondaryCode;
        this.callingCode = callingCode;
        this.capital = capital;
        this.region = region;
        this.population = population;
        this.latitude = latitude;
        this.longitude = longitude;
        this.area = area;
        this.timezone = timezone;
        this.currency = currency;
        this.language = language;
        this.nativeLanguage = nativeLanguage;
        this.flag = flag;
    }
}
