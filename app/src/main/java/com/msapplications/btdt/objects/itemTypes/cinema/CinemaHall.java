package com.msapplications.btdt.objects.itemTypes.cinema;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CinemaHall implements Parcelable
{
    int hall;
    int row;
    boolean like;

    // Constructor
    public CinemaHall(int hall, int row, boolean like)
    {
        this.hall = hall;
        this.row = row;
        this.like = like;
    }

    // Parcelling part
    // The order needs to be the same as in writeToParcel() method
    protected CinemaHall(Parcel in)
    {
        hall = in.readInt();
        row = in.readInt();
        like = in.readByte() != 0;
    }

    public static final Parcelable.Creator<CinemaHall> CREATOR = new Parcelable.Creator<CinemaHall>()
    {
        @Override
        public CinemaHall createFromParcel(Parcel in) {
            return new CinemaHall(in);
        }

        @Override
        public CinemaHall[] newArray(int size) {
            return new CinemaHall[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(hall);
        dest.writeInt(row);
        dest.writeByte((byte) (like ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
