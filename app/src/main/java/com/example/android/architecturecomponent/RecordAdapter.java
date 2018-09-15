package com.example.android.architecturecomponent;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.architecturecomponent.database.RecordEntity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Delight on 09/09/2018.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";

    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;
    // Class variables for the List that holds task data and the Context
    private List<RecordEntity> mRecordEntries;
    private Context mContext;
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    public RecordAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recycler_view_item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Determine the values of the wanted data
        RecordEntity recordEntity = mRecordEntries.get(position);
        String designation = recordEntity.getDesignation();
        int level = recordEntity.getLevel();
        String name = recordEntity.getName();
        String updatedAt = dateFormat.format(recordEntity.getUpdatedAt());

        //Set values
        holder.nameView.setText(name);
        holder.dateView.setText(updatedAt);
        holder.designationView.setText(designation);

        // Programmatically set thn text and color for the priority TextView
//        String levelString = "" + level; // converts int to String
//        holder.levelView.setText(levelString);

        holder.levelView.setText(getLvvel(level));
    }

    private String getLvvel(int priority) {
        String level_note = "";

        switch (priority) {
            case 1:
                level_note = "Beginner";
                break;
            case 2:

                level_note = "Intermediate";

                break;
            case 3:

                level_note = "Advanced";
                break;
            default:
                break;
        }
        return level_note;
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }
    @Override
    public int getItemCount() {
        if (mRecordEntries == null) {
            return 0;
        }
        return mRecordEntries.size();
    }

    public List<RecordEntity> getmRecordEntries() {
        return mRecordEntries;
    }

    public void setmRecordEntries(List<RecordEntity> taskEntries) {
        mRecordEntries = taskEntries;
        notifyDataSetChanged();
    }

    // Inner class for creating ViewHolders
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Class variables for the task description and priority TextViews
        TextView designationView;
        TextView nameView;
        TextView dateView;
        TextView levelView;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public ViewHolder(View itemView) {
            super(itemView);
            designationView = itemView.findViewById(R.id.designation);
            nameView = itemView.findViewById(R.id.name);
            dateView = itemView.findViewById(R.id.date);
            levelView = itemView.findViewById(R.id.level);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mRecordEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}
