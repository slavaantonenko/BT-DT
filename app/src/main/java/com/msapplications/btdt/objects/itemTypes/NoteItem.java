package com.msapplications.btdt.objects.itemTypes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "note_item_table")
public class NoteItem //extends ItemInCategory
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    String text;
    boolean isChecked;
    boolean isBold;
    boolean isItalic;


    public NoteItem() {
        this.isBold = false;
        this.isChecked = false;
        this.isItalic = false;
    }

    public NoteItem(boolean isChecked) {
        this.isBold = false;
        this.isChecked = isChecked;
        this.isItalic = false;
    }
}
