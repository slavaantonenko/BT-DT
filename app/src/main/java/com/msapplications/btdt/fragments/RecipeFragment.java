package com.msapplications.btdt.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.R;
import com.msapplications.btdt.room_storage.category.CategoryViewModel;
import com.msapplications.btdt.room_storage.recipe.RecipeViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeFragment extends Fragment {
    private String title = "";
    private int recipeID;
    private RecipeViewModel recipeViewModel;
    private CategoryViewModel categoryViewModel;

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
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        int recipesId = categoryViewModel.getIdByName(CommonValues.RECIPES);


        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.ingredients_frame, NotesFragment.newInstance("title", recipesId),
                        CommonValues.RECIPE_FRAGMENT)
                .addToBackStack("RecipesCollection").commit();

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.method_frame, NotesFragment.newInstance("title", recipesId),
                        CommonValues.RECIPE_FRAGMENT)
                .addToBackStack("RecipesCollection").commit();
    }
}
