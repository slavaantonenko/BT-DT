package com.msapplications.btdt.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.msapplications.btdt.room_storage.cinema.CinemaViewModel;
import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.R;
import com.msapplications.btdt.SwipeListCallback;
import com.msapplications.btdt.adapters.CinemasAdapter;
import com.msapplications.btdt.dialogs.AddCinemaDialogFragment;
import com.msapplications.btdt.interfaces.OnFloatingActionClick;
import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;

import java.util.ArrayList;
import java.util.List;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link CinemaSeatsFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link CinemaSeatsFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class CinemaSeatsFragment extends Fragment implements OnFloatingActionClick
{
    private ArrayList<Cinema> cinemasItems = null;
    private Fragment thisFragment = this;
    private RecyclerView recyclerView;
    private CinemasAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private CinemaViewModel cinemaViewModel;

//    private OnFragmentInteractionListener mListener;

    public CinemaSeatsFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CinemaSeatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CinemaSeatsFragment newInstance(String param1, String param2)
    {
        CinemaSeatsFragment fragment = new CinemaSeatsFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
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
//        adapter.setCinemas(cinemaViewModel.getCinemas().getValue());

        cinemaViewModel.getCinemas().observe(this, new Observer<List<Cinema>>() {
            @Override
            public void onChanged(@Nullable final List<Cinema> cinemas) {
                // Update the cached copy of the words in the adapter.
//                adapter.setWords(words);
                adapter.setCinemas(cinemas);
            }
        });
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
                    dialogFragment.show(ft, CommonValues.ADD_CINEMA_DIALOG_FRAGMENT_TAG);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void initRecyclerView(final View view)
    {
        recyclerView = view.findViewById(R.id.rvCinemas);
        layoutManager = new LinearLayoutManager(thisFragment.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CinemasAdapter(thisFragment.getContext());
        recyclerView.setAdapter(adapter);

        SwipeListCallback swipeListCallback = new SwipeListCallback()
        {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
            {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.RIGHT) {
                    adapter.notifyDataSetChanged();
                }
                else if (direction == ItemTouchHelper.LEFT)
                {
                    String name = adapter.getItem(position).getName();
                    String city = adapter.getItem(position).getCity();
                    cinemaViewModel.delete(adapter.getItem(position));
                    adapter.notifyDataSetChanged();
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeListCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView); // Set swipe to RecyclerView
    }
}
