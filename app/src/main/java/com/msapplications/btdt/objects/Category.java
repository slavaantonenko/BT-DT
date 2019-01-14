package com.msapplications.btdt.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

import com.msapplications.btdt.objects.itemTypes.ItemInCategory;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "cinemas_table")
public class Category implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "category_name")
    String name;

    @ColumnInfo(name = "category_items")
    ArrayList<? extends ItemInCategory> itemsInCat;

    @ColumnInfo(name = "category_type")
    CategoryType type;

    @ColumnInfo(name = "category_picture")
    int previewPic;


    public Category(String name, ArrayList<ItemInCategory> itemInCategories, CategoryType type, int previewPic)
    {
        this.name = name;
        this.itemsInCat = itemInCategories;
        this.type = type;
        this.previewPic = previewPic;
    }
}
