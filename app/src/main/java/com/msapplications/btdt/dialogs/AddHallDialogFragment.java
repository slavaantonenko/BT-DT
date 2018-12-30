package com.msapplications.btdt.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.msapplications.btdt.R;
import com.msapplications.btdt.lists.CinemasHallsList;
import com.msapplications.btdt.objects.itemTypes.cinema.CinemaHall;

public class AddHallDialogFragment extends DialogFragment
{
    private EditText etHallNumber;
    private EditText etRowNumber;
    private ImageButton ibLike;
    private ImageButton ibDislike;

    public AddHallDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_add_cinema_hall, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
    }

    private void initializeViews(View view)
    {
        etHallNumber = view.findViewById(R.id.etHallNumber);
        etRowNumber = view.findViewById(R.id.etRowNumber);
        ibLike = view.findViewById(R.id.ibLike);
        ibDislike = view.findViewById(R.id.ibDislike);

        ibLike.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addHall(true);
                dismiss();
            }
        });

        ibDislike.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addHall(false);
                dismiss();
            }
        });
    }

    private void addHall(boolean like)
    {
        CinemaHall cinemaHall = new CinemaHall(Integer.parseInt(etHallNumber.getText().toString()),
                Integer.parseInt(etRowNumber.getText().toString()), like);

        CinemasHallsList.addHall(getContext(), cinemaHall);
    }
}
