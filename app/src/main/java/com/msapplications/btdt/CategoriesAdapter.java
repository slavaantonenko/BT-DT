package com.msapplications.btdt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.msapplications.btdt.objects.Category;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder>
{
    private Context mContext;
    private List<Category> categoryList;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_category_title);
            thumbnail = view.findViewById(R.id.thumbnail);
            overflow = view.findViewById(R.id.overflow);
        }
    }

    public CategoriesAdapter(Context mContext)
    {
        this.mContext = mContext;
        this.categoryList = CategoryList.getCategories(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {
        Category category = categoryList.get(position);
        holder.title.setText(category.getName());

        // loading album cover using Glide library
        Glide.with(mContext).load(category.getPreviewPic()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view)
    {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.category_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(view));
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener
    {
        View view;
        public MyMenuItemClickListener(View view)
        {
            this.view = view;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem)
        {
            final String categoryName = ((TextView) ((ConstraintLayout)view.getParent()).findViewById(R.id.tv_category_title)).getText().toString();

            switch (menuItem.getItemId())
            {
                case R.id.action_rename:
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    final View dialogView = inflater.inflate(R.layout.dialog_rename_category, null);
                    EditText oldName = dialogView.findViewById(R.id.etRenameCategory);
                    oldName.setHint(categoryName);
                    oldName.requestFocus();

                    final CreateCategoryDialog createCategoryDialog = new CreateCategoryDialog (
                            new AlertDialog.Builder(mContext).setCancelable(true), dialogView);

                    (dialogView.findViewById(R.id.btnSaveRename)).setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            String newName = ((EditText) dialogView.findViewById(R.id.etRenameCategory)).getText().toString();
                            if (newName.equals("")) {
                                ((EditText) dialogView.findViewById(R.id.etRenameCategory)).setError("Name can't be empty");
                                return;
                            }

                            if (CategoryList.categoryNameExists(newName)) {
                                ((EditText) dialogView.findViewById(R.id.etNewCategoryName)).setError("Category name already exists");
                                return;
                            }

//                            String categoryName = ((TextView) ((ConstraintLayout)view.getParent()).findViewById(R.id.tv_category_title)).getText().toString();
                            CategoryList.renameCategory(mContext, categoryName, newName);

                            createCategoryDialog.dismiss();
                            notifyDataSetChanged();

                            Toast.makeText(mContext, mContext.getString(R.string.category_renamed), Toast.LENGTH_SHORT).show();
                        }
                    });

                    return true;

                case R.id.action_delete:
//                    String categoryName = ((TextView) ((ConstraintLayout)view.getParent()).findViewById(R.id.tv_category_title)).getText().toString();
                    CategoryList.deleteCategory(mContext, categoryName);
                    //refresh adapter
                    notifyDataSetChanged();

                    Toast.makeText(mContext, mContext.getString(R.string.category_deleted), Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }

            return false;
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}