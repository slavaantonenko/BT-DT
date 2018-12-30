package com.msapplications.btdt.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.msapplications.btdt.R;
import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;

import java.util.List;

public class CinemasAdapter extends RecyclerView.Adapter<CinemasAdapter.MyViewHolder>
{
    private Context context;
//    private ArrayList<Cinema> cinemasList;
    private List<Cinema> cinemasList;

    public CinemasAdapter(Context context)
    {
        this.context = context;
//        this.cinemasList = CinemasList.getCinemas(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name, city;
        public ImageView logo;
        public ConstraintLayout viewForeground, deleteCinemaViewBackground, addSeatViewBackground;

        public MyViewHolder(View view)
        {
            super(view);
            name = view.findViewById(R.id.tvCinemaName);
            city = view.findViewById(R.id.tvCinemaCity);
            logo = view.findViewById(R.id.ivCinemaLogo);
            viewForeground = view.findViewById(R.id.clCinemaViewForeground);
            deleteCinemaViewBackground = view.findViewById(R.id.clCinemaDeleteViewBackground);
            addSeatViewBackground = view.findViewById(R.id.clCinemaAddSeatViewBackground);
        }
    }

    @Override
    public CinemasAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cinema, parent, false);

        return new CinemasAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CinemasAdapter.MyViewHolder holder, final int position)
    {
        Cinema cinema = cinemasList.get(position);
        holder.name.setText(cinema.getName());
        holder.city.setText(cinema.getCity());

        // loading album cover using Glide library
        Glide.with(context).load(cinema.getLogo()).into(holder.logo);
    }

    @Override
    public int getItemCount() {
        if (cinemasList == null)
            return 0;

        return cinemasList.size();
    }

    public Cinema getItem(int position) {
        return cinemasList.get(position);
    }

    public void setCinemas(List<Cinema> cinemas) {
        cinemasList = cinemas;
        notifyDataSetChanged();
    }
}
