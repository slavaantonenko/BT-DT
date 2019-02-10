package com.msapplications.btdt.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.msapplications.btdt.fragments.NotesFragment;
import com.msapplications.btdt.lists.CategoryList;
import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.fragments.CinemaSeatsFragment;
import com.msapplications.btdt.fragments.CollectionFragment;
import com.msapplications.btdt.R;
import com.msapplications.btdt.objects.Category;
import com.msapplications.btdt.objects.CategoryType;

public class ListActivity extends AppCompatActivity
        implements CollectionFragment.OnFragmentInteractionListener

{
    private int categoryIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        categoryIndex = intent.getExtras().getInt(CommonValues.CATEGORY_INDEX);

        if (categoryIndex > -1)
            configureFragment();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // Do different stuff
    }

    private void configureFragment()
    {
        Category category = CategoryList.getCategoryByIndex(categoryIndex);
        if (category == null)
            return;

        CategoryType type = category.getType();

        switch (type)
        {
            case NOTES:
                openFragment(new NotesFragment(), CommonValues.NOTES_FRAGMENT, categoryIndex);
                break;
            case COLLECTION:
                openFragment(new CollectionFragment(), CommonValues.COLLECTION_FRAGMENT, categoryIndex);
                break;
            case CINEMA_SEATS:
                openFragment(new CinemaSeatsFragment(), CommonValues.CINEMA_SEATS_FRAGMENT, categoryIndex);
            case TRAVEL:

        }
    }

    private void openFragment(Fragment fragment, String tag, int index)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(CommonValues.CATEGORY_INDEX, index);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.listActivityContent, fragment, tag)
            .commit();
    }
}