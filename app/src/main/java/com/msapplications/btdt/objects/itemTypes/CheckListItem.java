package com.msapplications.btdt.objects.itemTypes;

import android.graphics.Typeface;
import android.widget.Button;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckListItem extends ItemInCategory implements Serializable
{
    private String name;
    private Boolean isChecked;
    private Boolean isBold;
    private Boolean isCheckbox;
    private Boolean isItalic;

    public CheckListItem() {
        name = "";
        isChecked = false;
        isCheckbox = false;
        isBold = false;
        isItalic = false;
    }

    public CheckListItem(String name, boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
    }
}
