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
import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;
import com.msapplications.btdt.objects.itemTypes.travel.CountryModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder>
{
    private LayoutInflater inflater;
    private Context context;
    private List<CountryModel> countries;
    private Picasso picasso;

    public CountriesAdapter(Context context, List<CountryModel> items)
    {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        countries = new ArrayList<>(items);
        picasso = Picasso.get();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private final ImageView ivFlag;
        private final TextView tvName;

        public ViewHolder(View view)
        {
            super(view);
            ivFlag = view.findViewById(R.id.ivCountryFlagItem);
            tvName = view.findViewById(R.id.tvCountryName);
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
}
