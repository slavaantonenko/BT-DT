package com.msapplications.btdt.objects.itemTypes.travel;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "countries_table")
public class CountryModel implements Parcelable
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

    // Parcelling part
    // The order needs to be the same as in writeToParcel() method
    protected CountryModel(Parcel in)
    {
        id = in.readInt();
        name = in.readString();
        nativeName = in.readString();
        code = in.readString();
        secondaryCode = in.readString();
        callingCode = in.readString();
        capital = in.readString();
        region = in.readString();
        population = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        area = in.readDouble();
        timezone = in.readString();
        currency = in.readString();
        language = in.readString();
        nativeLanguage = in.readString();
        flag = in.readString();
    }

    public static final Parcelable.Creator<CountryModel> CREATOR = new Parcelable.Creator<CountryModel>()
    {
        @Override
        public CountryModel createFromParcel(Parcel in) {
            return new CountryModel(in);
        }

        @Override
        public CountryModel[] newArray(int size) {
            return new CountryModel[0];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(nativeName);
        dest.writeString(code);
        dest.writeString(secondaryCode);
        dest.writeString(callingCode);
        dest.writeString(capital);
        dest.writeString(region);
        dest.writeInt(population);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeDouble(area);
        dest.writeString(timezone);
        dest.writeString(currency);
        dest.writeString(language);
        dest.writeString(nativeLanguage);
        dest.writeString(flag);
    }
}
