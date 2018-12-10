package com.msapplications.btdt.objects;

import android.graphics.Bitmap;

import com.msapplications.btdt.objects.itemTypes.ItemInCategory;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category implements Serializable
{
    String name;
    ArrayList<? extends ItemInCategory> itemsInCat;
    CategoryType type;
    int previewPic;


    public Category(String name, ArrayList<ItemInCategory> itemInCategories, CategoryType type, int previewPic)
    {
        this.name = name;
        this.itemsInCat = itemInCategories;
        this.type = type;
        this.previewPic = previewPic;
    }
}
