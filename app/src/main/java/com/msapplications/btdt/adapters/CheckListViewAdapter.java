package com.msapplications.btdt.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.msapplications.btdt.TextEditorButtons;
import com.msapplications.btdt.R;
import com.msapplications.btdt.interfaces.OnEnterKeyClickedListener;
import com.msapplications.btdt.objects.itemTypes.CheckListItem;

import java.util.ArrayList;

public class CheckListViewAdapter extends RecyclerView.Adapter<CheckListViewAdapter.ViewHolder>
{
    private LayoutInflater inflater;
    private ArrayList<CheckListItem> checkListItems;
    private OnEnterKeyClickedListener enterKeyClickListener;
    private ImageButton btnCheckbox;
    private Button btnBold;
    private Button btnItalic;

    public CheckListViewAdapter(Context context, ArrayList<CheckListItem> checkListItems, OnEnterKeyClickedListener listener)
    {
        this.checkListItems = checkListItems;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        enterKeyClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder //implements View.OnClickListener
    {
        protected final CheckBox checkBoxItem;
        protected final EditText editText;


        public ViewHolder(View view)
        {
            super(view);
            checkBoxItem = view.findViewById(R.id.checkBoxItem);
            editText = view.findViewById(R.id.lineText);
        }

        public void onBindViewHolder(final CheckListItem checkListItem)
        {
            editText.setText(checkListItem.getName());
            checkBoxItem.setChecked(checkListItem.getIsChecked());

            //set checkbox listener
            checkBoxItem.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    checkListItem.setIsChecked(checkBoxItem.isChecked());
                }
            });

        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_checklist, parent, false);
        ConstraintLayout constraintLayoutParent = ((ConstraintLayout)parent.getParent());
        btnBold = constraintLayoutParent.findViewById(R.id.bold);
        btnItalic = constraintLayoutParent.findViewById(R.id.italic);
        btnCheckbox = constraintLayoutParent.findViewById(R.id.checkbox);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.onBindViewHolder(checkListItems.get(position));

        final EditText currentEditText = holder.editText;
        if(position == checkListItems.size()-1) {
            currentEditText.setFocusableInTouchMode(true);
            currentEditText.requestFocus();
            setButtons(checkListItems.get(position), holder);
        }

        //setButtons(checkListItems.get(position), holder);
        currentEditText.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    enterKeyClickListener.OnEnterKeyClicked(currentEditText, position);
                }
                return false;
            }
        });

        currentEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(hasFocus)
                {
                    setButtons(checkListItems.get(position), holder);
                }
            }
        });

        currentEditText.setTypeface(null, Typeface.NORMAL);

        if (checkListItems.get(position).getIsBold())
            currentEditText.setTypeface(null, Typeface.BOLD);
        if (checkListItems.get(position).getIsItalic())
            currentEditText.setTypeface(null, Typeface.ITALIC);
        if(checkListItems.get(position).getIsCheckbox())
            holder.checkBoxItem.setVisibility(View.VISIBLE);

        if(checkListItems.size() > 1 && position > 0 && checkListItems.get(position-1).getIsCheckbox()) {
            holder.checkBoxItem.setVisibility(View.VISIBLE);
            checkListItems.get(position).setIsChecked(true);
        }

    }


    @Override
    public int getItemCount() {
        return checkListItems.size();
    }

    private void setButtons(CheckListItem checkListItem, ViewHolder holder)
    {
        if(btnBold != null) {
            TextEditorButtons.boldListener(btnBold, holder.editText, checkListItem);
            TextEditorButtons.italicListener(btnItalic, holder.editText, checkListItem);
            TextEditorButtons.checkboxListener(btnCheckbox, holder.checkBoxItem, checkListItem);
        }
    }
}
