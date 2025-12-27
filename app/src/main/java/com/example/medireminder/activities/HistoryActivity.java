package com.example.medireminder.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medireminder.R;
import com.example.medireminder.adapters.HistoryAdapter;
import com.example.medireminder.constants.AppConstants;
import com.example.medireminder.database.MedicineDAO;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView historyRecyclerView;
    private TextView emptyStateText;
    private HistoryAdapter adapter;
    private MedicineDAO medicineDAO;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.history_title);
        }

        userId = getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE)
                .getInt(AppConstants.PREF_USER_ID, -1);

        medicineDAO = new MedicineDAO(this);
        medicineDAO.open();

        initViews();
        setupRecyclerView();
        loadHistory();
    }

    private void initViews() {
        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        emptyStateText = findViewById(R.id.emptyStateText);
    }

    private void setupRecyclerView() {
        adapter = new HistoryAdapter(this);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyRecyclerView.setAdapter(adapter);
    }

    private void loadHistory() {
        Cursor cursor = medicineDAO.getHistory(userId);
        adapter.updateCursor(cursor);

        if (cursor == null || cursor.getCount() == 0) {
            emptyStateText.setVisibility(View.VISIBLE);
            historyRecyclerView.setVisibility(View.GONE);
        } else {
            emptyStateText.setVisibility(View.GONE);
            historyRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (medicineDAO != null) {
            medicineDAO.close();
        }
    }
}

