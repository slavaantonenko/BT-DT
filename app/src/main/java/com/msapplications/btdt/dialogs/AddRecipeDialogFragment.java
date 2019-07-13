package com.msapplications.btdt.dialogs;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.R;
import com.msapplications.btdt.Utils;
import com.msapplications.btdt.objects.itemTypes.NoteItem;
import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;
import com.msapplications.btdt.objects.itemTypes.recipes.Recipe;
import com.msapplications.btdt.objects.itemTypes.recipes.RecipeIngredient;
import com.msapplications.btdt.room_storage.category.CategoryViewModel;
import com.msapplications.btdt.room_storage.ingredient.IngredientViewModel;
import com.msapplications.btdt.room_storage.note.NoteItemViewModel;
import com.msapplications.btdt.room_storage.recipe.RecipeViewModel;

import java.util.OptionalDouble;

import static android.app.Activity.RESULT_OK;

public class AddRecipeDialogFragment extends DialogFragment
{
    private RecipeViewModel recipeViewModel = null;
    private IngredientViewModel ingredientViewModel = null;
    private NoteItemViewModel noteItemViewModel = null;
    private CategoryViewModel categoryViewModel = null;

    public AddRecipeDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.dialog_add_recipe, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        ingredientViewModel = ViewModelProviders.of(this).get(IngredientViewModel.class);
        noteItemViewModel = ViewModelProviders.of(this).get(NoteItemViewModel.class);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        initializeDialog(view);

//        cinemaViewModel = ViewModelProviders.of(this).get(CinemaViewModel.class);
//        cinemaViewModel.getCinemas().observe(this, new Observer<List<Cinema>>()
//        {
//            @Override
//            public void onChanged(@Nullable List<Cinema> cinemas)
//            {
//                cinemaList = cinemas;
//            }
//        });
    }

    private void initializeDialog(View view)
    {
        final Button btnSaveRecipe = view.findViewById(R.id.btnSaveRecipe);
        final EditText etRecipeName = view.findViewById(R.id.etNewRecipeName);

        btnSaveRecipe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String recipeName = etRecipeName.getText().toString();

                if (recipeName.isEmpty()) {
                    etRecipeName.setError(getString(R.string.name_empty_error));
                    return;
                }

                getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, new Intent());
                recipeViewModel.insert(new Recipe(0, recipeName));
                int recipeID = recipeViewModel.getRecipeIdByName(recipeName);
                int recipeCategoryID = categoryViewModel.getIdByName(CommonValues.RECIPES);

                RecipeIngredient newEmptyItem = new RecipeIngredient(0,recipeID, 0);
                newEmptyItem.setText("");
                ingredientViewModel.insert(newEmptyItem);

                NoteItem newMethodline = new NoteItem(0,recipeCategoryID, recipeID, 0);
                newMethodline.setText(" ");
                noteItemViewModel.insert(newMethodline);

                dismiss();
            }
        });
    }
}
