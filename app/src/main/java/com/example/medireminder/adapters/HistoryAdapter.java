package com.example.medireminder.adapters;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medireminder.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private Cursor cursor;

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        if (cursor != null && cursor.moveToPosition(position)) {
            holder.bind(cursor);
        }
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    public void updateCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        notifyDataSetChanged();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView medicineNameText, dosageText, dateTimeText;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            medicineNameText = itemView.findViewById(R.id.medicineNameText);
            dosageText = itemView.findViewById(R.id.dosageText);
            dateTimeText = itemView.findViewById(R.id.dateTimeText);
        }

        public void bind(Cursor cursor) {
            String medicineName = cursor.getString(cursor.getColumnIndexOrThrow("medicineName"));
            String dosage = cursor.getString(cursor.getColumnIndexOrThrow("dosage"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("takenDate"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("takenTime"));

            medicineNameText.setText(medicineName);
            dosageText.setText(dosage);
            dateTimeText.setText(String.format("%s at %s", date, time));
        }
    }
}

