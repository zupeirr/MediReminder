package com.example.medireminder.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medireminder.R;
import com.example.medireminder.activities.AddMedicineActivity;
import com.example.medireminder.database.MedicineDAO;
import com.example.medireminder.models.Medicine;

import java.util.ArrayList;
import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {
    private List<Medicine> medicines;
    private MedicineDAO medicineDAO;
    private int userId;

    public MedicineAdapter(android.content.Context context, MedicineDAO medicineDAO, int userId) {
        this.medicines = new ArrayList<>();
        this.medicineDAO = medicineDAO;
        this.userId = userId;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medicine, parent, false);
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        Medicine medicine = medicines.get(position);
        holder.bind(medicine);
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public void updateMedicines(List<Medicine> newMedicines) {
        this.medicines = newMedicines;
        notifyDataSetChanged();
    }

    class MedicineViewHolder extends RecyclerView.ViewHolder {
        private TextView medicineNameText, dosageText, timeText;
        private ImageButton editButton;

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            medicineNameText = itemView.findViewById(R.id.medicineNameText);
            dosageText = itemView.findViewById(R.id.dosageText);
            timeText = itemView.findViewById(R.id.timeText);
            editButton = itemView.findViewById(R.id.editButton);

            editButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Medicine medicine = medicines.get(position);
                    Intent intent = new Intent(itemView.getContext(), AddMedicineActivity.class);
                    intent.putExtra("medicineId", medicine.getId());
                    intent.putExtra("userId", userId);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        public void bind(Medicine medicine) {
            medicineNameText.setText(medicine.getName());
            dosageText.setText(medicine.getDosage());
            timeText.setText(String.format("Time: %s | Frequency: %s", medicine.getTime(), medicine.getFrequency()));
        }
    }
}

