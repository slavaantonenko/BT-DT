package com.msapplications.btdt.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.Utils;
import com.msapplications.btdt.interfaces.OnCategoryClickListener;
import com.msapplications.btdt.interfaces.OnCategoryMenuClickListener;
import com.msapplications.btdt.interfaces.OnMenuItemClickListener;
import com.msapplications.btdt.R;
import com.msapplications.btdt.objects.Category;
import com.msapplications.btdt.objects.CategoryType;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import cn.iwgang.countdownview.CountdownView;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>
{
    private Context context;
    private List<Category> categoriesList;
    private OnCategoryClickListener categoryClickListener;
    private OnCategoryMenuClickListener categoryMenuClickListener;
    private OnMenuItemClickListener menuItemClickListener;
    private int adapterPosition = -1;
    private int viewPositionAtCreation = -1;

    public CategoriesAdapter(Context context)
    {
        this.context = context;
        categoryClickListener = (OnCategoryClickListener) context;
        categoryMenuClickListener = (OnCategoryMenuClickListener) context;
        menuItemClickListener = (OnMenuItemClickListener) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener,
            View.OnClickListener
    {
        public TextView title, tvNewFeatureTitle;
        public ImageView overflow;
        public CardView cardView;
        public CountdownView mCvCountdownView; //for future features

        public ViewHolder(View view)
        {
            super(view);
            if (!CategoryType.TRAVEL.equals(getItem(viewPositionAtCreation).getType()))
            {
                title = view.findViewById(R.id.tvCategoryTitle);
                overflow = view.findViewById(R.id.overflow);
                cardView = view.findViewById(R.id.cvCategory);
                cardView.setOnClickListener(this);
                overflow.setOnClickListener(this);

                Random random = new Random();

                //randomize height for a category card
                int height = random.nextInt((CommonValues.CATEGORY_CARD_SIZE_MAX - CommonValues.CATEGORY_CARD_SIZE_MIN) + 1)
                        + CommonValues.CATEGORY_CARD_SIZE_MIN; // new Random().nextInt((max - min) + 1) + min;

                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) cardView.getLayoutParams();
                layoutParams.height = Utils.dpToPx(context.getResources(), height);
                cardView.setLayoutParams(layoutParams);
            }
            else {
                tvNewFeatureTitle = view.findViewById(R.id.tvNewFeatureTitle);
                mCvCountdownView = view.findViewById(R.id.countDownNewFeature);
            }
        }

        public void onBindViewHolder(Category category) {
            if (!getItem(getAdapterPosition()).getType().equals(CategoryType.TRAVEL)) {
                title.setText(category.getName());
                cardView.setCardBackgroundColor(category.getBackgroundColor());
            }
            else
            {   //future feature
                tvNewFeatureTitle.setText(CommonValues.TRAVEL);

                try
                {
                    Date currentDate = Calendar.getInstance().getTime();
                    String date = "01/04/2019";
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date finalDate = formatter.parse(date);
                    long diff = finalDate.getTime() - currentDate.getTime();
                    mCvCountdownView.start(diff);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            viewPositionAtCreation = -1;
        }

        @Override
        public void onClick(View view)
        {
            if (getItem(getAdapterPosition()).getType().equals(CategoryType.TRAVEL)) //TODO remove when Travel will be finished
                return;

            switch (view.getId())
            {
                case (R.id.cvCategory):
                    if (categoryClickListener == null)
                        return;

                    adapterPosition = getAdapterPosition();
                    categoryClickListener.onCategoryClick(view);
                    break;
                case (R.id.overflow):
                    if (categoryMenuClickListener == null)
                        return;

                    categoryMenuClickListener.onCategoryMenuClick(view, getAdapterPosition());
                    break;
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (getItem(getAdapterPosition()).getType().equals(CategoryType.TRAVEL)) //TODO remove when Travel will be finished
                return false;

            if (menuItem.getItemId() == R.id.action_rename)
                adapterPosition = getAdapterPosition();

            menuItemClickListener.onMenuItemClick(menuItem, getAdapterPosition());
            return false;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layout;

        if (CategoryType.TRAVEL.equals(getItem(viewType).getType()))
            layout = R.layout.new_feature_countdown_card;
        else
            layout = R.layout.category_card;

        viewPositionAtCreation = viewType;

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.onBindViewHolder(categoriesList.get(position));
    }

    @Override
    public int getItemCount()
    {
        if (categoriesList == null)
            return 0;

        return categoriesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public Category getItem(int position) {
        return categoriesList.get(position);
    }

    public void setCategories(List<Category> categories) {
        categoriesList = categories;
        notifyDataSetChanged();
    }

    public void setCategory(Category category)
    {
        categoriesList.set(adapterPosition, category);
        notifyItemChanged(adapterPosition);
        adapterPosition = -1;
    }

    public int adapterPosition() {
        return adapterPosition;
    }

    public void resetPosition() {
        adapterPosition = -1;
    }
}