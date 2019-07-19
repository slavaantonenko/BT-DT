package com.msapplications.btdt.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.colorpicker.ColorPickerPalette;
import com.android.colorpicker.ColorPickerSwatch;
import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.R;
import com.msapplications.btdt.Utils;
import com.msapplications.btdt.objects.Category;
import com.msapplications.btdt.room_storage.category.CategoryViewModel;

//import com.android.colorpicker.ColorPickerDialog;
//import com.android.colorpicker.ColorPickerPalette;
//import com.android.colorpicker.ColorPickerSwatch;

public class ChooseColorDialogFragment extends DialogFragment
{
    private CategoryViewModel categoryViewModel;
    private Category category;
    private int colorPickerPaletteWidth;

    public ChooseColorDialogFragment() {
        // Required empty public constructor
    }

    public static ChooseColorDialogFragment newInstance(Category category)
    {
        ChooseColorDialogFragment fragment = new ChooseColorDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(CommonValues.CATEGORY_BUNDLE, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            category = getArguments().getParcelable(CommonValues.CATEGORY_BUNDLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_select_color, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        if (category == null)
            return;

        initializeDialog(view);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = colorPickerPaletteWidth + Utils.dpToPx(getResources(), 80);
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    private void initializeDialog(View view)
    {
        ColorPickerPalette colorPickerPalette = view.findViewById(R.id.colorPickerPalette);

        int[] categoryBackgroundColors = getResources().getIntArray(R.array.categories_background);
        colorPickerPalette.init(categoryBackgroundColors.length, 5, new ColorPickerSwatch.OnColorSelectedListener()
        {
            @Override
            public void onColorSelected(int color) {
                categoryViewModel.updateColor(color, category.getId());
                dismiss();
            }
        });

        colorPickerPalette.drawPalette(categoryBackgroundColors, category.getBackgroundColor());
        colorPickerPalette.measure(0,0);
        colorPickerPaletteWidth = colorPickerPalette.getMeasuredWidth();
    }
}
