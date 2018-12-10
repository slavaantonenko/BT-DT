package com.msapplications.btdt.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.msapplications.btdt.adapters.CategoriesAdapter;
import com.msapplications.btdt.CategoryList;
import com.msapplications.btdt.CreateCategoryDialog;
import com.msapplications.btdt.R;
import com.msapplications.btdt.Utils;
import com.msapplications.btdt.objects.Category;
import com.msapplications.btdt.objects.CategoryType;
import com.msapplications.btdt.objects.itemTypes.ItemInCategory;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private CategoriesAdapter adapter;
    private Activity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view);

        setSupportActionBar(toolbar);
        thisActivity = this;

        this.setTitle(R.string.am_title);
        Utils.centerTitle(this);
        adapter = new CategoriesAdapter(this);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final View dialogView = getLayoutInflater().inflate(R.layout.dialog_new_category, null);

                final CreateCategoryDialog createCategoryDialog = new CreateCategoryDialog(
                        new AlertDialog.Builder(thisActivity).setCancelable(true), dialogView);

                (dialogView.findViewById(R.id.btnNewSave)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        String newName = ((EditText) dialogView.findViewById(R.id.etNewCategoryName)).getText().toString();
                        if (newName.equals("")) {
                            ((EditText) dialogView.findViewById(R.id.etNewCategoryName)).setError("Name can't be empty");
                            return;
                        }

                        if (CategoryList.categoryNameExists(newName)) {
                            ((EditText) dialogView.findViewById(R.id.etNewCategoryName)).setError("Category name already exists");
                            return;
                        }

                        CategoryType type = getSelectedType(((Spinner)dialogView.findViewById(R.id.choose_category_type)).getSelectedItem().toString());

                        //TODO remove this when images will be implemented.
                        Random rand = new Random();
                        int resID = getResources().getIdentifier("album" + Integer.toString(rand.nextInt((4) + 1) + 4), "drawable", getPackageName());

//                        resID = R.drawable.food;
                        Category newCategory = new Category(newName, new ArrayList<ItemInCategory>(), type, resID); //TODO image
//                        Category newCategory = new Category(newName, new ArrayList<ItemInCategory>(), type, R.drawable.album4); //TODO image
                        CategoryList.addCategory(thisActivity, newCategory);

                        createCategoryDialog.dismiss();
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        });
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

    private void initRecyclerView()
    {

    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration
    {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge)
        {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
        {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge)
            {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) // top edge
                    outRect.top = spacing;

                outRect.bottom = spacing; // item bottom
            }
            else
            {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)

                if (position >= spanCount)
                    outRect.top = spacing; // item top
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp)
    {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    private CategoryType getSelectedType(String typeName)
    {
        switch (typeName)
        {
            case ("Checklist"):
                return CategoryType.CHECKLIST;
            case ("Note"):
                return CategoryType.NOTES;
            case ("Collection"):
                return CategoryType.COLLECTION;
        }

        return null;
    }
}
