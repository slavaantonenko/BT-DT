package com.msapplications.btdt.dialogs;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.R;
import com.msapplications.btdt.fragments.CinemaSeatsFragment;
import com.msapplications.btdt.objects.Category;
import com.msapplications.btdt.room_storage.category.CategoryViewModel;

public class RenameCategoryDialogFragment extends DialogFragment
{
    private CategoryViewModel categoryViewModel;
    private Category category;
    private OnRenameListener onRenameListener;

    public RenameCategoryDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param category category object.
     * @return A new instance of fragment CheckListFragment.
     */
    public static RenameCategoryDialogFragment newInstance(Category category)
    {
        RenameCategoryDialogFragment fragment = new RenameCategoryDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(CommonValues.CATEGORY_BUNDLE, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            category = getArguments().getParcelable(CommonValues.CATEGORY_BUNDLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_rename_category, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        initializeDialog(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onRenameListener = (OnRenameListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onRenameListener = null;
    }

    private void initializeDialog(final View view)
    {
        EditText oldName = view.findViewById(R.id.etRenameCategory);
        Button btnSaveRename = view.findViewById(R.id.btnSaveRename);

        oldName.setHint(category.getName());
        oldName.requestFocus();

        btnSaveRename.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText newName = view.findViewById(R.id.etRenameCategory);

                if (newName.getText().toString().isEmpty()) {
                    newName.setError(getString(R.string.name_empty_error));
                    return;
                }

                if (categoryViewModel.categoryNameExists(newName.getText().toString()) > 0) {
                    newName.setError(getString(R.string.category_exist_error));
                    return;
                }

                category.setName(newName.getText().toString());
                categoryViewModel.rename(category);
                onRenameListener.onRename(newName.getText().toString());
                dismiss();
            }
        });
    }

    public interface OnRenameListener {
        void onRename(String title);
    }
}