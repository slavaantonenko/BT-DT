package com.msapplications.btdt.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.msapplications.btdt.R;
import com.msapplications.btdt.interfaces.OnCountryClickListener;
import com.msapplications.btdt.objects.itemTypes.travel.CountryModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TravelListAdapter extends RecyclerView.Adapter<TravelListAdapter.ViewHolder>
{
    private Context context;
    private List<CountryModel> countries;
    private Picasso picasso;
    private OnCountryClickListener countryClickListener;

    public TravelListAdapter(Context context, OnCountryClickListener countryClickListener)
    {
        this.context = context;
        this.countryClickListener = countryClickListener;
        picasso = Picasso.get();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public ImageView ivCountryImage, ivRemoveFromTravelList, ivBeenThere;
        public TextView tvCountryName;

        public ViewHolder(View view) {
            super(view);
            ivCountryImage = view.findViewById(R.id.ivCountryImage);
            tvCountryName = view.findViewById(R.id.tvTravelListCountry);
            ivRemoveFromTravelList = view.findViewById(R.id.ivRemoveFromTravelList);
            ivBeenThere = view.findViewById(R.id.ivBeenThere);
            view.setOnClickListener(this);
            ivRemoveFromTravelList.setOnClickListener(this);
            ivBeenThere.setOnClickListener(this);
        }

        private void onBindViewHolder(CountryModel country)
        {
            tvCountryName.setText(country.getName());
            picasso.load(country.getImage()).fit().into(ivCountryImage);

            if (country.isBeenThere())
                ivBeenThere.setImageDrawable(context.getDrawable(R.drawable.ic_flight_land));
            else
                ivBeenThere.setImageDrawable(context.getDrawable(R.drawable.ic_flight_take_off));

        }

        @Override
        public void onClick(View view)
        {
            if (countryClickListener == null)
                return;

            countryClickListener.onCountryClick(view, getAdapterPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_travel_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBindViewHolder(countries.get(position));
    }

    @Override
    public int getItemCount()
    {
        if (countries == null)
            return 0;

        return countries.size();
    }

    public void setCountries(List<CountryModel> countries) {
        this.countries = countries;
        notifyDataSetChanged();
    }

    public CountryModel getItem(int position) {
        return countries.get(position);
    }
}
