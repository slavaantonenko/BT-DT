package com.msapplications.btdt.objects.itemTypes.travel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountriesCoordinates
{
    @SerializedName("country_code")
    @Expose
    private String countryCode;

    @SerializedName("latitude")
    @Expose
    private Double latitude;

    @SerializedName("longitude")
    @Expose
    private Double longitude;

    @SerializedName("name")
    @Expose
    private String name;
}
