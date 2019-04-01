package com.msapplications.btdt.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.data.Geometry;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonMultiPolygon;
import com.google.maps.android.data.geojson.GeoJsonPolygon;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;
import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.R;
import com.msapplications.btdt.Utils;
import com.msapplications.btdt.interfaces.CountryService;
import com.msapplications.btdt.interfaces.OnFloatingActionClick;
import com.msapplications.btdt.objects.itemTypes.travel.CountryImagesList;
import com.msapplications.btdt.objects.itemTypes.travel.CountryModel;
import com.msapplications.btdt.rest.RestClientManager;
import com.msapplications.btdt.room_storage.travel.CountryViewModel;
import com.squareup.picasso.Picasso;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TravelCountryActivity extends AppCompatActivity  implements OnMapReadyCallback, OnFloatingActionClick
{
    private CountryViewModel countryViewModel;
    private CountryModel country;
    private Picasso picasso;
    private GoogleMap map;
    private ConstraintLayout clOverview;
    private ExpandableLayout elOverview;
    private ImageView ivCountryFlag, ivExpand;
    private FloatingActionButton fabCountry;
    private TextView tvCountryName, tvNativeNameValue, tvCapitalCityName, tvLanguageName, tvNativeLanguageName, tvRegionName,
            tvAreaSize, tvPopulationSize, tvCurrencyValue, tvTimeZoneValue, tvCallingCodeValue, tvCountryCodeValues;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_country);
        clOverview = findViewById(R.id.clOverview);
        elOverview = findViewById(R.id.elOverview);
        ivExpand = findViewById(R.id.ivExpandOverview);
        ivCountryFlag = findViewById(R.id.ivCountryFlag);
        fabCountry = findViewById(R.id.fabCountry);
        tvCountryName = findViewById(R.id.tvCountryName);
        tvNativeNameValue = findViewById(R.id.tvNativeNameValue);
        tvCapitalCityName = findViewById(R.id.tvCapitalCityName);
        tvLanguageName = findViewById(R.id.tvLanguageName);
        tvNativeLanguageName = findViewById(R.id.tvNativeLanguageName);
        tvRegionName = findViewById(R.id.tvRegionName);
        tvAreaSize = findViewById(R.id.tvAreaSize);
        tvPopulationSize = findViewById(R.id.tvPopulationSize);
        tvCurrencyValue = findViewById(R.id.tvCurrencyValue);
        tvTimeZoneValue = findViewById(R.id.tvTimeZoneValue);
        tvCallingCodeValue = findViewById(R.id.tvCallingCodeValue);
        tvCountryCodeValues = findViewById(R.id.tvCountryCodeValues);

        picasso = Picasso.get();

        country = getIntent().getParcelableExtra(CommonValues.COUNTRY_EXTRA);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapCountry);
        mapFragment.getMapAsync(this);

        initializeView();

        countryViewModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        fabCountry.setOnClickListener(onFabClick());
