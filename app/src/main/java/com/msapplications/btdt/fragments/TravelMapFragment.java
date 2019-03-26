package com.msapplications.btdt.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Html;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;
import com.msapplications.btdt.R;
import com.msapplications.btdt.Utils;
import com.msapplications.btdt.objects.itemTypes.travel.CountriesCoordinates;
import com.msapplications.btdt.objects.itemTypes.travel.CountriesCoordinatesResults;
import com.msapplications.btdt.objects.itemTypes.travel.CountryModel;
import com.msapplications.btdt.room_storage.travel.CountryViewModel;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link TravelMapFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link TravelMapFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class TravelMapFragment extends Fragment implements OnMapReadyCallback
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GoogleMap map;
    private CountryViewModel countryViewModel;
//    private GoogleMap googleMap;
//    MapView mapView;

//    private OnFragmentInteractionListener mListener;

    public TravelMapFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TravelMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TravelMapFragment newInstance(String param1, String param2)
    {
        TravelMapFragment fragment = new TravelMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_travel_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        countryViewModel = ViewModelProviders.of(this).get(CountryViewModel.class);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        countryViewModel.getTravelListCountries().observe(this, new Observer<List<CountryModel>>()
        {
            @Override
            public void onChanged(@Nullable List<CountryModel> countries)
            {
                for (CountryModel country : countries)
                {
                    LatLng latLng = new LatLng(country.getLatitude(), country.getLongitude());
                    int iconID = R.drawable.ic_place_marker;

                    if (country.isBeenThere())
                        iconID = R.drawable.ic_place_marker_been_there;

                    map.addMarker(new MarkerOptions()
                            .position(latLng)
                            .icon(bitmapDescriptorFromVector(getContext(), iconID)));
                }
            }
        });

//        Gson gson = new Gson();
//        BufferedReader br = null;
//        try {
//            br = new BufferedReader(new FileReader("data.json"));
//            Result result = gson.fromJson(br, Result.class);
//            if (result != null) {
//                for (Todo t : result.getTodos()) {
//                    System.out.println(t.getId() + " - " + t.getTitle() + " - " + t.getCompleted());
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }

//        Gson gson = new Gson();
//        CountriesCoordinatesResults results = gson.fromJson(Utils.loadJSONFromAsset(
//                getContext(), "countries_coordinates"), CountriesCoordinatesResults.class);
//
//        double latitude = 0;
//        double longitude = 0;
//
//        for (CountriesCoordinates country : results.getResults()) {
//            if (country.getCountryCode().equals("AT")) {
//                latitude = country.getLatitude();
//                longitude = country.getLongitude();
//                break;
//            }
//        }

        // Move the camera to location
//        LatLng country = new LatLng(latitude, longitude);
//        LatLng country = new LatLng(49.75, 6.16666666);
//        map.addMarker(new MarkerOptions().position(country).title("Marker in Sydney"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(country));

        // For zooming automatically to the location of the marker
//        CameraPosition cameraPosition = new CameraPosition.Builder().target(country).zoom((float) 5.5).build();
//        CameraPosition cameraPosition = new CameraPosition.Builder().target(country).zoom(5.5F).build();
//        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

//        BitmapDescriptor bitmapDescriptor;
//
//        ivCountryFlag.invalidate();
//        BitmapDrawable drawable = (BitmapDrawable) ivCountryFlag.getDrawable();
//        Bitmap bitmap = drawable.getBitmap();
//        bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
//
//        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
////                                .image(BitmapDescriptorFactory.fromResource(R.drawable.newark_nj_1922))
//                .image(bitmapDescriptor)
//                .positionFromBounds(bounds);
//        map.addGroundOverlay(newarkMap);



//        private Target target = new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//            }
//        }
//
//        private void someMethod() {
//        Picasso.with(this).load("url").into(target);
//    }
//
//        @Override
//        public void onDestroy() {  // could be in onPause or onStop
//        Picasso.with(this).cancelRequest(target);
//        super.onDestroy();
//    }



//        try
//        {
////            GeoJsonLayer layer = new GeoJsonLayer(map, R.raw.deu_geojson, getContext());
////            GeoJsonPolygonStyle style = layer.getDefaultPolygonStyle();
////            style.setFillColor(ContextCompat.getColor(getContext(), R.color.highlightCountry));
////            style.setStrokeColor(ContextCompat.getColor(getContext(), R.color.highlightCountry));
////            style.setStrokeWidth(1F);
////            style.setFillColor(R.color.highlightCountry);
////            style.setStrokeColor(R.color.highlightCountry);
//
////            layer.addLayerToMap();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId)
    {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri)
//    {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context)
//    {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach()
//    {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener
//    {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
