package com.msapplications.btdt.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.msapplications.btdt.TextEditorButtons;
import com.msapplications.btdt.adapters.CheckListViewAdapter;
import com.msapplications.btdt.interfaces.OnCheckBoxClickListener;
import com.msapplications.btdt.R;
import com.msapplications.btdt.interfaces.OnEnterKeyClickedListener;
import com.msapplications.btdt.objects.itemTypes.CheckListItem;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CheckListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CheckListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckListFragment extends AbstractFragmentItems implements OnEnterKeyClickedListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<CheckListItem> checkListItems = null;
    private Fragment thisFragment = this;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private OnFragmentInteractionListener mListener;

    public CheckListFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckListFragment.
     */
    public static CheckListFragment newInstance(String param1, String param2)
    {
        CheckListFragment fragment = new CheckListFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        if (category != null)
            this.checkListItems = (ArrayList<CheckListItem>) category.getItemsInCat();

        initRecyclerView(view);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

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
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void OnEnterKeyClicked(EditText editText, int index)
    {
        //save current
        checkListItems.get(index).setName(editText.getText().toString());
        //add new enpy
        checkListItems.add(index+1, new CheckListItem());
       // adapter.notifyItemInserted(index+1);
        adapter.notifyDataSetChanged();
    }

    private void initRecyclerView(View view)
    {
        if(checkListItems.size() == 0) {
            checkListItems.add(new CheckListItem());
        }

        recyclerView = view.findViewById(R.id.rvCheckList);
        layoutManager = new LinearLayoutManager(thisFragment.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CheckListViewAdapter(thisFragment.getContext(), checkListItems, this);
        recyclerView.setAdapter(adapter);
    }
}
