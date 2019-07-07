package com.msapplications.btdt.fragments;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.R;
import com.msapplications.btdt.Utils;
import com.msapplications.btdt.objects.itemTypes.recipes.Recipe;
import com.msapplications.btdt.room_storage.category.CategoryViewModel;
import com.msapplications.btdt.room_storage.recipe.RecipeViewModel;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeFragment extends Fragment implements View.OnClickListener {
    private String title = "";
    private int recipeID;
    private RecipeViewModel recipeViewModel;
    private CategoryViewModel categoryViewModel;
    private static int GET_FROM_GALLERY = 4;
    private ImageView recipeImage;
    private ImageView addRecipePreview;

//    private OnFragmentInteractionListener onFragmentInteractionListener;

    public RecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeFragment newInstance(String title, int id) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putString(CommonValues.FRAGMENT_TITLE, title);
        args.putInt(CommonValues.RECIPE_ID_EXTRA, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(CommonValues.FRAGMENT_TITLE);
            recipeID = getArguments().getInt(CommonValues.RECIPE_ID_EXTRA);
        }

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        recipeImage = view.findViewById(R.id.recipe_preview);
        addRecipePreview = view.findViewById(R.id.add_recipe_preview);
        addRecipePreview.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        int recipesCategoryId = categoryViewModel.getIdByName(CommonValues.RECIPES);

        Recipe recipe = recipeViewModel.getRecipe(recipeID);
        recipeImage.setImageBitmap(Utils.getRecipeImage(recipe.getImageURi(), getActivity()));

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.ingredients_frame, IngredientsFragment.newInstance(recipeID),
                        CommonValues.RECIPE_INGREDIENTS).commit();

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.method_frame, NotesFragment.newInstance(recipesCategoryId, recipeID),
                        CommonValues.RECIPE_FRAGMENT).commit();
    }

    @Override
    public void onClick(View view) {
        startActivityForResult(new Intent(Intent.ACTION_OPEN_DOCUMENT,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                recipeImage.setImageBitmap(bitmap);
                recipeViewModel.setImage(recipeID, selectedImage.toString());
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
