package com.msapplications.btdt.adapters;

import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.msapplications.btdt.R;
import com.msapplications.btdt.interfaces.OnEnterKeyClickedListener;
import com.msapplications.btdt.objects.itemTypes.NoteItem;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>
{
    ArrayList<NoteItem> notes;
    OnEnterKeyClickedListener listener;
    EditText focusedEditText;
    CheckBox focusedCheckbox;
    int currentIndex;

    public NotesAdapter(ArrayList<NoteItem> notes, OnEnterKeyClickedListener listener) {
        this.notes = notes;
        this.listener = listener;
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder
    {
        public EditText editText;
        public CheckBox checkbox;

        public NotesViewHolder(View view)
        {
            super(view);
            editText = view.findViewById(R.id.etNote);
            checkbox = view.findViewById(R.id.checkbox);
        }
    }

    @Override
    public NotesAdapter.NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);


        FrameLayout constraintLayoutParent = ((FrameLayout)parent.getParent());
        Button btnBold = constraintLayoutParent.findViewById(R.id.bold);
        Button btnItalic = constraintLayoutParent.findViewById(R.id.italic);
        ImageButton btnCheckbox = constraintLayoutParent.findViewById(R.id.checkbox_btn);

        btnBold.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                NoteItem noteItem = notes.get(currentIndex);
                if (focusedEditText!= null) {
                    Typeface typeface = focusedEditText.getTypeface();

                    if(typeface == null || !focusedEditText.getTypeface().isBold()) {
                        noteItem.setBold(true);

                        if(typeface != null && focusedEditText.getTypeface().isItalic())
                            focusedEditText.setTypeface(typeface, Typeface.BOLD_ITALIC);
                        else
                            focusedEditText.setTypeface(typeface, Typeface.BOLD);
                    }
                    else {
                        noteItem.setBold(false);

                        if (focusedEditText.getTypeface().isItalic())
                            focusedEditText.setTypeface(typeface, Typeface.ITALIC);
                        else
                            focusedEditText.setTypeface(null, Typeface.NORMAL);
                    }
                }
            }
        });

        btnItalic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                NoteItem noteItem = notes.get(currentIndex);
                if (focusedEditText!= null) {
                    Typeface typeface = focusedEditText.getTypeface();

                    if (typeface == null || !focusedEditText.getTypeface().isItalic()) {
                        noteItem.setItalic(true);

                        if(typeface != null && focusedEditText.getTypeface().isBold())
                            focusedEditText.setTypeface(typeface, Typeface.BOLD_ITALIC);
                        else
                            focusedEditText.setTypeface(typeface, Typeface.ITALIC);
                    }
                    else {
                        noteItem.setItalic(false);

                        if(focusedEditText.getTypeface().isBold())
                            focusedEditText.setTypeface(typeface, Typeface.BOLD);
                        else
                            focusedEditText.setTypeface(null, Typeface.NORMAL);
                    }
                }
            }
        });

        btnCheckbox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                NoteItem noteItem = notes.get(currentIndex);
                if (focusedCheckbox != null) {
                    if (focusedCheckbox.getVisibility() == View.INVISIBLE) {
                        noteItem.setChecked(true);
                        focusedCheckbox.setVisibility(View.VISIBLE);
                    }
                    else {
                        noteItem.setChecked(false);
                        focusedCheckbox.setVisibility(View.INVISIBLE);
                    }
                }

            }
        });

        return new NotesAdapter.NotesViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final NotesAdapter.NotesViewHolder holder, final int position)
    {
        this.currentIndex = position;
        NoteItem noteItem = notes.get(position);
        final EditText currentEditText = holder.editText;
        final CheckBox currentCheckbox = holder.checkbox;
        currentEditText.setText(noteItem.getText());
        currentEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus) {
                    focusedEditText = currentEditText;
                    focusedCheckbox = currentCheckbox;
                }
            }
        });

        currentEditText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                //on enter pressed
                if ( (actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN ))) {
                    listener.OnEnterKeyClicked(currentEditText, position);
                    return true;
                }

                //on backspace pressed
                if(event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    if(currentEditText.getText().equals("") && notes.size() > 1) {
                        notes.remove(position);
                        return true;
                    }
                }

                return false;
            }
        });



//        currentEditText.addTextChangedListener(new TextWatcher()
//        {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count)
//            {
//                if (s.subSequence(start, start + count).toString().equals("\n")) {
//                    currentEditText.setText(currentEditText.getText().delete(start, start + 1));
//                    listener.OnEnterKeyClicked(currentEditText, position);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) { }
//        });

        currentEditText.setFocusableInTouchMode(true);
        currentEditText.requestFocus();

        if (!noteItem.isChecked()) {
            currentCheckbox.setVisibility(View.INVISIBLE);
        }
        if (noteItem.isBold() && noteItem.isItalic())
            currentEditText.setTypeface(null, Typeface.BOLD_ITALIC);
        else if (noteItem.isBold())
            currentEditText.setTypeface(null, Typeface.BOLD);
        else if (noteItem.isItalic())
            currentEditText.setTypeface(null, Typeface.ITALIC);
    }


    @Override
    public int getItemCount() {
        return notes.size();
    }
}
