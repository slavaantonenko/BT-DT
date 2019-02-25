package com.msapplications.btdt.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import com.msapplications.btdt.adapters.CategoriesAdapter;
import com.msapplications.btdt.objects.itemTypes.cinema.CinemaHall;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "categories_table")
public class Category implements Parcelable
{
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "category_name")
    String name;

    @TypeConverters(CategoryTypeConverter.class)
    @ColumnInfo(name = "category_type")
    CategoryType type;

    @ColumnInfo(name = "category_picture")
    int backgroundColor;

    public Category(int id, String name, CategoryType type, int backgroundColor)
    {
        this.id = id;
        this.name = name;
        this.type = type;
        this.backgroundColor = backgroundColor;
    }

    // Parcelling part
    // The order needs to be the same as in writeToParcel() method
    protected Category(Parcel in)
    {
        id = in.readInt();
        name = in.readString();
        backgroundColor = in.readInt();
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>()
    {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[0];
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
        dest.writeInt(backgroundColor);
    }
}
