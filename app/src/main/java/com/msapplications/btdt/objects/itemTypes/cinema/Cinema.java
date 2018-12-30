package com.msapplications.btdt.objects.itemTypes.cinema;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "cinemas_table")
public class Cinema
{
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "cinema_name")
    String name;

    @ColumnInfo(name = "cinema_city")
    String city;

    @ColumnInfo(name = "logo")
    int logo;

    // Constructor
    public Cinema(int id, String name, String city, int logo)
    {
        this.id = id;
        this.name = name;
        this.city = city;
        this.logo = logo;
    }
}
//public class Cinema implements Parcelable
//{
//    String name;
//    String city;
//    int logo;
//
//    // Constructor
//    public Cinema(String name, String city, int logo)
//    {
//        this.name = name;
//        this.city = city;
//        this.logo = logo;
//    }
//
//    // Parcelling part
//    // The order needs to be the same as in writeToParcel() method
//    protected Cinema(Parcel in)
//    {
//        this.name = in.readString();
//        this.city = in.readString();
//        this.logo = in.readInt();
//    }
//
//    public static final Creator<Cinema> CREATOR = new Creator<Cinema>()
//    {
//        @Override
//        public Cinema createFromParcel(Parcel in) {
//            return new Cinema(in);
//        }
//
//        @Override
//        public Cinema[] newArray(int size) {
//            return new Cinema[size];
//        }
//    };
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags)
//    {
//        dest.writeString(name);
//        dest.writeString(city);
//        dest.writeInt(logo);
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//}
