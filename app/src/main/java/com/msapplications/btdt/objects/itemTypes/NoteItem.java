package com.msapplications.btdt.objects.itemTypes;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
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


    public NoteItem(int id, int categoryID, int lineNumber) {
        this.id = id;
        this.categoryID = categoryID;
        this.lineNumber = lineNumber;
        this.isBold = false;
        this.isChecked = false;
        this.isItalic = false;
    }

//    public NoteItem(int id, int categoryID, boolean isChecked) {
//        this.id = id;
//        this.categoryID = categoryID;
//        this.isBold = false;
//        this.isChecked = isChecked;
//        this.isItalic = false;
//    }

    @Override
    public int compareTo(NoteItem o) {
        return this.getLineNumber() - o.getLineNumber();
    }
}
