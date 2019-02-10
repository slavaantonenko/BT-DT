package com.msapplications.btdt.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.msapplications.btdt.R;
import com.msapplications.btdt.adapters.CheckListViewAdapter;
import com.msapplications.btdt.adapters.NotesAdapter;
import com.msapplications.btdt.interfaces.OnEnterKeyClickedListener;
import com.msapplications.btdt.objects.itemTypes.CheckListItem;
import com.msapplications.btdt.objects.itemTypes.NoteItem;

import java.util.ArrayList;


public class NotesFragment extends AbstractFragmentItems implements OnEnterKeyClickedListener
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
        View view =  inflater.inflate(R.layout.fragment_notes, container, false);
        recyclerView = view.findViewById(R.id.rvNotes);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        if(category != null) {
            this.notes = (ArrayList<NoteItem>) category.getItemsInCat();

            if(notes == null)
                notes = new ArrayList<>();

            if(notes.size() == 0) {
                notes.add(new NoteItem());
            }

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(thisFragment.getContext());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new NotesAdapter(notes, this);
            recyclerView.setAdapter(adapter);
        }
    }

    public void onButtonPressed(Uri uri)
    {
    }

    @Override
    public void OnEnterKeyClicked(EditText editText, int position)
    {
        notes.get(position).setText(editText.getText().toString());
        notes.add(position+1, new NoteItem(notes.get(position).isChecked()));
        adapter.notifyItemInserted(position+1);
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
