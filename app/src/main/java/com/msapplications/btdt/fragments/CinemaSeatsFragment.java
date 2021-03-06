package com.msapplications.btdt.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.R;
import com.msapplications.btdt.adapters.CinemaHallsAdapter;
import com.msapplications.btdt.adapters.CinemasAdapter;
import com.msapplications.btdt.dialogs.AddCinemaDialogFragment;
import com.msapplications.btdt.dialogs.AddEditHallDialogFragment;
import com.msapplications.btdt.interfaces.OnCinemaClickListener;
import com.msapplications.btdt.interfaces.OnCinemaHallLongClickListener;
import com.msapplications.btdt.interfaces.OnCinemaOptionClickListener;
import com.msapplications.btdt.interfaces.OnFloatingActionClick;
import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;
import com.msapplications.btdt.objects.itemTypes.cinema.CinemaHall;
import com.msapplications.btdt.room_storage.cinema.CinemaHallsViewModel;
import com.msapplications.btdt.room_storage.cinema.CinemaViewModel;

import java.util.List;


public class CinemaSeatsFragment extends Fragment implements OnFloatingActionClick,
        OnCinemaClickListener, OnCinemaOptionClickListener, OnCinemaHallLongClickListener
{
    private String title = "";
    private int cinemaClickedPosition = 0;
    private Fragment thisFragment = this;
    private RecyclerView recyclerViewCinema;
    private RecyclerView recyclerViewCHLastClicked;
    private RecyclerView recyclerViewCinemaHalls;
    private CinemasAdapter adapterCinema;
    private CinemaHallsAdapter adapterCinemaHalls;
    private RecyclerView.LayoutManager layoutManagerCinema;
    private CinemaViewModel cinemaViewModel;
    private CinemaHallsViewModel cinemaHallsViewModel;
    private boolean isCinemaAdded = false;
    private boolean isCinemaDeleted = false;
    private boolean isCinemaHallEdited = false;
    private OnFragmentInteractionListener onFragmentInteractionListener;

    public CinemaSeatsFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static CinemaSeatsFragment newInstance(String title)
    {
        CinemaSeatsFragment fragment = new CinemaSeatsFragment();
        Bundle args = new Bundle();
        args.putString(CommonValues.FRAGMENT_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            title = getArguments().getString(CommonValues.FRAGMENT_TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (onFragmentInteractionListener != null)
            onFragmentInteractionListener.onFragmentInteraction(title);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cinema_seats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = view.findViewById(R.id.fabCinemaSeats);
        fab.setOnClickListener(onFabClick());
        initRecyclerView(view);

        cinemaViewModel = ViewModelProviders.of(this).get(CinemaViewModel.class);
        cinemaHallsViewModel = ViewModelProviders.of(this).get(CinemaHallsViewModel.class);

        cinemaViewModel.getCinemas().observe(this, new Observer<List<Cinema>>() {
            @Override
            public void onChanged(@Nullable final List<Cinema> cinemas) {
                // Update the cached copy of the words in the adapterCinema.

                if (isCinemaAdded) {
                    adapterCinema.addCinema(cinemas.get(cinemas.size() - 1));
                    isCinemaAdded = false;
                }
                else
                    adapterCinema.setCinemas(cinemas);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentInteractionListener = null;
    }

    public View.OnClickListener onFabClick()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    FragmentTransaction ft = getFragmentManager().beginTransaction().addToBackStack(null);
                    AddCinemaDialogFragment dialogFragment = new AddCinemaDialogFragment();
                    dialogFragment.setTargetFragment(thisFragment, CommonValues.ADD_CINEMA_REQUEST_CODE);
                    dialogFragment.show(ft, CommonValues.ADD_CINEMA_DIALOG_FRAGMENT_TAG);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public void onCinemaClick(View view, int position) {
        initRecyclerViewCinemaHalls(view, position);
    }

    @Override
    public void onCinemaOptionClick(View view, int position)
    {
        switch (view.getId())
        {
            case (R.id.ibAddSeat): // Add hall
                openCinemaHallDialogFragment(position, null, false);
                adapterCinema.notifyDataSetChanged();
                break;
            case (R.id.ibDeleteCinema): // Delete Cinema
                deleteCinema(position);
                break;
        }
    }

    @Override
    public void onCinemaLongClick(View view, int position) {
        openCinemaHallDialogFragment(cinemaClickedPosition, adapterCinemaHalls.getItem(position), true);
    }

    private void initRecyclerView(View view)
    {
        recyclerViewCinema = view.findViewById(R.id.rvCinemas);
        layoutManagerCinema = new LinearLayoutManager(thisFragment.getContext());
        recyclerViewCinema.setLayoutManager(layoutManagerCinema);
        
        recyclerViewCinema.addItemDecoration(new DividerItemDecoration(recyclerViewCinema.getContext(), DividerItemDecoration.VERTICAL));

        adapterCinema = new CinemasAdapter(thisFragment.getContext(), this, this);
        recyclerViewCinema.setAdapter(adapterCinema);
    }

    private void initRecyclerViewCinemaHalls(View view, int position)
    {
        recyclerViewCinemaHalls = view.findViewById(R.id.rvCinemaHalls);
        int visibilityStatus = recyclerViewCinemaHalls.getVisibility();

        if (visibilityStatus == View.GONE || (visibilityStatus == View.VISIBLE && position != cinemaClickedPosition))
        {
            recyclerViewCinemaHalls.setVisibility(View.VISIBLE);

            if (position != cinemaClickedPosition && recyclerViewCHLastClicked != null)
                recyclerViewCHLastClicked.setVisibility(View.GONE);

            RecyclerView.LayoutManager layoutManagerCinemaHalls = new LinearLayoutManager(thisFragment.getContext());
            recyclerViewCinemaHalls.setLayoutManager(layoutManagerCinemaHalls);

            if (recyclerViewCinemaHalls.getItemDecorationCount() == 0)
                recyclerViewCinemaHalls.addItemDecoration(new DividerItemDecoration(recyclerViewCinemaHalls.getContext(), DividerItemDecoration.VERTICAL));

            adapterCinemaHalls = new CinemaHallsAdapter(thisFragment.getContext(), this);
            recyclerViewCinemaHalls.setAdapter(adapterCinemaHalls);

            Cinema item = adapterCinema.getItem(position);

            //observer for changes in cinema halls from db
            cinemaHallsViewModel.getCinemaHalls(item.getName(), item.getCity()).observe(this, new Observer<List<CinemaHall>>()
            {
                @Override
                public void onChanged(@Nullable List<CinemaHall> cinemaHallList)
                {
                    if (cinemaHallList.size() > 0)
                    {
                        if (isCinemaHallEdited) {
                            adapterCinemaHalls.setCinemaHall(cinemaHallsViewModel.getCinemaHall(
                                    adapterCinemaHalls.getItem(adapterCinemaHalls.getAdapterPosition()).getId()));
                            isCinemaHallEdited = false;
                            return;
                        }

                        Cinema cinema = adapterCinema.getItem(cinemaClickedPosition); // Get clicked cinema.

                        // This check prevents access also to other halls list which was not touched.
                        if (cinemaHallList.get(0).getCinemaName().equals(cinema.getName()) &&
                                cinemaHallList.get(0).getCinemaCity().equals(cinema.getCity())) {

                            if (adapterCinemaHalls.getItemCount() != cinemaHallList.size())
                                adapterCinemaHalls.setCinemaHalls(cinemaHallList);

                            adapterCinemaHalls.notifyDataSetChanged();
                        }
                    }

                    if (isCinemaDeleted || cinemaHallList.size() == 0)
                    {
                        recyclerViewCinemaHalls.setVisibility(View.GONE);
                        isCinemaDeleted = false;
                    }
                }
            });

            cinemaClickedPosition = position;
            recyclerViewCHLastClicked = recyclerViewCinemaHalls;
        }
        else {
                recyclerViewCinemaHalls.setVisibility(View.GONE);
        }
    }

    private void openCinemaHallDialogFragment(int position, CinemaHall cinemaHall, boolean edit)
    {
        Cinema cinema = adapterCinema.getItem(position);
        FragmentTransaction ft = getFragmentManager().beginTransaction().addToBackStack(null);
        AddEditHallDialogFragment dialogFragment = new AddEditHallDialogFragment().newInstance(
                new String[]{cinema.getName(), cinema.getCity()}, cinemaHall, edit);

        if (edit)
            dialogFragment.setTargetFragment(thisFragment, CommonValues.EDIT_HALL_REQUEST_CODE);

        dialogFragment.show(ft, CommonValues.ADD_HALL_DIALOG_FRAGMENT_TAG);
    }

    private void deleteCinema(final int position)
    {
        new AlertDialog.Builder(getContext())
                .setMessage("Are you sure you want to delete this cinema?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Cinema cinema = adapterCinema.getItem(position);
                        isCinemaDeleted = true;
                        cinemaHallsViewModel.deleteCinemaHalls(cinema);
                        cinemaViewModel.delete(cinema);
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case (CommonValues.ADD_CINEMA_REQUEST_CODE):
                isCinemaAdded = true;
                break;
            case (CommonValues.EDIT_HALL_REQUEST_CODE):
                isCinemaHallEdited = true;
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String title);
    }
}