//        fabCountry.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                if (!country.isInTravelList())
//                {
//                    Intent resultIntent = new Intent();
//                    resultIntent.putExtra(CommonValues.COUNTRY_EXTRA, country);
//                    setResult(CommonValues.ADDED_TO_TRAVEL_LIST, resultIntent);
//
//                    country.setInTravelList(true);
//                    getImage();
//                    Toast.makeText(getBaseContext(), "Congrats! You just added " + country.getName() + " to your travel list."
//                            , Toast.LENGTH_LONG).show();
//                }
//                else
//                    Toast.makeText(getBaseContext(), "You already plan to visit " + country.getName()
//                            , Toast.LENGTH_LONG).show();
//            }
//        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        map = googleMap;

        try
        {
            int rawID = getResources().getIdentifier(
                    country.getSecondaryCode().toLowerCase() + "_geojson", "raw", getPackageName());
            final GeoJsonLayer layer = new GeoJsonLayer(map, rawID, this);
            layer.addLayerToMap();

            if (layer.getFeatures().iterator().hasNext())
            {
                // Waiting till layer will be added to the map.
                map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback()
                {
                    @Override
                    public void onMapLoaded()
                    {
                        // Get the bounding box builder.
                        LatLngBounds.Builder builder = LatLngBounds.builder();

                        Geometry geometry = layer.getFeatures().iterator().next().getGeometry();

                        // Some countries have multiply borders like Greece (because of the many islands).
                        if (geometry.getGeometryType().equals("Polygon")) {
                            List<? extends List<LatLng>> listOfCoordinates = ((GeoJsonPolygon) geometry).getCoordinates();
                            builder = getLatLngBoundsBuilder(listOfCoordinates, builder);
                        }
                        else if (geometry.getGeometryType().equals("MultiPolygon"))
                        {
                            List<GeoJsonPolygon> polygons = ((GeoJsonMultiPolygon) geometry).getPolygons();
                            if (polygons != null)
                                for (GeoJsonPolygon polygon : polygons)
                                    builder = getLatLngBoundsBuilder(polygon.getCoordinates(), builder);
                        }

                        LatLngBounds bounds = builder.build();
                        int padding = 100; // offset from edges of the map in pixels
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                        map.moveCamera(cameraUpdate);
                        map.animateCamera(cameraUpdate);
                    }
                });
            }
            else
            {
                // Move the camera to location
                LatLng latLng = new LatLng(country.getLatitude(), country.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(8.0F).build();
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }

            map.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener()
            {
                @Override
                public void onCameraIdle()
                {
                    if (map.getCameraPosition().zoom > 7)
                        setPolygonStyle(layer, android.R.color.transparent, android.R.color.transparent, 0F);
                    else
                        setPolygonStyle(layer, R.color.highlightCountry, R.color.highlightCountry, 2F);
                }
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View.OnClickListener onFabClick()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!country.isInTravelList())
                {
//                    Intent resultIntent = new Intent();
//                    resultIntent.putExtra(CommonValues.COUNTRY_EXTRA, country);
//                    setResult(CommonValues.ADDED_TO_TRAVEL_LIST, resultIntent);

                    Utils.saveBooleanToCache(getBaseContext(), CommonValues.TRAVEL_LIST_CHANGED, true);
                    country.setInTravelList(true);
                    getImage();
                    Toast.makeText(getBaseContext(), "Congrats! You just added " + country.getName() + " to your travel list."
                            , Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getBaseContext(), "You already plan to visit " + country.getName()
                            , Toast.LENGTH_LONG).show();
            }
        };
    }

    private void initializeView()
    {
        clOverview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (elOverview.isExpanded()) {
                    elOverview.collapse();
                    ivExpand.setImageResource(R.drawable.ic_collapse);
                }
                else {
                    elOverview.expand();
                    ivExpand.setImageResource(R.drawable.ic_expand);
                }
            }
        });


        picasso.load(country.getFlag()).into(ivCountryFlag);
        tvCountryName.setText(country.getName());
        tvNativeNameValue.setText(country.getNativeName());
        tvCapitalCityName.setText(country.getCapital());
        tvLanguageName.setText(country.getLanguage());
        tvNativeLanguageName.setText(country.getNativeLanguage());
        tvRegionName.setText(country.getRegion());
        tvAreaSize.setText(getString(R.string.area_size_proportion, String.valueOf(country.getArea())));
        tvPopulationSize.setText(String.valueOf(country.getPopulation()));
        tvCurrencyValue.setText(country.getCurrency());
        tvTimeZoneValue.setText(country.getTimezone());
        tvCallingCodeValue.setText(getString(R.string.calling_code, country.getCallingCode()));
        tvCountryCodeValues.setText(getString(R.string.country_codes, country.getCode(), country.getSecondaryCode()));
    }

    private void getImage()
    {
        if (country.getImage() == null)
        {
            CountryService countryService = RestClientManager.getCountryServiceInstance(CountryService.BASE_IMAGES_API_URL);
            countryService.getCountryImages(country.getCapital()).enqueue(new Callback<CountryImagesList>()
            {
                @Override
                public void onResponse(Call<CountryImagesList> call, Response<CountryImagesList> response)
                {
                    country.setImage(response.body().getHits().get(0).getWebformatURL());
                    countryViewModel.updateIsInTravelList(country);
                }

                @Override
                public void onFailure(Call<CountryImagesList> call, Throwable t)
                {
                    Log.i("failure", t.getMessage());
                }
            });
        }
    }

    /**
     * The method adds coordinates to existed builder.
     * @param listOfCoordinates border coordinates, could be multiply borders.
     * @param builder Latitude Longitude existed builder.
     * @return
     */
    private LatLngBounds.Builder getLatLngBoundsBuilder(List<? extends List<LatLng>> listOfCoordinates, LatLngBounds.Builder builder)
    {
        if (listOfCoordinates != null)
            for (List<LatLng> coordinates : listOfCoordinates)
                for (LatLng latLng : coordinates)
                    builder.include(latLng);

        return builder;
    }

    /**
     * The method sets country layer style.
     * @param layer country layer.
     * @param fillColor
     * @param strokeColor
     * @param strokeWidth
     */
    private void setPolygonStyle(GeoJsonLayer layer, int fillColor, int strokeColor, float strokeWidth)
    {
        GeoJsonPolygonStyle style = layer.getDefaultPolygonStyle();
        style.setFillColor(ContextCompat.getColor(getBaseContext(), fillColor));
        style.setStrokeColor(ContextCompat.getColor(getBaseContext(), strokeColor));
        style.setStrokeWidth(strokeWidth);
    }
}
