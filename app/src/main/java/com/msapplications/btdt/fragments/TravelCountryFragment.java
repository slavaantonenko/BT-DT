package com.msapplications.btdt.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.R;
import com.msapplications.btdt.objects.itemTypes.travel.CountryModel;
import com.squareup.picasso.Picasso;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link TravelCountryFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link TravelCountryFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class TravelCountryFragment extends Fragment //implements OnMapReadyCallback
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private GoogleMap googleMap;
//    MapView mapView;
    private CountryModel country;
    private Picasso picasso;
    private GoogleMap map;
    private ImageView ivCountryName;
    private TextView tvCountryName, tvNativeNameValue, tvCapitalCityName, tvLanguageName, tvNativeLanguageName, tvRegionName,
            tvAreaSize, tvPopulationSize, tvCurrencyValue, tvTimeZoneValue, tvCallingCodeValue, tvCountryCodeValues;
//    private OnFragmentInteractionListener mListener;

    public TravelCountryFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment TravelCountryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TravelCountryFragment newInstance(CountryModel country)
    {
        TravelCountryFragment fragment = new TravelCountryFragment();
        Bundle args = new Bundle();
//        args.putParcelable(CommonValues.COUNTRY_EXTRA, country);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            country = getArguments().getParcelable(CommonValues.COUNTRY_EXTRA);

        picasso = Picasso.get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_travel_country, container, false);
//        ivCountryName = view.findViewById(R.id.ivCountryFlag);
//        tvCountryName = view.findViewById(R.id.tvCountryName);
//        tvNativeNameValue = view.findViewById(R.id.tvNativeNameValue);
//        tvCapitalCityName = view.findViewById(R.id.tvCapitalCityName);
//        tvLanguageName = view.findViewById(R.id.tvLanguageName);
//        tvNativeLanguageName = view.findViewById(R.id.tvNativeLanguageName);
//        tvRegionName = view.findViewById(R.id.tvRegionName);
//        tvAreaSize = view.findViewById(R.id.tvAreaSize);
//        tvPopulationSize = view.findViewById(R.id.tvPopulationSize);
//        tvCurrencyValue = view.findViewById(R.id.tvCurrencyValue);
//        tvTimeZoneValue = view.findViewById(R.id.tvTimeZoneValue);
//        tvCallingCodeValue = view.findViewById(R.id.tvCallingCodeValue);
//        tvCountryCodeValues = view.findViewById(R.id.tvCountryCodeValues);

//        picasso.load(country.getFlag()).into(ivCountryName);
//        tvCountryName.setText(country.getName());
//        tvNativeNameValue.setText(country.getNativeName());
//        tvCapitalCityName.setText(country.getCapital());
//        tvLanguageName.setText(country.getLanguage());
//        tvNativeLanguageName.setText(country.getNativeLanguage());
//        tvRegionName.setText(country.getRegion());
//        tvAreaSize.setText(Double.toString(country.getArea()) + Html.fromHtml("X<sup>2</sup>"));
//        tvPopulationSize.setText(Integer.toString(country.getPopulation()));
//        tvCurrencyValue.setText(country.getCurrency());
//        tvTimeZoneValue.setText(country.getTimezone());
//        tvCallingCodeValue.setText(country.getCallingCode());
//        tvCountryCodeValues.setText(country.getCode() + ", " + country.getSecondaryCode());
//        mapView = view.findViewById(R.id.mvCountry);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mvCountry);
//        mapFragment.getMapAsync(this);
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap)
//    {
//        map = googleMap;
//
//        Gson gson = new Gson();
//        CountriesCoordinatesResults results = gson.fromJson(Utils.loadJSONFromAsset(
//                getContext(), "countries_coordinates"), CountriesCoordinatesResults.class);
//
//        double latitude = 0;
//        double longitude = 0;
//
//        for (CountriesCoordinates country : results.getResults()) {
////            if (country.getCountryCode().equals("DE")) {
//            if (country.getCountryCode().equals("AT")) {
//                latitude = country.getLatitude();
//                longitude = country.getLongitude();
//                break;
//            }
//        }
//
//        // Move the camera to location
////        LatLng country = new LatLng(latitude, longitude);
////        LatLng country = new LatLng(47.33333333, 13.33333333);
//        LatLng country = new LatLng(38, -97);
////        map.addMarker(new MarkerOptions().position(country).title("Marker in Sydney"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(country));
//
//        // For zooming automatically to the location of the marker
////        CameraPosition cameraPosition = new CameraPosition.Builder().target(country).zoom(4.4F).build();
//        CameraPosition cameraPosition = new CameraPosition.Builder().target(country).zoom(3F).build();
//        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//
//        try
//        {
////            GeoJsonLayer layer = new GeoJsonLayer(map, R.raw.deu_geojson, getContext());
//            GeoJsonLayer layer = new GeoJsonLayer(map, R.raw.usa_geojson, getContext());
//            GeoJsonPolygonStyle style = layer.getDefaultPolygonStyle();
//            style.setFillColor(ContextCompat.getColor(getContext(), R.color.highlightCountry));
//            style.setStrokeColor(ContextCompat.getColor(getContext(), R.color.highlightCountry));
//            style.setStrokeWidth(1F);
////            style.setFillColor(R.color.highlightCountry);
////            style.setStrokeColor(R.color.highlightCountry);
//
//            layer.addLayerToMap();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    //    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri)
//    {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
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
//
//    @Override
//    public void onDetach()
//    {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener
//    {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
