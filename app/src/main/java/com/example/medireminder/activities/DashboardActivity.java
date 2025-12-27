package com.example.medireminder.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medireminder.R;
import com.example.medireminder.adapters.MedicineAdapter;
import com.example.medireminder.constants.AppConstants;
import com.example.medireminder.database.MedicineDAO;
import com.example.medireminder.models.Medicine;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private RecyclerView medicinesRecyclerView;
    private TextView welcomeText, emptyStateText;
    private MedicineAdapter adapter;
    private MedicineDAO medicineDAO;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE);
        userId = prefs.getInt(AppConstants.PREF_USER_ID, -1);
        String username = prefs.getString(AppConstants.PREF_USERNAME, "");

        if (userId == -1) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        medicineDAO = new MedicineDAO(this);
        medicineDAO.open();

        initViews();
        setupRecyclerView();
        loadMedicines();

        welcomeText.setText(getString(R.string.welcome, username));

        FloatingActionButton addButton = findViewById(R.id.addMedicineButton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, AddMedicineActivity.class);
            startActivity(intent);
        });
    }

    private void initViews() {
        medicinesRecyclerView = findViewById(R.id.medicinesRecyclerView);
        welcomeText = findViewById(R.id.welcomeText);
        emptyStateText = findViewById(R.id.emptyStateText);
    }

    private void setupRecyclerView() {
        adapter = new MedicineAdapter(this, medicineDAO, userId);
        medicinesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        medicinesRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMedicines();
    }

    private void loadMedicines() {
        List<Medicine> medicines = medicineDAO.getAllMedicines(userId);
        adapter.updateMedicines(medicines);
        
        if (medicines.isEmpty()) {
            emptyStateText.setVisibility(View.VISIBLE);
            medicinesRecyclerView.setVisibility(View.GONE);
        } else {
            emptyStateText.setVisibility(View.GONE);
            medicinesRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_history) {
            startActivity(new Intent(this, HistoryActivity.class));
            return true;
        } else if (id == R.id.action_logout) {
            SharedPreferences prefs = getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (medicineDAO != null) {
            medicineDAO.close();
        }
    }
}

