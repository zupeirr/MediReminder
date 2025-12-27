package com.example.medireminder.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.medireminder.R;
import com.example.medireminder.constants.AppConstants;
import com.example.medireminder.database.MedicineDAO;
import com.example.medireminder.models.Medicine;
import com.example.medireminder.utils.TimeUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class AddMedicineActivity extends AppCompatActivity {
    private TextInputEditText medicineNameEditText, dosageEditText, timeEditText;
    private TextInputEditText startDateEditText, endDateEditText;
    private AutoCompleteTextView frequencyAutoComplete;
    private MaterialButton saveButton;
    private MedicineDAO medicineDAO;
    private Medicine existingMedicine;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        userId = getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE)
                .getInt(AppConstants.PREF_USER_ID, -1);
        int medicineId = getIntent().getIntExtra("medicineId", -1);

        medicineDAO = new MedicineDAO(this);
        medicineDAO.open();

        if (medicineId != -1) {
            existingMedicine = medicineDAO.getMedicineById(medicineId);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Edit Medicine");
            }
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Add Medicine");
            }
        }

        initViews();
        setupFrequencyDropdown();
        setupDatePickers();
        setupTimePicker();

        if (existingMedicine != null) {
            populateFields();
        }

        saveButton.setOnClickListener(v -> saveMedicine());
    }

    private void initViews() {
        medicineNameEditText = findViewById(R.id.medicineNameEditText);
        dosageEditText = findViewById(R.id.dosageEditText);
        frequencyAutoComplete = findViewById(R.id.frequencyAutoComplete);
        timeEditText = findViewById(R.id.timeEditText);
        startDateEditText = findViewById(R.id.startDateEditText);
        endDateEditText = findViewById(R.id.endDateEditText);
        saveButton = findViewById(R.id.saveButton);
    }

    private void setupFrequencyDropdown() {
        String[] frequencies = {getString(R.string.daily), getString(R.string.weekly), getString(R.string.as_needed)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, frequencies);
        frequencyAutoComplete.setAdapter(adapter);
    }

    private void setupDatePickers() {
        startDateEditText.setOnClickListener(v -> showDatePicker(startDateEditText));
        endDateEditText.setOnClickListener(v -> showDatePicker(endDateEditText));
    }

    private void setupTimePicker() {
        timeEditText.setOnClickListener(v -> showTimePicker());
    }

    private void showDatePicker(TextInputEditText editText) {
        Calendar calendar = Calendar.getInstance();
        if (editText.getText().toString().isEmpty() && editText == startDateEditText) {
            // Default to today for start date
        } else if (!editText.getText().toString().isEmpty()) {
            calendar.setTime(TimeUtils.parseDate(editText.getText().toString()));
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, dayOfMonth);
                    editText.setText(TimeUtils.formatDate(selectedDate.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        if (!timeEditText.getText().toString().isEmpty()) {
            calendar.setTime(TimeUtils.parseTime(timeEditText.getText().toString()));
        }

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    Calendar selectedTime = Calendar.getInstance();
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedTime.set(Calendar.MINUTE, minute);
                    timeEditText.setText(TimeUtils.formatTime(selectedTime.getTime()));
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false);
        timePickerDialog.show();
    }

    private void populateFields() {
        medicineNameEditText.setText(existingMedicine.getName());
        dosageEditText.setText(existingMedicine.getDosage());
        frequencyAutoComplete.setText(existingMedicine.getFrequency(), false);
        timeEditText.setText(existingMedicine.getTime());
        startDateEditText.setText(existingMedicine.getStartDate());
        if (existingMedicine.getEndDate() != null && !existingMedicine.getEndDate().isEmpty()) {
            endDateEditText.setText(existingMedicine.getEndDate());
        }
        saveButton.setText(R.string.update_medicine);
    }

    private void saveMedicine() {
        String name = medicineNameEditText.getText().toString().trim();
        String dosage = dosageEditText.getText().toString().trim();
        String frequency = frequencyAutoComplete.getText().toString().trim();
        String time = timeEditText.getText().toString().trim();
        String startDate = startDateEditText.getText().toString().trim();
        String endDate = endDateEditText.getText().toString().trim();

        if (name.isEmpty() || dosage.isEmpty() || frequency.isEmpty() || time.isEmpty() || startDate.isEmpty()) {
            Toast.makeText(this, R.string.empty_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        if (existingMedicine != null) {
            existingMedicine.setName(name);
            existingMedicine.setDosage(dosage);
            existingMedicine.setFrequency(frequency);
            existingMedicine.setTime(time);
            existingMedicine.setStartDate(startDate);
            existingMedicine.setEndDate(endDate.isEmpty() ? null : endDate);
            medicineDAO.updateMedicine(existingMedicine);
            Toast.makeText(this, "Medicine updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Medicine medicine = new Medicine(name, dosage, frequency, time, startDate, endDate, userId);
            long result = medicineDAO.insertMedicine(medicine);
            if (result != -1) {
                Toast.makeText(this, "Medicine added successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to add medicine", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
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

