package com.msapplications.btdt.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.msapplications.btdt.R;
import com.msapplications.btdt.interfaces.OnCountryClickListener;
import com.msapplications.btdt.objects.itemTypes.travel.CountryModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
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
        public TextView tvCountryName;
        public ImageView ivCountryImage, ivRemoveFromTravelList, ivBeenThere;

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

        private void onBindViewHolder(final CountryModel country)
        {
            tvCountryName.setText(country.getName());
            picasso.load(country.getImage()).fit().error(R.drawable.travel_default)
                    .networkPolicy(NetworkPolicy.NO_CACHE).into(ivCountryImage, new Callback()
            {
                @Override
                public void onSuccess() {
                    Log.i("Picasso success", country.getName() + " downloaded image succeeded");
                }

                @Override
                public void onError(Exception e) {
                    tvCountryName.setTextColor(ContextCompat.getColor(context, R.color.travel_been_there));
                    ivBeenThere.setImageDrawable(context.getDrawable(R.drawable.ic_flight_land));
                    ivRemoveFromTravelList.setImageDrawable(context.getDrawable(R.drawable.ic_delete_gray));
                    Log.e("Picasso failed", "Failed load image for " + country.getName() + " " + e.getMessage());
                }
            });

            if (country.isBeenThere()) {
                ivCountryImage.setAlpha(0.5f);
                tvCountryName.setTextColor(ContextCompat.getColor(context, R.color.travel_been_there));
                ivBeenThere.setImageDrawable(context.getDrawable(R.drawable.ic_flight_land));
                ivRemoveFromTravelList.setImageDrawable(context.getDrawable(R.drawable.ic_delete_gray));
            }
            else {
                ivCountryImage.setAlpha(1f);
                tvCountryName.setTextColor(Color.WHITE);
                ivBeenThere.setImageDrawable(context.getDrawable(R.drawable.ic_flight_take_off));
                ivRemoveFromTravelList.setImageDrawable(context.getDrawable(R.drawable.ic_delete));
            }

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
