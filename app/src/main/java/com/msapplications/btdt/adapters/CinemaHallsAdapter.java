package com.msapplications.btdt.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.msapplications.btdt.R;
import com.msapplications.btdt.interfaces.OnCinemaHallLongClickListener;
import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;
import com.msapplications.btdt.objects.itemTypes.cinema.CinemaHall;

import java.util.List;

public class CinemaHallsAdapter extends RecyclerView.Adapter<CinemaHallsAdapter.ViewHolder>
{
    private Context context;
    private List<CinemaHall> cinemaHallsList;
    private OnCinemaHallLongClickListener cinemaHallLongClickListener;

    public CinemaHallsAdapter(Context context, OnCinemaHallLongClickListener listener) {
        this.context = context;
        cinemaHallLongClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener
    {
        private TextView hall, row;
        private ImageView status;

        public ViewHolder(@NonNull View view)
        {
            super(view);
            hall = view.findViewById(R.id.tvHallNumber);
            row = view.findViewById(R.id.tvRowNumber);
            status = view.findViewById(R.id.ivStatus);
            view.setOnLongClickListener(this);
        }

        public void onBindViewHolder(CinemaHall cinemaHall)
        {
            hall.setText(cinemaHall.getHall());
            row.setText(cinemaHall.getRow());

            // loading album cover using Glide library
            int drawableID = R.drawable.ic_like;
            if (!cinemaHall.isStatus())
                drawableID = R.drawable.ic_dislike;

            Glide.with(context).load(drawableID).into(status);
        }

        @Override
        public boolean onLongClick(View view)
        {
            cinemaHallLongClickListener.onCinemaLongClick(view, getAdapterPosition());
            return true;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cinema_hall, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.onBindViewHolder(cinemaHallsList.get(position));
    }

    @Override
    public int getItemCount() {
        if (cinemaHallsList == null)
            return 0;

        return cinemaHallsList.size();
    }

    public CinemaHall getItem(int position) {
        return cinemaHallsList.get(position);
    }

    public void setCinemaHalls(List<CinemaHall> cinemaHalls) {
        cinemaHallsList = cinemaHalls;
        notifyDataSetChanged();
    }
}
