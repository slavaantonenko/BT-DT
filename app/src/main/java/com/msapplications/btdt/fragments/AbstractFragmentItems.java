package com.msapplications.btdt.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.objects.itemTypes.ItemInCategory;

import java.util.ArrayList;

/*
Currently used only for notes.
 */
public abstract class AbstractFragmentItems extends Fragment
{
    int categoryID=-1;
    ArrayList<ItemInCategory> itemsInCat;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        categoryID = getArguments().getInt(CommonValues.CATEGORY_ID_EXTRA);
    }
}
