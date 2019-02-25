package com.msapplications.btdt;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.msapplications.btdt.adapters.CinemasAdapter;
import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListCallbackCinema extends ItemTouchHelper.SimpleCallback
{
//    private CinemasAdapter adapter;

    public ListCallbackCinema(CinemasAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    // Drag & Drop
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    // Swipe
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((CinemasAdapter.ViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
    {
        View foregroundView = ((CinemasAdapter.ViewHolder) viewHolder).viewForeground;;

        if (dX > 0) { // Swiping to the right
            ((CinemasAdapter.ViewHolder) viewHolder).addSeatViewBackground.setVisibility(View.VISIBLE);
            ((CinemasAdapter.ViewHolder) viewHolder).deleteCinemaViewBackground.setVisibility(View.INVISIBLE);
        }
        else if (dX < 0) { // Swiping to the left
            ((CinemasAdapter.ViewHolder) viewHolder).addSeatViewBackground.setVisibility(View.INVISIBLE);
            ((CinemasAdapter.ViewHolder) viewHolder).deleteCinemaViewBackground.setVisibility(View.VISIBLE);
        }

        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {}
}
