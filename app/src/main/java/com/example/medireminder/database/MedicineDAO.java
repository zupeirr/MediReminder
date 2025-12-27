package com.example.medireminder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.medireminder.constants.AppConstants;
import com.example.medireminder.models.Medicine;

import java.util.ArrayList;
import java.util.List;

public class MedicineDAO {
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public MedicineDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertMedicine(Medicine medicine) {
        ContentValues values = new ContentValues();
        values.put("name", medicine.getName());
        values.put("dosage", medicine.getDosage());
        values.put("frequency", medicine.getFrequency());
        values.put("time", medicine.getTime());
        values.put("startDate", medicine.getStartDate());
        values.put("endDate", medicine.getEndDate());
        values.put("isActive", medicine.isActive() ? 1 : 0);
        values.put("userId", medicine.getUserId());

        return database.insert(AppConstants.TABLE_MEDICINES, null, values);
    }

    public int updateMedicine(Medicine medicine) {
        ContentValues values = new ContentValues();
        values.put("name", medicine.getName());
        values.put("dosage", medicine.getDosage());
        values.put("frequency", medicine.getFrequency());
        values.put("time", medicine.getTime());
        values.put("startDate", medicine.getStartDate());
        values.put("endDate", medicine.getEndDate());
        values.put("isActive", medicine.isActive() ? 1 : 0);

        return database.update(AppConstants.TABLE_MEDICINES, values,
                "id = ?", new String[]{String.valueOf(medicine.getId())});
    }

    public void deleteMedicine(int medicineId) {
        database.delete(AppConstants.TABLE_MEDICINES, "id = ?",
                new String[]{String.valueOf(medicineId)});
    }

    public List<Medicine> getAllMedicines(int userId) {
        List<Medicine> medicines = new ArrayList<>();
        Cursor cursor = database.query(AppConstants.TABLE_MEDICINES,
                null, "userId = ? AND isActive = 1", new String[]{String.valueOf(userId)},
                null, null, "time ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Medicine medicine = cursorToMedicine(cursor);
                medicines.add(medicine);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return medicines;
    }

    public Medicine getMedicineById(int medicineId) {
        Cursor cursor = database.query(AppConstants.TABLE_MEDICINES,
                null, "id = ?", new String[]{String.valueOf(medicineId)},
                null, null, null);

        Medicine medicine = null;
        if (cursor != null && cursor.moveToFirst()) {
            medicine = cursorToMedicine(cursor);
            cursor.close();
        }

        return medicine;
    }

    private Medicine cursorToMedicine(Cursor cursor) {
        Medicine medicine = new Medicine();
        medicine.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        medicine.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        medicine.setDosage(cursor.getString(cursor.getColumnIndexOrThrow("dosage")));
        medicine.setFrequency(cursor.getString(cursor.getColumnIndexOrThrow("frequency")));
        medicine.setTime(cursor.getString(cursor.getColumnIndexOrThrow("time")));
        medicine.setStartDate(cursor.getString(cursor.getColumnIndexOrThrow("startDate")));
        medicine.setEndDate(cursor.getString(cursor.getColumnIndexOrThrow("endDate")));
        medicine.setActive(cursor.getInt(cursor.getColumnIndexOrThrow("isActive")) == 1);
        medicine.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow("userId")));
        return medicine;
    }

    public void addHistory(int medicineId, String medicineName, String dosage, String date, String time) {
        ContentValues values = new ContentValues();
        values.put("medicineId", medicineId);
        values.put("medicineName", medicineName);
        values.put("dosage", dosage);
        values.put("takenDate", date);
        values.put("takenTime", time);

        database.insert(AppConstants.TABLE_HISTORY, null, values);
    }

    public Cursor getHistory(int userId) {
        return database.rawQuery(
                "SELECT h.* FROM " + AppConstants.TABLE_HISTORY + " h " +
                "INNER JOIN " + AppConstants.TABLE_MEDICINES + " m ON h.medicineId = m.id " +
                "WHERE m.userId = ? ORDER BY h.takenDate DESC, h.takenTime DESC",
                new String[]{String.valueOf(userId)}
        );
    }
}

