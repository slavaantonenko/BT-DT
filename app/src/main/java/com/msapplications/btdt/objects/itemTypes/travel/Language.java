package com.msapplications.btdt.objects.itemTypes.travel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Language
{
    @SerializedName("iso639_1")
    @Expose
    private String iso6391;

    @SerializedName("iso639_2")
    @Expose
    private String iso6392;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("nativeName")
    @Expose
    private String nativeName;
}
