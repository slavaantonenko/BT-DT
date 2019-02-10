package com.msapplications.btdt.objects.itemTypes.travel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Translations
{
    @SerializedName("de")
    @Expose
    private String de;

    @SerializedName("es")
    @Expose
    private String es;

    @SerializedName("fr")
    @Expose
    private String fr;

    @SerializedName("ja")
    @Expose
    private String ja;

    @SerializedName("it")
    @Expose
    private String it;

    @SerializedName("br")
    @Expose
    private String br;

    @SerializedName("pt")
    @Expose
    private String pt;

    @SerializedName("nl")
    @Expose
    private String nl;

    @SerializedName("hr")
    @Expose
    private String hr;

    @SerializedName("fa")
    @Expose
    private String fa;
}
