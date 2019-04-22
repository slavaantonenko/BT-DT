package com.msapplications.btdt.objects.itemTypes.recipes;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "note_item_table")
public class RecipeIngredients implements Comparable<RecipeIngredients>
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "recipe_id")
    private int recipeID;

    @ColumnInfo(name = "line_number")
    private int lineNumber;

    @ColumnInfo(name = "text")
    private String text;

    public RecipeIngredients(int id, int recipeID, int lineNumber) {
        this.id = id;
        this.recipeID = recipeID;
        this.lineNumber = lineNumber;
    }

    @Override
    public int compareTo(RecipeIngredients o) {
        return this.getLineNumber() - o.getLineNumber();
    }
}
