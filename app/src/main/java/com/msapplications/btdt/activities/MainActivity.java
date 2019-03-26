package com.msapplications.btdt.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.MenuItem;
import android.widget.TextView;

import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.adapters.CategoriesAdapter;
import com.msapplications.btdt.dialogs.ChooseColorDialogFragment;
import com.msapplications.btdt.dialogs.NewCategoryDialogFragment;
import com.msapplications.btdt.dialogs.RenameCategoryDialogFragment;
import com.msapplications.btdt.interfaces.OnCategoryClickListener;
import com.msapplications.btdt.interfaces.OnCategoryMenuClickListener;
import com.msapplications.btdt.interfaces.OnMenuItemClickListener;
import com.msapplications.btdt.R;
import com.msapplications.btdt.Utils;
import com.msapplications.btdt.interfaces.OnFloatingActionClick;
import com.msapplications.btdt.objects.Category;
import com.msapplications.btdt.objects.CategoryType;
import com.msapplications.btdt.room_storage.category.CategoryViewModel;
import com.msapplications.btdt.room_storage.cinema.CinemaViewModel;

import java.util.List;

/*
Main activity, shows all categories of the user
 */
public class MainActivity extends AppCompatActivity implements OnFloatingActionClick, OnCategoryClickListener,
        OnCategoryMenuClickListener, OnMenuItemClickListener, RenameCategoryDialogFragment.OnRenameListener
{
    private RecyclerView recyclerView;
    private CategoriesAdapter adapter;
    private CategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        AppCompatTextView title = findViewById(R.id.app_title);
        recyclerView = findViewById(R.id.recycler_view);

        setSupportActionBar(toolbar);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ARLRDBD.ttf");
        title.setTypeface(font);

        initRecyclerView();

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class); //room
        //observer for categories list
        categoryViewModel.getCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable final List<Category> categories)
            {
                int position = adapter.adapterPosition();

                if (position > -1)
                    adapter.setCategory(categoryViewModel.getCategory(adapter.getItem(position).getId()));
                else
                    // Update the cached copy of the categories in the adapter.
                    adapter.setCategories(categories);
            }
        });

        if (firstUse()) {
            Utils.newCategory(this,this, CommonValues.CINEMA_SEATS, CategoryType.CINEMA_SEATS);
            Utils.newCategory(this,this, CommonValues.TRAVEL, CategoryType.TRAVEL);
            Utils.newCategory(this,this, CommonValues.RECIPES, CategoryType.RECIPES);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(onFabClick()); //add new category button
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
            return true;

        return super.onOptionsItemSelected(item);
    }

    /*
    create new category
     */
    public View.OnClickListener onFabClick()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                NewCategoryDialogFragment dialogFragment = new NewCategoryDialogFragment();
                dialogFragment.show(ft, CommonValues.NEW_CATEGORY_DIALOG_FRAGMENT_TAG);
            }
        };
    }

    @Override
    public void onCategoryClick(View view)
    {
        TextView name = view.findViewById(R.id.tvCategoryTitle);

        if (name.getText().equals(CommonValues.TRAVEL))
//            startActivity(new Intent(this, TravelCountryActivity.class));
            startActivity(new Intent(this, TravelActivity.class));
        else
        {
            //open category list
            Intent intent = new Intent(this, ListActivity.class);
            intent.putExtra(CommonValues.CATEGORY_ID_EXTRA, categoryViewModel.getIdByName(name.getText().toString()));
            intent.putExtra(CommonValues.CATEGORY_NAME_EXTRA, name.getText().toString());
            startActivityForResult(intent, 0);
        }
    }

    @Override
    public void onCategoryMenuClick(View view, int position) {
        showPopupMenu(view, position);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem, int position)
    {
        Category category = adapter.getItem(position);

        switch (menuItem.getItemId())
        {
            case R.id.action_rename:
                Utils.renameCategory(getSupportFragmentManager(), category);
                return true;

            case R.id.action_choose_color:
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                ChooseColorDialogFragment dialogFragment = new ChooseColorDialogFragment().newInstance(category);
                dialogFragment.show(ft, CommonValues.CHOOSE_COLOR_DIALOG_FRAGMENT_TAG);
                return true;

            case R.id.action_delete:
                if (category.getType().equals(CategoryType.CINEMA_SEATS)) {
                    CinemaViewModel cinemaViewModel = ViewModelProviders.of(this).get(CinemaViewModel.class);
                    Utils.deleteCategory(cinemaViewModel, 0);
                }

                Utils.deleteCategory(categoryViewModel, category.getId());

                return true;
            default:
        }

        return false;
    }

    @Override
    public void onRename(String title) {}

    private void initRecyclerView()
    {
        adapter = new CategoriesAdapter(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    /*
    use when app is opened for the first time
     */
    private boolean firstUse()
    {
        boolean firstUse = Utils.getBooleanFromCache(this, CommonValues.FIRST_USE, true);

        if (firstUse)
            Utils.saveBooleanToCache(this, CommonValues.FIRST_USE, false);

        return firstUse;
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view, int position)
    {
        // inflate menu
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.category_menu, popup.getMenu());
        popup.setOnMenuItemClickListener((CategoriesAdapter.ViewHolder)recyclerView.findViewHolderForAdapterPosition(position));
        popup.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (!(resultCode == CommonValues.RENAME_CATEGORY_RESULT_CODE))
            adapter.resetPosition();
    }
}
