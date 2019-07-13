package com.msapplications.btdt.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.msapplications.btdt.R;
import com.msapplications.btdt.interfaces.NotesEditor;
import com.msapplications.btdt.objects.itemTypes.recipes.RecipeIngredient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>
{
    ArrayList<RecipeIngredient> notes;
    NotesEditor listener;
    int currentIndex=-1;
    private Context context;


    public IngredientsAdapter(Context context, NotesEditor listener) {
        this.listener = listener;
        this.context =  context;
    }

    public void setIngredients(List<RecipeIngredient> ingredients, int notifyFromIndex, boolean isRemoved) {
        if(ingredients == null)
            return;

        this.notes = new ArrayList<>(ingredients);
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


    public class IngredientsViewHolder extends RecyclerView.ViewHolder implements View.OnKeyListener
    {
        public EditText editText;

        public IngredientsViewHolder(View view)
        {
            super(view);
            editText = view.findViewById(R.id.etIngredient);
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
    public IngredientsViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        return new IngredientsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final IngredientsViewHolder holder, final int position)
    {
        //this.currentIndex = position;
        final RecipeIngredient ingredient = notes.get(position);
        final EditText currentEditText = holder.editText;

        currentEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        currentEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);

        currentEditText.setText(ingredient.getText());

//        currentEditText.setText(context.getString(R.string.bullet, ingredient.getText()));

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
                    if(!currentEditText.getText().toString().equals(ingredient.getText())) {
                        listener.saveCurrentEdit(ingredient.getId(), currentEditText.getText().toString(), position);
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
                    listener.OnEnterKeyClicked(ingredient.getId(),position +1, currentEditText, false);
                    currentIndex = position +1;
                    return true;
                }

                return false;
            }
        });


        //set focus for current line
        if(currentIndex == position) {
            currentEditText.setFocusableInTouchMode(true);
            currentEditText.requestFocus();
        }
    }


    @Override
    public int getItemCount() {
        if(notes == null)
            return 0;

        return notes.size();
    }

}
