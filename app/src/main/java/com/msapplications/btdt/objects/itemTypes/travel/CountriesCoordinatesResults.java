package com.msapplications.btdt.objects.itemTypes.travel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountriesCoordinatesResults
{
    @SerializedName("results")
    @Expose
    private List<CountriesCoordinates> results = null;
}
