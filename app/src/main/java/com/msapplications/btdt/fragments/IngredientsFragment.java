package com.msapplications.btdt.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.R;
import com.msapplications.btdt.adapters.IngredientsAdapter;
import com.msapplications.btdt.interfaces.NotesEditor;
import com.msapplications.btdt.objects.itemTypes.recipes.RecipeIngredient;
import com.msapplications.btdt.room_storage.ingredient.IngredientViewModel;

import java.util.ArrayList;
import java.util.List;

public class IngredientsFragment extends Fragment implements NotesEditor
{
    private Fragment thisFragment = this;
    private RecyclerView recyclerView;
    private IngredientsAdapter adapter;
    private IngredientViewModel ingredientViewModel;
    private boolean updateAdapter = true;
    private int notifyFromIndex = -1;
    private boolean isRemovedItems = false;
    private int recipeID = -1;

    public IngredientsFragment() {
    }

    public static IngredientsFragment newInstance(int recipeID)
    {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putInt(CommonValues.RECIPE_ID_EXTRA, recipeID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            recipeID = getArguments().getInt(CommonValues.RECIPE_ID_EXTRA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_ingredients, container, false);
        recyclerView = view.findViewById(R.id.rvIngredients);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        if(recipeID != -1) {
            ingredientViewModel = ViewModelProviders.of(this).get(IngredientViewModel.class);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(thisFragment.getContext());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new IngredientsAdapter(getContext(), this);
            recyclerView.setAdapter(adapter);
            RecipeIngredient recipeIngredient = new RecipeIngredient(0, recipeID, 0);
            recipeIngredient.setText("");
            List<RecipeIngredient> list = new ArrayList<>();
            list.add(recipeIngredient);
            adapter.setIngredients(list,0,false);

            ingredientViewModel.getRecipeIngredients(recipeID).observe(this, new Observer<List<RecipeIngredient>>() {
                @Override
                public void onChanged(@Nullable List<RecipeIngredient> recipeIngredients) {
                    if(updateAdapter) {
                        adapter.setIngredients(recipeIngredients, notifyFromIndex, isRemovedItems);
                        updateAdapter = false;
                        isRemovedItems = false;
                    }
                }
            });
        }
    }

    //add new line
    @Override
    public void OnEnterKeyClicked(int id, int newLineNumber, EditText editText, boolean isCheckBox)
    {
//        ingredients.get(position).setText(editText.getText().toString());
        ingredientViewModel.updateText(id, editText.getText().toString().substring(2));
        ingredientViewModel.increaseLineNumbers(newLineNumber, recipeID);
        RecipeIngredient newIngredient = new RecipeIngredient(0, recipeID, newLineNumber);
        newIngredient.setText("");
        ingredientViewModel.insert(newIngredient);
        updateAdapter = true;
        notifyFromIndex = newLineNumber -1;
        adapter.notifyItemChanged(newLineNumber);
    }

    //remove line
    @Override
    public void onBackspaceClicked(int ingredientItemID, int position) {
        notifyFromIndex = position;
        isRemovedItems = true;
        ingredientViewModel.delete(ingredientItemID);
        ingredientViewModel.decreaseLineNumbers(position, recipeID);
        updateAdapter = true;
    }

    @Override
    public void onCheckedClickListener(int noteItemId, boolean newValue) {
        //no checked option for ingredients
    }

    //current line is edited, save to db
    @Override
    public void saveCurrentEdit(int id, String newText, int adapterPosition){
        notifyFromIndex = adapterPosition;
        ingredientViewModel.updateText(id, newText);
        updateAdapter = true;

    }
}
