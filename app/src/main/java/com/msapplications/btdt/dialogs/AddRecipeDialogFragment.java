package com.msapplications.btdt.dialogs;

import android.content.Intent;
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
import com.msapplications.btdt.Utils;
import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;

import static android.app.Activity.RESULT_OK;

public class AddRecipeDialogFragment extends DialogFragment
{
    public AddRecipeDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.dialog_add_cinema, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initializeDialog(view);

//        cinemaViewModel = ViewModelProviders.of(this).get(CinemaViewModel.class);
//        cinemaViewModel.getCinemas().observe(this, new Observer<List<Cinema>>()
//        {
//            @Override
//            public void onChanged(@Nullable List<Cinema> cinemas)
//            {
//                cinemaList = cinemas;
//            }
//        });
    }

    private void initializeDialog(View view)
    {
        final Button btnSaveCinema = view.findViewById(R.id.btnSaveCinema);
        final EditText etRecipeName = view.findViewById(R.id.etNewRecipeName);

        btnSaveCinema.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String recipeName = etRecipeName.getText().toString();

                if (recipeName.isEmpty()) {
                    etRecipeName.setError(getString(R.string.name_empty_error));
                    return;
                }

                int color = Utils.randomColor(getContext());

//                getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, new Intent());
//                cinemaViewModel.insert(new Cinema(0, cinemaName, cinemaCity.toString(), logoID));
                dismiss();
            }
        });
    }
}
