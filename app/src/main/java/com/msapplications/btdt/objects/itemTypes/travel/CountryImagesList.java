package com.msapplications.btdt.objects.itemTypes.travel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryImagesList
{
    @SerializedName("totalHits")
    @Expose
    private Integer totalHits;

    @SerializedName("hits")
    @Expose
    private List<CountryImage> hits = null;

    @SerializedName("total")
    @Expose
    private Integer total;
}
