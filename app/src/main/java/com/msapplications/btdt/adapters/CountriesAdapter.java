package com.msapplications.btdt.adapters;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.msapplications.btdt.R;
import com.msapplications.btdt.interfaces.OnCountryClickListener;
import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;
import com.msapplications.btdt.objects.itemTypes.travel.Country;
import com.msapplications.btdt.objects.itemTypes.travel.CountryModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/*
not used until travel category is implemented
 */
public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder>
{
    private LayoutInflater inflater;
    private Context context;
    private List<CountryModel> countries;
    private Picasso picasso;
    private OnCountryClickListener countryClickListener;

    public CountriesAdapter(Context context, List<CountryModel> items, OnCountryClickListener countryClickListener)
    {
        this.context = context;
        this.countryClickListener = countryClickListener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        countries = new ArrayList<>(items);
        picasso = Picasso.get();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final ImageView ivFlag;
        private final TextView tvName;

        public ViewHolder(View view)
        {
            super(view);
            ivFlag = view.findViewById(R.id.ivCountryFlagItem);
            tvName = view.findViewById(R.id.tvCountryName);
            view.setOnClickListener(this);
        }

        public void onBindViewHolder(CountryModel countryModel)
        {
            picasso.load(countryModel.getFlag())
                    .into(ivFlag, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.i("onSuccess", "onSuccess");
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("onError", "onError() called with: e = [" + e + "]");
                        }
                    });

            tvName.setText(countryModel.getName());
        }

        @Override
        public void onClick(View view) {
            countryClickListener.onCountryClick(view, getAdapterPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_country, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.onBindViewHolder(countries.get(position));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public void setData(List<CountryModel> items) {
        countries.clear();
        countries.addAll(items);
        notifyDataSetChanged();
    }

    public CountryModel getItem(int position) {
        return countries.get(position);
    }
}
