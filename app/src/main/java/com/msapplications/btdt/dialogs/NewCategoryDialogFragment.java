package com.msapplications.btdt.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.CreateCategoryDialog;
import com.msapplications.btdt.R;
import com.msapplications.btdt.Utils;
import com.msapplications.btdt.objects.CategoryType;
import com.msapplications.btdt.room_storage.category.CategoryViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class NewCategoryDialogFragment extends DialogFragment
{
    private CategoryViewModel categoryViewModel;

    public NewCategoryDialogFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param categoryCode category code.
     * @return A new instance of fragment CheckListFragment.
     */
    public static NewCategoryDialogFragment newInstance()
    {
        NewCategoryDialogFragment fragment = new NewCategoryDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        initializeDialog(view);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    private void initializeDialog(final View view)
    {
        final EditText etNewCategoryName = view.findViewById(R.id.etNewCategoryName);
        final Spinner spCategoryType = view.findViewById(R.id.spCategoryType);
        final Button btnSaveCategory = view.findViewById(R.id.btnSaveCategory);

        spCategoryType.setAdapter(getSpinnerEntries(view));

        spCategoryType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = spCategoryType.getSelectedItem().toString();
                switch (selectedItem)
                {
                    case (CommonValues.CINEMA_SEATS):
                        etNewCategoryName.setText(CommonValues.CINEMA_SEATS);
                        etNewCategoryName.setEnabled(false);
                        break;
                    case (CommonValues.TRAVEL):
                        etNewCategoryName.setText(CommonValues.TRAVEL);
                        etNewCategoryName.setEnabled(false);
                        break;
                    case (CommonValues.RECIPES):
                        etNewCategoryName.setText(CommonValues.RECIPES);
                        etNewCategoryName.setEnabled(false);
                    default:
                        etNewCategoryName.setText("");
                        etNewCategoryName.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnSaveCategory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String newName = etNewCategoryName.getText().toString();

                if (newName.isEmpty()) {
                    etNewCategoryName.setError(getString(R.string.name_empty_error));
                    return;
                }

                if (categoryViewModel.categoryNameExists(newName) > 0) {
                    etNewCategoryName.setError(getString(R.string.category_exist_error));
                    return;
                }

                String typeName = ((Spinner)view.findViewById(R.id.spCategoryType)).getSelectedItem().toString();
                CategoryType type = getSelectedType(typeName);

                if (type.equals(CategoryType.CINEMA_SEATS))
                {
                    if (categoryViewModel.categoryTypeExists(type.getCode()) > 0) {
                        showAlertDialog(CommonValues.CINEMA_SEATS, getString(R.string.cinema_seats_exist_error));
                        dismiss();
                        return;
                    }

                    showAlertDialog(CommonValues.CINEMA_SEATS, getString(R.string.cinema_seats_warning));
                }

                Utils.newCategory(getActivity(),getContext(), newName, type);
                dismiss();
            }
        });
    }

    private ArrayAdapter<CharSequence> getSpinnerEntries(View view)
    {
        String[] categories = getResources().getStringArray(R.array.category_types);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, new ArrayList(Arrays.asList(categories)));

        for (int i = 0; i < adapter.getCount(); i++)
        {
            CharSequence category = adapter.getItem(i);
            CategoryType categoryType = getSelectedType(category.toString());
            if (categoryType != CategoryType.NOTES) {
                if (categoryViewModel.categoryTypeExists(getSelectedType(category.toString()).getCode())  > 0) {
                    adapter.remove(category);
                    i--;
                }
            }
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private CategoryType getSelectedType(String typeName)
    {
        switch (typeName)
        {
            case (CommonValues.NOTE):
                return CategoryType.NOTES;
            case (CommonValues.RECIPES):
                return CategoryType.RECIPES;
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
