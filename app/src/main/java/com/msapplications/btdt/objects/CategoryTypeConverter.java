package com.msapplications.btdt.objects;

import android.arch.persistence.room.TypeConverter;

/*
Convert int from db to category type
 */
public class CategoryTypeConverter
{
    @TypeConverter
    public static CategoryType getType(int type)
    {
        if (type == CategoryType.NOTES.getCode())
            return CategoryType.NOTES;
        else if (type == CategoryType.COLLECTION.getCode())
            return CategoryType.COLLECTION;
        else if (type == CategoryType.CINEMA_SEATS.getCode())
            return CategoryType.CINEMA_SEATS;
        else if (type == CategoryType.TRAVEL.getCode())
            return CategoryType.TRAVEL;
        else
            throw new IllegalArgumentException("Could not recognize type code");
    }

    @TypeConverter
    public static Integer getCode(CategoryType type) {
        return type.getCode();
    }
}
