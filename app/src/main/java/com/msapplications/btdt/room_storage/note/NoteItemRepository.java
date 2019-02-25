package com.msapplications.btdt.room_storage.note;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.msapplications.btdt.objects.itemTypes.NoteItem;
import com.msapplications.btdt.room_storage.RoomDatabase;
import java.util.List;

public class NoteItemRepository
{
    private NoteItemDao noteItemDao;
    private LiveData<List<NoteItem>> noteItems;

    NoteItemRepository(Application application)
    {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        noteItemDao = db.noteItemDao();
    }

    public LiveData<List<NoteItem>> getNoteItems(int categoryID) {
        return noteItemDao.getNoteItems(categoryID);
    }

    public void insertNoteItem(NoteItem noteItem) {
        noteItemDao.insert(noteItem);
        //new insertAsyncTask(noteItemDao).execute(noteItem);
    }

//    public void insertNoteItems(Collection<NoteItem> noteItems) {
//        noteItemDao.insertAll(noteItems);
//    }

    public void deleteNoteItemsByCategory(int itemID) {
        noteItemDao.deleteNoteItemsByCategory(itemID);
    }

    public void updateText(int id, String newText) {
        noteItemDao.updateText(newText, id);
    }

    public void increaseLineNumbers(int minLine, int categoryID) {
        noteItemDao.increaseLineNumbers(minLine, categoryID);
    }

    public void decreaseLineNumbers(int minLine, int categoryID) {
        noteItemDao.decreaseLineNumbers(minLine, categoryID);
    }

    public void updateBold(int id, boolean isBold) {
        noteItemDao.updateBold(id, isBold);
    }

    public void updateItalic(int id, boolean isItalic) {
        noteItemDao.updateItalic(id, isItalic);
    }

    public void updateCheckBox(int id, boolean isCheckBox) {
        noteItemDao.updateCheckBox(id, isCheckBox);
    }

    public void updateChecked(int id, boolean isChecked) {
        noteItemDao.updateChecked(id, isChecked);
    }

//    public void deleteAll() {
//        noteItemDao.deleteAll();
//    }

    private static class insertAsyncTask extends AsyncTask<NoteItem, Void, Void>
    {
        private NoteItemDao asyncTaskDao;

        insertAsyncTask(NoteItemDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final NoteItem... noteItems) {
            asyncTaskDao.insert(noteItems[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void>
    {
        private NoteItemDao asyncTaskDao;

        deleteAsyncTask(NoteItemDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Integer... categoryIDs) {
            asyncTaskDao.deleteNoteItemsByCategory(categoryIDs[0]);
            return null;
        }
    }
}
