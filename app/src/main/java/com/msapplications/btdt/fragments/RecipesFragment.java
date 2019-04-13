package com.msapplications.btdt.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.R;
import com.msapplications.btdt.Utils;
import com.msapplications.btdt.adapters.RecipesAdapter;
import com.msapplications.btdt.dialogs.AddRecipeDialogFragment;
import com.msapplications.btdt.interfaces.OnMenuItemClickListener;
import com.msapplications.btdt.interfaces.OnObjectMenuClickListener;
import com.msapplications.btdt.objects.itemTypes.recipes.Recipe;
import com.msapplications.btdt.room_storage.recipe.RecipeViewModel;

import java.util.List;


public class RecipesFragment extends Fragment implements OnObjectMenuClickListener, OnMenuItemClickListener {

    private String title = "";
    private OnFragmentInteractionListener onFragmentInteractionListener;
    private RecyclerView recyclerViewRecipe;
    private RecipesAdapter adapterRecipe;
    private RecyclerView.LayoutManager layoutManagerRecipe;
    private RecipeViewModel recipeViewModel;
    private Fragment thisFragment = this;
    private boolean isRecipeAdded = false;

    public RecipesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipesFragment newInstance(String title) {
        RecipesFragment fragment = new RecipesFragment();
        Bundle args = new Bundle();
        args.putString(CommonValues.FRAGMENT_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            title = getArguments().getString(CommonValues.FRAGMENT_TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (onFragmentInteractionListener != null)
            onFragmentInteractionListener.onFragmentInteraction(title);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                if(isRecipeAdded) {
                    adapterRecipe.addRecipe(recipes.get(recipes.size() - 1));
                    isRecipeAdded = false;
                } else {
                    adapterRecipe.setRecipes(recipes);
                }
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fabRecipe);
        fab.setOnClickListener(onFabClick());
        initRecyclerView(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onFragmentInteractionListener = (RecipesFragment.OnFragmentInteractionListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed()
//    {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        onFragmentInteractionListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(CommonValues.ADD_RECIPE_REQUEST_CODE == requestCode) {

        }
    }

        private void initRecyclerView(View view)
    {
        recyclerViewRecipe = view.findViewById(R.id.rvRecipes);
        layoutManagerRecipe = new LinearLayoutManager(this.getContext());
        recyclerViewRecipe.setLayoutManager(layoutManagerRecipe);
        recyclerViewRecipe.setLayoutManager(new StaggeredGridLayoutManager(2, 1));

        adapterRecipe = new RecipesAdapter(this);
        recyclerViewRecipe.setAdapter(adapterRecipe);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem, int position) {
        Recipe recipe = adapterRecipe.getItem(position);

        switch (menuItem.getItemId())
        {
            case R.id.action_rename:
//                Utils.renameCategory(getSupportFragmentManager(), category);
                return true;

            case R.id.action_choose_color:
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction().addToBackStack(null);
//                ChooseColorDialogFragment dialogFragment = new ChooseColorDialogFragment().newInstance(category);
//                dialogFragment.show(ft, CommonValues.CHOOSE_COLOR_DIALOG_FRAGMENT_TAG);
                return true;

            case R.id.action_delete:
                Utils.deleteRecipe(recipeViewModel, recipe.getId());
                return true;
            default:
        }

        return false;    }

    @Override
    public void onObjectMenuClick(View view, int position) {
        PopupMenu popup = new PopupMenu(getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.category_menu, popup.getMenu());
        popup.setOnMenuItemClickListener((RecipesAdapter.ViewHolder)recyclerViewRecipe.findViewHolderForAdapterPosition(position));
        popup.show();
    }

    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(String title);
    }

    public View.OnClickListener onFabClick()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    FragmentTransaction ft = getFragmentManager().beginTransaction().addToBackStack(null);
                    AddRecipeDialogFragment dialogFragment = new AddRecipeDialogFragment();
                    dialogFragment.setTargetFragment(thisFragment, CommonValues.ADD_RECIPE_REQUEST_CODE);
                    dialogFragment.show(ft, CommonValues.ADD_RECIPE_DIALOG_FRAGMENT_TAG);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
