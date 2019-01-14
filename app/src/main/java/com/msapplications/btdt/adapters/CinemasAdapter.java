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
import com.msapplications.btdt.interfaces.OnCinemaClickListener;
import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;

import java.util.List;

public class CinemasAdapter extends RecyclerView.Adapter<CinemasAdapter.ViewHolder>
{
    private Context context;
//    private ArrayList<Cinema> cinemasList;
    private List<Cinema> cinemasList;
    private OnCinemaClickListener cinemaClickListener;

    public CinemasAdapter(Context context, OnCinemaClickListener listener)
    {
        this.context = context;
        cinemaClickListener = listener;
//        this.cinemasList = CinemasList.getCinemas(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView name, city;
        public ImageView logo;
        public ConstraintLayout viewForeground, deleteCinemaViewBackground, addSeatViewBackground;

        public ViewHolder(View view)
        {
            super(view);
            name = view.findViewById(R.id.tvCinemaName);
            city = view.findViewById(R.id.tvCinemaCity);
            logo = view.findViewById(R.id.ivCinemaLogo);
            viewForeground = view.findViewById(R.id.clCinemaViewForeground);
            deleteCinemaViewBackground = view.findViewById(R.id.clCinemaDeleteViewBackground);
            addSeatViewBackground = view.findViewById(R.id.clCinemaAddSeatViewBackground);
            view.setOnClickListener(this);
        }

        public void onBindViewHolder(Cinema cinema)
        {
            name.setText(cinema.getName());
            city.setText(cinema.getCity());

            // loading album cover using Glide library
            Glide.with(context).load(cinema.getLogo()).into(logo);
        }

        @Override
        public void onClick(View view)
        {
            if (cinemaClickListener == null)
                return;

            cinemaClickListener.onCinemaClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cinema, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.onBindViewHolder(cinemasList.get(position));
    }

    @Override
    public int getItemCount() {
        if (cinemasList == null)
            return 0;

        return cinemasList.size();
    }

    public List<Cinema> getCinemasList() {
        return cinemasList;
    }

    public Cinema getItem(int position) {
        return cinemasList.get(position);
    }

    public void setCinemas(List<Cinema> cinemas) {
        cinemasList = cinemas;
        notifyDataSetChanged();
    }
}
