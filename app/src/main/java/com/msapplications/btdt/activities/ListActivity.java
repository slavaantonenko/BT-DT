package com.msapplications.btdt.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.msapplications.btdt.Utils;
import com.msapplications.btdt.dialogs.RenameCategoryDialogFragment;
import com.msapplications.btdt.fragments.NotesFragment;
import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.fragments.CinemaSeatsFragment;
import com.msapplications.btdt.fragments.RecipesCollectionFragment;
import com.msapplications.btdt.R;
import com.msapplications.btdt.objects.CategoryType;
import com.msapplications.btdt.room_storage.category.CategoryViewModel;
import com.msapplications.btdt.room_storage.cinema.CinemaViewModel;

public class ListActivity extends AppCompatActivity
        implements RecipesCollectionFragment.OnFragmentInteractionListener, CinemaSeatsFragment.OnFragmentInteractionListener,
        NotesFragment.OnFragmentInteractionListener, RenameCategoryDialogFragment.OnRenameListener
{
    CategoryViewModel categoryViewModel;
    private int categoryTypeCode = -1;
    private int categoryID = -1;
    private String categoryName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        Intent intent = getIntent();
        categoryID = intent.getExtras().getInt(CommonValues.CATEGORY_ID_EXTRA);
        categoryName = intent.getExtras().getString(CommonValues.CATEGORY_NAME_EXTRA);
        categoryTypeCode = categoryViewModel.getType(categoryID);

        if (categoryTypeCode > -1)
            configureFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.category_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_choose_color);
        if (menuItem != null)
            menuItem.setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home: // Click on back button
                this.finish();
                return true;
            case R.id.action_rename:
                Utils.renameCategory(getSupportFragmentManager(), categoryViewModel.getCategory(categoryID));
                break;
            case R.id.action_delete:
                if (CategoryType.CINEMA_SEATS.equals(categoryTypeCode)) {
                    CinemaViewModel cinemaViewModel = ViewModelProviders.of(this).get(CinemaViewModel.class);
                    Utils.deleteCategory(cinemaViewModel, 0);
                }

                Utils.deleteCategory(categoryViewModel, categoryID);
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onRename(String title)
    {
        onFragmentInteraction(title);
        Intent intent = new Intent();
        setResult(CommonValues.RENAME_CATEGORY_RESULT_CODE, intent);
    }

    private void configureFragment()
    {
        switch (CategoryType.getType(categoryTypeCode))
        {
            case NOTES:
                openFragment(new NotesFragment().newInstance(categoryName, categoryID), CommonValues.NOTES_FRAGMENT);
                break;
            case RECIPES:
                openFragment(new RecipesCollectionFragment().newInstance(categoryName), CommonValues.RECIPES_FRAGMENT);
                break;
            case CINEMA_SEATS:
                openFragment(new CinemaSeatsFragment().newInstance(categoryName), CommonValues.CINEMA_SEATS_FRAGMENT);
        }
    }

    private void openFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.listActivityContent, fragment, tag).commit();
    }
}
