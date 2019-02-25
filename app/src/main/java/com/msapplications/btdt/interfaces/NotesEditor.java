package com.msapplications.btdt.interfaces;

import android.widget.EditText;

public interface NotesEditor
{
    void OnEnterKeyClicked(int idClicked, int newLineNumber, EditText editText, boolean isChecked);
    void saveCurrentEdit(int id, String newText, int adapterPosition);
    void onBackspaceClicked(int noteItemID, int position);
    void ocCheckedClickListener(int noteItemId, boolean newValue);

}
