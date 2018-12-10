package com.msapplications.btdt;

import android.graphics.Typeface;
import android.opengl.Visibility;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.msapplications.btdt.objects.itemTypes.CheckListItem;

public class TextEditorButtons
{

    public static void checkboxListener(ImageButton checkBoxView, final CheckBox checkBox, final CheckListItem checkListItem)
    {
        checkBoxView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(checkBox.getVisibility() == View.GONE)
                    checkBox.setVisibility(View.VISIBLE);
                else
                    checkBox.setVisibility(View.GONE);

                checkListItem.setIsCheckbox(!checkListItem.getIsCheckbox());
            }
        });
    }

    public static void boldListener(Button btBold, final EditText text, final CheckListItem checkListItem)
    {
        btBold.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (text.getTypeface() == null || !text.getTypeface().isBold())
                    text.setTypeface(null, Typeface.BOLD);
                else
                    text.setTypeface(null, Typeface.NORMAL);

                checkListItem.setIsBold(!checkListItem.getIsBold());
            }
        });


    }

    public static void italicListener(Button tbItalic, final EditText text, final CheckListItem checkListItem)
    {
        tbItalic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (text.getTypeface() == null || !text.getTypeface().isItalic())
                    text.setTypeface(null, Typeface.ITALIC);
                else
                    text.setTypeface(null, Typeface.NORMAL);

                checkListItem.setIsItalic(!checkListItem.getIsItalic());
            }
        });
    }
}
