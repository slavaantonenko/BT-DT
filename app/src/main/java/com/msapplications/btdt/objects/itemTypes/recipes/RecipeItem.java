package com.msapplications.btdt.objects.itemTypes.recipes;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "note_item_table")
public class RecipeItem implements Comparable<RecipeItem>
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "recipe_id")
    private int recipeID;

    @ColumnInfo(name = "line_number")
    private int lineNumber;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "info_type")
    private int recipeInfoType; // 0 for grocery ,1 for process


    public RecipeItem(int id, int recipeID, int lineNumber, int recipeInfoType) {
        this.id = id;
        this.recipeID = recipeID;
        this.lineNumber = lineNumber;
        this.recipeInfoType = recipeInfoType;
    }

    @Override
    public int compareTo(RecipeItem o) {
        return this.getLineNumber() - o.getLineNumber();
    }
}
