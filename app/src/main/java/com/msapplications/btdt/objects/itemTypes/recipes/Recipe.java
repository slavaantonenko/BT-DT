package com.msapplications.btdt.objects.itemTypes.recipes;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.msapplications.btdt.interfaces.Renamable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "recipes_table")
public class Recipe implements Renamable
{
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "recipe_name")
    String name;

    @ColumnInfo(name = "recipe_color")
    int color;

    // Constructor
    public Recipe(int id, String name, int color)
    {
        this.id = id;
        this.name = name;
        this.color = color;
    }


    protected Recipe(Parcel in)
    {
        id = in.readInt();
        name = in.readString();
        color = in.readInt();
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>()
    {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(color);
    }
}
