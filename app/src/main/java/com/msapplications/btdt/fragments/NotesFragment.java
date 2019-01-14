package com.msapplications.btdt.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.msapplications.btdt.R;
import com.msapplications.btdt.objects.itemTypes.CheckListItem;
import com.msapplications.btdt.objects.itemTypes.NoteItem;

import java.util.ArrayList;


public class NotesFragment extends AbstractFragmentItems
{

    private ArrayList<NoteItem> notes = null;
    private Fragment thisFragment = this;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    public NotesFragment()
    {
    }

    public static NotesFragment newInstance(String param1, String param2)
    {
        NotesFragment fragment = new NotesFragment();
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
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    public void onButtonPressed(Uri uri)
    {
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

}
