package com.msapplications.btdt.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.R;
import com.msapplications.btdt.adapters.NotesAdapter;
import com.msapplications.btdt.interfaces.NotesEditor;
import com.msapplications.btdt.objects.itemTypes.NoteItem;
import com.msapplications.btdt.room_storage.note.NoteItemViewModel;

import java.util.ArrayList;
import java.util.List;


public class NotesFragment extends AbstractFragmentItems implements NotesEditor
{
    private String title = "";
    private ArrayList<NoteItem> notes = null;
    private Fragment thisFragment = this;
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private NoteItemViewModel noteItemViewModel;
    private OnFragmentInteractionListener onFragmentInteractionListener;
    private boolean updateAdapter = true;
    private int notifyFromIndex = -1;
    private boolean isRemovedItems = false;

    public NotesFragment() {
    }

    public static NotesFragment newInstance(String title, int categoryID)
    {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putString(CommonValues.FRAGMENT_TITLE, title);
        args.putInt(CommonValues.CATEGORY_ID_EXTRA, categoryID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            title = getArguments().getString(CommonValues.FRAGMENT_TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notes, container, false);
        recyclerView = view.findViewById(R.id.rvNotes);

        if (onFragmentInteractionListener != null)
            onFragmentInteractionListener.onFragmentInteraction(title);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        if(categoryID != -1) {

            noteItemViewModel = ViewModelProviders.of(this).get(NoteItemViewModel.class);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(thisFragment.getContext());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new NotesAdapter(this, noteItemViewModel);
            recyclerView.setAdapter(adapter);

            noteItemViewModel.getNoteItems(categoryID).observe(this, new Observer<List<NoteItem>>() {
                @Override
                public void onChanged(@Nullable final List<NoteItem> noteItems) {
                    if(updateAdapter) {
                        adapter.setNoteItems(noteItems, notifyFromIndex, isRemovedItems);
                        updateAdapter = false;
                        isRemovedItems = false;
                    }
                }
            });

        }
    }

    //add new line
    @Override
    public void OnEnterKeyClicked(int id, int newLineNumber, EditText editText, boolean isCheckBox)
    {
        //notes.get(position).setText(editText.getText().toString());
        noteItemViewModel.updateText(id, editText.getText().toString());
        noteItemViewModel.increaseLineNumbers(newLineNumber, categoryID);
        NoteItem newNoteItem = new NoteItem(0, categoryID, newLineNumber);
        newNoteItem.setCheckBox(isCheckBox);
        noteItemViewModel.insert(newNoteItem);
        updateAdapter = true;
        notifyFromIndex = newLineNumber -1;
       // adapter.notifyItemChanged(newLineNumber);
    }

    //remove line
    @Override
    public void onBackspaceClicked(int noteItemID, int position) {
        notifyFromIndex = position;
        isRemovedItems = true;
        noteItemViewModel.delete(noteItemID);
        noteItemViewModel.decreaseLineNumbers(position, categoryID);
        updateAdapter = true;
    }

    //current line is edited, save to db
    @Override
    public void saveCurrentEdit(int id, String newText, int adapterPosition){
        notifyFromIndex = adapterPosition;
        noteItemViewModel.updateText(id, newText);
        updateAdapter = true;

    }

    @Override
    public void ocCheckedClickListener(int noteItemId, boolean newValue) {
        noteItemViewModel.updateChecked(noteItemId, newValue);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try {
            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentInteractionListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String title);
    }
}
