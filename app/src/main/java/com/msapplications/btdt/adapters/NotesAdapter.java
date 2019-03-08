package com.msapplications.btdt.adapters;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.msapplications.btdt.R;
import com.msapplications.btdt.interfaces.NotesEditor;
import com.msapplications.btdt.objects.itemTypes.NoteItem;
import com.msapplications.btdt.room_storage.note.NoteItemViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>
{
    ArrayList<NoteItem> notes;
    NotesEditor listener;
    NoteItemViewModel noteItemViewModel;
    int currentIndex=-1;
    boolean setSelectionFocus = true;

    ImageButton btnBold;
    ImageButton btnItalic;
    ImageButton btnCheckbox;


    public NotesAdapter(NotesEditor listener, NoteItemViewModel noteItemViewModel) {
        this.noteItemViewModel = noteItemViewModel;
        this.listener = listener;
    }

    public void setNoteItems(List<NoteItem> noteItems, int notifyFromIndex, boolean isRemoved) {
        if(noteItems == null)
            return;

        this.notes = new ArrayList<>(noteItems);
        Collections.sort(notes); //sort by line number
        if(notifyFromIndex == -1)
            notifyDataSetChanged(); //note just opened
        else if(isRemoved) { //a line is removed
            notifyItemRangeRemoved(notifyFromIndex, 1);
            if(notifyFromIndex != 0) {
                currentIndex = notifyFromIndex - 1;
                notifyItemChanged(notifyFromIndex - 1);
            }

        } else {
            notifyItemRangeChanged(notifyFromIndex, notes.size() - notifyFromIndex);
        }
    }


    public class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnKeyListener
    {
        public EditText editText;
        public CheckBox checkbox;

        public NotesViewHolder(View view)
        {
            super(view);
            editText = view.findViewById(R.id.etNote);
            checkbox = view.findViewById(R.id.checkbox);
            editText.setOnKeyListener(this);
        }

        public int getIndex() {
            return getAdapterPosition();
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event)
        {
            int position = getAdapterPosition();

            //line removed
            if(keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN  && position != 0 && editText.getText().toString().isEmpty()) {
                listener.onBackspaceClicked(notes.get(position).getId(), position);
            }
            return false;
        }
    }

    @Override
    public NotesAdapter.NotesViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);

        ConstraintLayout constraintLayoutParent = ((ConstraintLayout)parent.getParent());
        btnBold = constraintLayoutParent.findViewById(R.id.bold);
        btnItalic = constraintLayoutParent.findViewById(R.id.italic);
        btnCheckbox = constraintLayoutParent.findViewById(R.id.checkbox_btn);

        //line un/bold
        btnBold.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(parent.findFocus() == null) {
                    parent.getChildAt(0).findViewById(R.id.etNote).requestFocus();
                }

                String e = ((EditText) parent.findFocus()).getText().toString();
                NoteItem noteItem = notes.get(currentIndex);
                boolean newValue = !noteItem.isBold();
                noteItemViewModel.updateBold(noteItem.getId(), newValue);
                noteItem.setBold(newValue);
                noteItem.setText(e);
                setSelectionFocus = false;
                notifyItemChanged(currentIndex);
            }
        });

        //line un/italic
        btnItalic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(parent.findFocus() == null) {
                    parent.getChildAt(0).findViewById(R.id.etNote).requestFocus();
                }

                String e = ((EditText) parent.findFocus()).getText().toString();
                NoteItem noteItem = notes.get(currentIndex);
                boolean newValue = !noteItem.isItalic();
                noteItemViewModel.updateItalic(noteItem.getId(), newValue);
                noteItem.setItalic(newValue);
                noteItem.setText(e);
                setSelectionFocus = false;
                notifyItemChanged(currentIndex);

            }
        });

        //line checkbox un/shown
        btnCheckbox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(parent.findFocus() == null) {
                    parent.getChildAt(0).findViewById(R.id.etNote).requestFocus();
                }

                String e = ((EditText) parent.findFocus()).getText().toString();
                NoteItem noteItem = notes.get(currentIndex);
                boolean newValue = !noteItem.isCheckBox();
                noteItemViewModel.updateCheckBox(noteItem.getId(), newValue);
                noteItem.setCheckBox(newValue);
                noteItem.setText(e);
                setSelectionFocus = false;
                notifyItemChanged(currentIndex);
            }
        });

        return new NotesAdapter.NotesViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final NotesAdapter.NotesViewHolder holder, final int position)
    {
        //this.currentIndex = position;
        final NoteItem noteItem = notes.get(position);
        final EditText currentEditText = holder.editText;
        final CheckBox currentCheckbox = holder.checkbox;

        currentEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        currentEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);

       // if(noteItem.getText() != null && !noteItem.getText().equals(currentEditText.getText().toString()))
        currentEditText.setText(noteItem.getText());

        currentEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus) {
                    currentIndex = holder.getAdapterPosition();
                    currentEditText.setSelection(currentEditText.length());
                }
                else {
                    if(!currentEditText.getText().toString().equals(noteItem.getText())) {
                        listener.saveCurrentEdit(noteItem.getId(), currentEditText.getText().toString(), position);
                    }
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
                    listener.OnEnterKeyClicked(noteItem.getId(),position +1, currentEditText, noteItem.isCheckBox());
                    currentIndex = position +1;
                    return true;
                }

                return false;
            }
        });

        //checkbox un/checked
        currentCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                listener.ocCheckedClickListener(noteItem.getId(), isChecked);
                noteItem.setChecked(isChecked);
            }
        });

        //set focus for current line
        if(currentIndex == position) {
            currentEditText.setFocusableInTouchMode(true);
            currentEditText.requestFocus();
        }

        //adjust line appearance (text and checkbox)
        if (!noteItem.isCheckBox()) {
            currentCheckbox.setVisibility(View.INVISIBLE);
        } else {
            currentCheckbox.setChecked(noteItem.isChecked());
            currentCheckbox.setVisibility(View.VISIBLE);
        }
        if (noteItem.isBold() && noteItem.isItalic())
            currentEditText.setTypeface(null, Typeface.BOLD_ITALIC);
        else if (noteItem.isBold())
            currentEditText.setTypeface(null, Typeface.BOLD);
        else if (noteItem.isItalic())
            currentEditText.setTypeface(null, Typeface.ITALIC);
        else
            currentEditText.setTypeface(null, Typeface.NORMAL);

    }


    @Override
    public int getItemCount() {
        if(notes == null)
            return 0;

        return notes.size();
    }

}
