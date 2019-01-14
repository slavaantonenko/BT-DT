package com.msapplications.btdt.objects.itemTypes.cinema;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

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
