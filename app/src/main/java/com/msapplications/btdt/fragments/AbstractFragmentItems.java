package com.msapplications.btdt.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.msapplications.btdt.lists.CategoryList;
import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.objects.Category;
import com.msapplications.btdt.objects.itemTypes.ItemInCategory;

import java.util.ArrayList;

public abstract class AbstractFragmentItems extends Fragment
{
    int categoryIndex=0;
    Category category;
    ArrayList<ItemInCategory> itemsInCat;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        categoryIndex = getArguments().getInt(CommonValues.CATEGORY_INDEX);
        category = CategoryList.getCategoryByIndex(categoryIndex);

        if(category == null)
            return;

    }
}
