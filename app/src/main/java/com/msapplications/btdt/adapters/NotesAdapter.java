//package com.msapplications.btdt.adapters;
//
//import android.content.Intent;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.msapplications.btdt.CommonValues;
//import com.msapplications.btdt.R;
//import com.msapplications.btdt.activities.ListActivity;
//import com.msapplications.btdt.objects.Category;
//import com.msapplications.btdt.objects.itemTypes.NoteItem;
//
//import java.util.ArrayList;
//
//public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>
//{
//    ArrayList<NoteItem> notes;
//
//    public NotesAdapter(ArrayList<NoteItem> notes) {
//        this.notes = notes;
//    }
//
//    public class NotesViewHolder extends RecyclerView.ViewHolder
//    {
//        public TextView etNote;
//
//        public NotesViewHolder(View view)
//        {
//            super(view);
//            etNote = view.findViewById(R.id.rvNotes);
//        }
//    }
//
//    @Override
//    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
//    {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.note_line, parent, false);
//
//        return new NotesViewHolder(itemView);
//
//    }
//
//    @Override
//    public void onBindViewHolder(final NotesViewHolder holder, final int position)
//    {
//        NoteItem note = notes.get(position);
////        holder.etNote.setText(note);
//
////        // loading album cover using Glide library
////        Glide.with(mContext).load(category.getPreviewPic()).into(holder.thumbnail);
////
////        holder.overflow.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                showPopupMenu(holder.overflow);
////            }
////        });
////
////        holder.cardView.setOnClickListener(new View.OnClickListener()
////        {
////            @Override
////            public void onClick(View view)
////            {
////                Intent intent = new Intent(mContext, ListActivity.class);
////                intent.putExtra(CommonValues.CATEGORY_INDEX, position);
////                mContext.startActivity(intent);
////            }
////        });
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return notes.size();
//    }
//}
