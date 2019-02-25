package com.msapplications.btdt.room_storage.note;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import com.msapplications.btdt.objects.itemTypes.NoteItem;
import java.util.List;

public class NoteItemViewModel extends AndroidViewModel
{
    private NoteItemRepository repository;
    private LiveData<List<NoteItem>> noteItems;

    public NoteItemViewModel(Application application)
    {
        super(application);
        repository = new NoteItemRepository(application);
    }

    public LiveData<List<NoteItem>> getNoteItems(int categoryID)
    {
        return repository.getNoteItems(categoryID);
    }

    public void insert(NoteItem noteItem) { repository.insertNoteItem(noteItem); }

    public void delete(int itemID) { repository.deleteNoteItemsByCategory(itemID); }

    public void updateText(int id, String newText) { repository.updateText(id, newText); }

    public void increaseLineNumbers(int minLine, int categoryID) { repository.increaseLineNumbers(minLine, categoryID); }

    public void decreaseLineNumbers(int minLine, int categoryID) { repository.decreaseLineNumbers(minLine, categoryID); }

    public void updateBold(int id, boolean isBold) { repository.updateBold(id, isBold); }

    public void updateItalic(int id, boolean isItalic) { repository.updateItalic(id, isItalic); }

    public void updateCheckBox(int id, boolean isCheckBox) { repository.updateCheckBox(id, isCheckBox); }

    public void updateChecked(int id, boolean isChecked) { repository.updateChecked(id, isChecked); }
}