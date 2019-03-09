package com.msapplications.btdt.objects.itemTypes.recipes;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "recipes_table")
public class Recipe
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
}
