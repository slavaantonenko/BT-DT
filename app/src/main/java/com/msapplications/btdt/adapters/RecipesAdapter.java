package com.msapplications.btdt.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.msapplications.btdt.R;
import com.msapplications.btdt.interfaces.OnMenuItemClickListener;
import com.msapplications.btdt.interfaces.OnObjectMenuClickListener;
import com.msapplications.btdt.objects.itemTypes.recipes.Recipe;
import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {
    private List<Recipe> recipeList = new ArrayList<>();
    private OnObjectMenuClickListener recipeMenuClickListener;
    private OnMenuItemClickListener menuItemClickListener;
    private int adapterPosition = -1;

    public RecipesAdapter(Fragment fragment)
    {
        recipeMenuClickListener = (OnObjectMenuClickListener) fragment;
        menuItemClickListener = (OnMenuItemClickListener) fragment;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener, View.OnClickListener{
        public TextView recipeTitle;
        public ImageView preview, overflow;
        public Button btnColor;

        public ViewHolder(View view) {
            super(view);
            recipeTitle = view.findViewById(R.id.recipe_title);
            preview = view.findViewById(R.id.recipe_preview);
            btnColor = view.findViewById(R.id.btn_color_recipe);
            overflow = view.findViewById(R.id.overflow);
            overflow.setOnClickListener(this);
        }

        public void onBindViewHolder(Recipe recipe, final int position) {
            recipeTitle.setText(recipe.getName());
            btnColor.setBackgroundColor(recipe.getColor());
        }

        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case (R.id.recipe_card):
//                    if (categoryClickListener == null)
//                        return;
//
//                    adapterPosition = getAdapterPosition();
//                    categoryClickListener.onCategoryClick(view);
                    break;
                case (R.id.overflow):
                    if (recipeMenuClickListener == null)
                        return;

                    recipeMenuClickListener.onObjectMenuClick(view, getAdapterPosition());
                    break;
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (menuItem.getItemId() != R.id.action_delete)
                adapterPosition = getAdapterPosition();

            menuItemClickListener.onMenuItemClick(menuItem, getAdapterPosition());
            return false;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecipesAdapter.ViewHolder holder, final int position) {
        holder.onBindViewHolder(recipeList.get(position), position);
    }

    @Override
    public int getItemCount()
    {
        return recipeList.size();
    }

    public Recipe getItem(int position) {
        return recipeList.get(position);
    }

    public void setRecipes(List<Recipe> recipes) {
        recipeList = recipes;
        notifyDataSetChanged();
    }

    public void addRecipe(Recipe recipe) {
        recipeList.add(getItemCount(), recipe);
        notifyItemInserted(getItemCount() - 1);
    }

}
