package com.msapplications.btdt.objects.itemTypes.cinema;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "cinema_halls_table")
public class CinemaHall implements Parcelable
{
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "cinema_hall")
    String hall;

    @ColumnInfo(name = "cinema_hall_row")
    String row;

    @ColumnInfo(name = "cinema_hall_status")
    boolean status;

    @ColumnInfo(name = "cinema_name")
    String cinemaName;

    @ColumnInfo(name = "cinema_city")
    String cinemaCity;

    // Constructor
    public CinemaHall(int id, String hall, String row, boolean status, String cinemaName, String cinemaCity)
    {
        this.id = id;
        this.hall = hall;
        this.row = row;
        this.status = status;
        this.cinemaName = cinemaName;
        this.cinemaCity = cinemaCity;
    }

    // Parcelling part
    // The order needs to be the same as in writeToParcel() method
    protected CinemaHall(Parcel in)
    {
        id = in.readInt();
        hall = in.readString();
        row = in.readString();
        status = in.readByte() != 0;
        cinemaName = in.readString();
        cinemaCity = in.readString();
    }

    public static final Parcelable.Creator<CinemaHall> CREATOR = new Parcelable.Creator<CinemaHall>()
    {
        @Override
        public CinemaHall createFromParcel(Parcel in) {
            return new CinemaHall(in);
        }

        @Override
        public CinemaHall[] newArray(int size) {
            return new CinemaHall[0];
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
        dest.writeString(hall);
        dest.writeString(row);
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeString(cinemaName);
        dest.writeString(cinemaCity);
    }
}
