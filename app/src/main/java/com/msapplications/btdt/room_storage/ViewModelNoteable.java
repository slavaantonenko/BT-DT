package com.msapplications.btdt.room_storage;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.msapplications.btdt.objects.itemTypes.NoteItem;
import com.msapplications.btdt.room_storage.note.NoteItemRepository;

import java.util.List;

public interface ViewModelNoteable {
    public LiveData<List<NoteItem>> getNoteItems(int categoryID);

    public void insert(NoteItem noteItem);

    public void delete(int itemID);

    public void updateText(int id, String newText);

    public void increaseLineNumbers(int minLine, int categoryID);
    public void decreaseLineNumbers(int minLine, int categoryID);
    public void updateBold(int id, boolean isBold);

    public void updateItalic(int id, boolean isItalic);

    public void updateCheckBox(int id, boolean isCheckBox);

    public void updateChecked(int id, boolean isChecked);
}
