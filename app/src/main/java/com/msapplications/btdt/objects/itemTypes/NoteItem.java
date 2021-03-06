package com.msapplications.btdt.objects.itemTypes;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "note_item_table")
public class NoteItem implements Comparable<NoteItem>{

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "category_id")
    private int categoryID;

    @ColumnInfo(name = "line_number")
    private int lineNumber;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "is_checkBox")
    private boolean isCheckBox;

    @ColumnInfo(name = "is_checked")
    private boolean isChecked;

    @ColumnInfo(name = "is_bold")
    private boolean isBold;

    @ColumnInfo(name = "is_italic")
    private boolean isItalic;

    @ColumnInfo(name = "recipeID")
    private int recipeID;

    @Ignore
    public NoteItem(int id, int categoryID, int lineNumber) {
        initValues(id, categoryID, lineNumber);
        recipeID = -1;
    }

    public NoteItem(int id, int categoryID, int recipeID, int lineNumber) {
        this.recipeID = recipeID;
        initValues(id, categoryID, lineNumber);
    }

    private void initValues(int id, int categoryID, int lineNumber) {
        this.id = id;
        this.categoryID = categoryID;
        this.lineNumber = lineNumber;
        this.isBold = false;
        this.isChecked = false;
        this.isItalic = false;
    }

    @Override
    public int compareTo(NoteItem o) {
        return this.getLineNumber() - o.getLineNumber();
    }
}
