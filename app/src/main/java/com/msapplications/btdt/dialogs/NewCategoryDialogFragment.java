package com.msapplications.btdt.dialogs;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.CreateCategoryDialog;
import com.msapplications.btdt.R;
import com.msapplications.btdt.lists.CategoryList;
import com.msapplications.btdt.objects.Category;
import com.msapplications.btdt.objects.CategoryType;
import com.msapplications.btdt.objects.itemTypes.NoteItem;

import java.util.ArrayList;
import java.util.Random;

public class NewCategoryDialogFragment extends DialogFragment
{
    public NewCategoryDialogFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckListFragment.
     */
    public static NewCategoryDialogFragment newInstance(String param1, String param2)
    {
        NewCategoryDialogFragment fragment = new NewCategoryDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_new_category, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initializeDialog(view);
    }

    private void initializeDialog(final View view)
    {
        Button btnSaveCategory = view.findViewById(R.id.btnSaveCategory);

        btnSaveCategory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText etNewCategoryName = view.findViewById(R.id.etNewCategoryName);
                String newName = etNewCategoryName.getText().toString();

                if (newName.equals("")) {
                    etNewCategoryName.setError("Name can't be empty");
                    return;
                }

                if (CategoryList.categoryNameExists(newName)) {
                    etNewCategoryName.setError("Category name already exists");
                    return;
                }

                CategoryType type = getSelectedType(((Spinner)view.findViewById(R.id.choose_category_type)).getSelectedItem().toString());

                if (type.equals(CategoryType.CINEMA_SEATS))
                {
                    if (CategoryList.categoryTypeExists(type)) {
                        showAlertDialog(CommonValues.CINEMA_SEATS, getString(R.string.cinema_seats_exist));
                        dismiss();
                        return;
                    }

                    showAlertDialog(CommonValues.CINEMA_SEATS, getString(R.string.cinema_seats_warning));
                }

                //TODO remove this when images will be implemented.
                // new Random().nextInt((max - min) + 1) + min;
                int[] categoryBackground = getResources().getIntArray(R.array.categories_background);
                Random random = new Random();
                int randomNum = random.nextInt(10);

                Category newCategory = new Category(newName, new ArrayList<NoteItem>(), type, categoryBackground[randomNum]); //TODO image
                CategoryList.addCategory(getActivity(), newCategory);

//                adapter.notifyDataSetChanged();
                dismiss();
            }
        });
    }

    private CategoryType getSelectedType(String typeName)
    {
        switch (typeName)
        {
            case (CommonValues.NOTE):
                return CategoryType.NOTES;
            case (CommonValues.COLLECTION):
                return CategoryType.COLLECTION;
            case (CommonValues.CINEMA_SEATS):
                return CategoryType.CINEMA_SEATS;
            case (CommonValues.TRAVEL):
                return CategoryType.TRAVEL;
        }

        return null;
    }

    /**
     * This method creates a custom AlertDialog and sets dynamically title and text.
     * @param title the title which will be shown.
     * @param msg the text which will be shown.
     */
    private void showAlertDialog(String title, String msg)
    {
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_alert_dialog, null);

        final CreateCategoryDialog categoryCreateAlertDialog = new CreateCategoryDialog(
                new AlertDialog.Builder(getActivity()).setCancelable(true), dialogView);

        ((TextView) dialogView.findViewById(R.id.tvAlertDialogTitle)).setText(title);
        ((TextView) dialogView.findViewById(R.id.tvAlertDialogMessage)).setText(msg);
        dialogView.findViewById(R.id.btnAlertDialogNeutral).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCreateAlertDialog.dismiss();
            }
        });
    }
}
