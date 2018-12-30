package com.msapplications.btdt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Adapter;

import com.msapplications.btdt.adapters.CinemasAdapter;
import com.msapplications.btdt.fragments.CinemaSeatsFragment;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class SwipeListCallback extends ItemTouchHelper.SimpleCallback
{
//    private Drawable icon;
//    private final ColorDrawable background = null;

//    public SwipeListCallback(int dragDirs, int swipeDirs) {
//        super(dragDirs, swipeDirs);
//        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
//        icon = ContextCompat.getDrawable(adapter., R.drawable.ic_delete);
//        background = new ColorDrawable(Color.RED);
//    }

    public SwipeListCallback() {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((CinemasAdapter.MyViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
    {
        View foregroundView = ((CinemasAdapter.MyViewHolder) viewHolder).viewForeground;;

        if (dX > 0) { // Swiping to the right
            ((CinemasAdapter.MyViewHolder) viewHolder).addSeatViewBackground.setVisibility(View.VISIBLE);
            ((CinemasAdapter.MyViewHolder) viewHolder).deleteCinemaViewBackground.setVisibility(View.INVISIBLE);
        }
        else if (dX < 0) { // Swiping to the left
            ((CinemasAdapter.MyViewHolder) viewHolder).addSeatViewBackground.setVisibility(View.INVISIBLE);
            ((CinemasAdapter.MyViewHolder) viewHolder).deleteCinemaViewBackground.setVisibility(View.VISIBLE);
        }

        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {}
//        @Override
//    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
//    {
//        Context context = recyclerView.getContext();
//        Drawable swipeLeftIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete);
//        Drawable swipeRightIcon = ContextCompat.getDrawable(context, R.drawable.ic_add);
//        ColorDrawable swipeLeftBackground = new ColorDrawable(ContextCompat.getColor(context, R.color.colorAccent));
//        ColorDrawable swipeRightBackground = new ColorDrawable(ContextCompat.getColor(context, android.R.color.holo_blue_light));
//
//        View itemView = viewHolder.itemView;
//        int backgroundCornerOffset = 20; //so swipeLeftBackground is behind the rounded corners of itemView
//
//
//
//        if (dX > 0) // Swiping to the right
//        {
//            int iconMargin = (itemView.getHeight() - swipeRightIcon.getIntrinsicHeight()) / 2;
//            int iconTop = itemView.getTop() + (itemView.getHeight() - swipeRightIcon.getIntrinsicHeight()) / 2;
//            int iconBottom = iconTop + swipeRightIcon.getIntrinsicHeight();
//
//            int iconLeft = itemView.getLeft() + iconMargin + swipeRightIcon.getIntrinsicWidth();
//            int iconRight = itemView.getLeft() + iconMargin;
//            swipeRightIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
//
//            swipeRightBackground.setBounds(itemView.getLeft(), itemView.getTop(),
//                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());
//        }
//        else if (dX < 0) // Swiping to the left
//        {
//            int iconMargin = (itemView.getHeight() - swipeLeftIcon.getIntrinsicHeight()) / 2;
//            int iconTop = itemView.getTop() + (itemView.getHeight() - swipeLeftIcon.getIntrinsicHeight()) / 2;
//            int iconBottom = iconTop + swipeLeftIcon.getIntrinsicHeight();
//
//            int iconLeft = itemView.getRight() - iconMargin - swipeLeftIcon.getIntrinsicWidth();
//            int iconRight = itemView.getRight() - iconMargin;
//            swipeLeftIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
//
//            swipeLeftBackground.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
//                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
//        }
//        else { // view is unSwiped
//            swipeLeftBackground.setBounds(0, 0, 0, 0);
//            swipeRightBackground.setBounds(0,0,0,0);
//        }
//
//        swipeLeftBackground.draw(c);
//        swipeLeftIcon.draw(c);
//        swipeRightBackground.draw(c);
//        swipeRightIcon.draw(c);
//
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//    }
}
