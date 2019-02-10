package com.msapplications.btdt;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Utils
{
    public static void centerTitle(Activity activity)
    {
        ArrayList<View> textViews = new ArrayList<>();

        activity.getWindow().getDecorView().findViewsWithText(textViews, activity.getTitle(), View.FIND_VIEWS_WITH_TEXT);

        if (textViews.size() > 0)
        {
            AppCompatTextView appCompatTextView = null;

            if (textViews.size() == 1)
                appCompatTextView = (AppCompatTextView) textViews.get(0);
            else
            {
                for (View v : textViews)
                {
                    if (v.getParent() instanceof Toolbar)
                    {
                        appCompatTextView = (AppCompatTextView) v;
                        break;
                    }
                }
            }

            if (appCompatTextView != null)
            {
                ViewGroup.LayoutParams params = appCompatTextView.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                appCompatTextView.setLayoutParams(params);
                appCompatTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
        }
    }

    /**
     * Saves array list to cache.
     * @param cacheDir emulator cache directory.
     * @param arrayList array list with the data to save.
     * @param key key name to save under.
     * @return
     */
    public static boolean saveListToCache(File cacheDir, ArrayList<?> arrayList, String key)
    {
        final File suspend_f = new File(cacheDir, key);

        FileOutputStream fos  = null;
        ObjectOutputStream oos  = null;
        boolean keep = true;

        try
        {
            fos = new FileOutputStream(suspend_f);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(arrayList);
        }
        catch (Exception e) {
            keep = false;
        }
        finally
        {
            try
            {
                if (oos != null)   oos.close();
                if (fos != null)   fos.close();
                if (!keep) suspend_f.delete();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return keep;
    }


    /**
     * Gets receipts list from cache.
     * @param cacheDir emulator cache directory.
     * @param key key name to save under.
     * @return
     */
    public static ArrayList<?> getListFromCache(File cacheDir, String key)
    {
        final File suspend_f = new File(cacheDir, key);

        ArrayList<?> arrayList = null;
        FileInputStream fis = null;
        ObjectInputStream is = null;

        try
        {
            fis = new FileInputStream(suspend_f);
            is = new ObjectInputStream(fis);
            arrayList = (ArrayList<?>) is.readObject();
        }
        catch (FileNotFoundException e) {
            return null;
        }
        catch(Exception e) {
            e.getMessage();
        }
        finally
        {
            try
            {
                if (fis != null)   fis.close();
                if (is != null)   is.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return arrayList;
    }

    /**
     * Checks if app has access to necessary service on the device and if not request access from the user.
     * @param activity
     */
    public static void requestNecessaryPermissions(Activity activity, String[] PERMISSIONS)
    {
        boolean grant = false;

        for (String permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                grant = true;
                break;
            }
        }

        // We don't have permission so prompt the user
        if (grant)
            ActivityCompat.requestPermissions(activity, PERMISSIONS, 1);
    }

    public static String loadJSONFromAsset(Context context, String fileName)
    {
        String json = null;

        try
        {
            InputStream is = context.getAssets().open(fileName.replace(".json", ""));
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    public static int calculateNoOfColumns(Context context)
    {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 100);
        return noOfColumns;
    }

    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration
    {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        }
    }

    public static class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int mItemOffset;

        public ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
            this(context.getResources().getDimensionPixelSize(itemOffsetId));
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }
}
