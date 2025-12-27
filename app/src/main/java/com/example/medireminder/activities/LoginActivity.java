package com.example.medireminder.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medireminder.R;
import com.example.medireminder.constants.AppConstants;
import com.example.medireminder.database.DBHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText usernameEditText, passwordEditText;
    private TextInputLayout usernameLayout, passwordLayout;
    private MaterialButton loginButton;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DBHelper(this);
        initViews();
        checkIfLoggedIn();

        loginButton.setOnClickListener(v -> attemptLogin());
        findViewById(R.id.signupLink).setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });
    }

    private void initViews() {
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        usernameLayout = findViewById(R.id.usernameLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        loginButton = findViewById(R.id.loginButton);
    }

    private void checkIfLoggedIn() {
        SharedPreferences prefs = getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE);
        if (prefs.getBoolean(AppConstants.PREF_IS_LOGGED_IN, false)) {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        }
    }

    private void attemptLogin() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            usernameLayout.setError(getString(R.string.empty_fields));
            return;
        }
        usernameLayout.setError(null);

        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError(getString(R.string.empty_fields));
            return;
        }
        passwordLayout.setError(null);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(AppConstants.TABLE_USERS,
                new String[]{"id", "username"},
                "username = ? AND password = ?",
                new String[]{username, password},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String dbUsername = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            cursor.close();

            SharedPreferences prefs = getSharedPreferences(AppConstants.PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(AppConstants.PREF_USER_ID, userId);
            editor.putString(AppConstants.PREF_USERNAME, dbUsername);
            editor.putBoolean(AppConstants.PREF_IS_LOGGED_IN, true);
            editor.apply();

            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            finish();
        } else {
            if (cursor != null) cursor.close();
            Toast.makeText(this, R.string.login_error, Toast.LENGTH_SHORT).show();
        }
    }
}

