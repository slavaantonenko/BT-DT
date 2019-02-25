package com.msapplications.btdt.dialogs;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.R;
import com.msapplications.btdt.objects.itemTypes.cinema.CinemaHall;
import com.msapplications.btdt.room_storage.cinema.CinemaHallsViewModel;

import static android.app.Activity.RESULT_OK;

public class AddEditHallDialogFragment extends DialogFragment
{
    private CinemaHallsViewModel cinemaHallsViewModel = null;
    private TextView tvAddHallTitle;
    private EditText etHallNumber;
    private EditText etRowNumber;
    private ImageButton ibIcon1;
    private ImageButton ibIcon2;
    private CinemaHall cinemaHall;
    private String[] cinemaInfo = null;
    private boolean edit = false;

    public AddEditHallDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cinemaInfo index 0 contains the name and index 1 contains the city.
     * @return A new instance of fragment CheckListFragment.
     */
//    public static AddEditHallDialogFragment newInstance(String[] cinemaInfo, boolean edit, int id)
    public static AddEditHallDialogFragment newInstance(String[] cinemaInfo, CinemaHall cinemaHall, boolean edit)
    {
        AddEditHallDialogFragment fragment = new AddEditHallDialogFragment();
        Bundle args = new Bundle();
        args.putStringArray(CommonValues.CINEMA_INFO_BUNDLE, cinemaInfo);
        args.putBoolean(CommonValues.CINEMA_HALL_ACTION_BUNDLE, edit);
        args.putParcelable(CommonValues.CINEMA_HALL_BUNDLE, cinemaHall);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        {
            cinemaHall = getArguments().getParcelable(CommonValues.CINEMA_HALL_BUNDLE);
            cinemaInfo = getArguments().getStringArray(CommonValues.CINEMA_INFO_BUNDLE);
            edit = getArguments().getBoolean(CommonValues.CINEMA_HALL_ACTION_BUNDLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_cinema_hall, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        cinemaHallsViewModel = ViewModelProviders.of(this).get(CinemaHallsViewModel.class);
        initializeViews(view);
    }

    private void initializeViews(View view)
    {
        tvAddHallTitle = view.findViewById(R.id.tvAddHallTitle);
        etHallNumber = view.findViewById(R.id.etHallNumber);
        etRowNumber = view.findViewById(R.id.etRowNumber);
        ibIcon1 = view.findViewById(R.id.ibIcon1);
        ibIcon2 = view.findViewById(R.id.ibIcon2);

        if (edit)
        {
            tvAddHallTitle.setText(getString(R.string.dach_title_edit));
            etHallNumber.setText(cinemaHall.getHall());
            etRowNumber.setText(cinemaHall.getRow());
            ibIcon1.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_edit));
            ibIcon2.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_delete_black));
        }

        // Edit or Add liked hall.
        ibIcon1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (checkInput()) {
                    if (edit)
                        editHall();
                    else
                        addHall(true);

                    dismiss();
                }
            }
        });

        // Delete or Add not liked hall.
        ibIcon2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (checkInput()) {
                    if (edit)
                        deleteHall();
                    else
                        addHall(false);

                    dismiss();
                }
            }
        });
    }

    private boolean checkInput()
    {
        boolean valid = true;

        if (etHallNumber.getText().toString().isEmpty()) {
            etHallNumber.setError(getString(R.string.cinema_hall_empty_error));
            valid = false;
        }

        if (etRowNumber.getText().toString().equals("")) {
            etRowNumber.setError(getString(R.string.cinema_hall_row_empty_error));
            valid = false;
        }

        return valid;
    }

    private void addHall(boolean like)
    {
        if (cinemaInfo != null) {
            CinemaHall cinemaHall = new CinemaHall(0, etHallNumber.getText().toString(),
                    etRowNumber.getText().toString(), like, cinemaInfo[0], cinemaInfo[1]);

            cinemaHallsViewModel.insert(cinemaHall);
        }
    }

    private void editHall() {
        cinemaHall.setHall(etHallNumber.getText().toString());
        cinemaHall.setRow(etRowNumber.getText().toString());
        getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, new Intent());
        cinemaHallsViewModel.edit(cinemaHall);
    }

    private void deleteHall() {
        cinemaHallsViewModel.delete(cinemaHall);
    }
}
