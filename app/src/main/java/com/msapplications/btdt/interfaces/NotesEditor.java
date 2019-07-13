package com.msapplications.btdt.interfaces;

import android.widget.EditText;

/**
 * for editing a note
 */
public interface NotesEditor
{
    void OnEnterKeyClicked(int idClicked, int newLineNumber, EditText editText, boolean isChecked);
    void saveCurrentEdit(int id, String newText, int adapterPosition);
    void onBackspaceClicked(int noteItemID, int position);
    void onCheckedClickListener(int noteItemId, boolean newValue);

}
