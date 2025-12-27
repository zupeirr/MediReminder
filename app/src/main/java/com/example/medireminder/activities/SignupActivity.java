package com.example.medireminder.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medireminder.R;
import com.example.medireminder.constants.AppConstants;
import com.example.medireminder.database.DBHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignupActivity extends AppCompatActivity {
    private TextInputEditText usernameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private TextInputLayout usernameLayout, emailLayout, passwordLayout, confirmPasswordLayout;
    private MaterialButton signupButton;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dbHelper = new DBHelper(this);
        initViews();

        signupButton.setOnClickListener(v -> attemptSignup());
        findViewById(R.id.loginLink).setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void initViews() {
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        usernameLayout = findViewById(R.id.usernameLayout);
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        confirmPasswordLayout = findViewById(R.id.confirmPasswordLayout);
        signupButton = findViewById(R.id.signupButton);
    }

    private void attemptSignup() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        boolean hasError = false;

        if (TextUtils.isEmpty(username)) {
            usernameLayout.setError(getString(R.string.empty_fields));
            hasError = true;
        } else {
            usernameLayout.setError(null);
        }

        if (TextUtils.isEmpty(email)) {
            emailLayout.setError(getString(R.string.empty_fields));
            hasError = true;
        } else {
            emailLayout.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError(getString(R.string.empty_fields));
            hasError = true;
        } else {
            passwordLayout.setError(null);
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordLayout.setError(getString(R.string.empty_fields));
            hasError = true;
        } else {
            confirmPasswordLayout.setError(null);
        }

        if (hasError) return;

        if (!password.equals(confirmPassword)) {
            confirmPasswordLayout.setError(getString(R.string.password_mismatch));
            return;
        }
        confirmPasswordLayout.setError(null);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        
        // Check if username or email already exists
        android.database.Cursor cursor = db.query(AppConstants.TABLE_USERS,
                new String[]{"id"},
                "username = ? OR email = ?",
                new String[]{username, email},
                null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            Toast.makeText(this, R.string.user_exists, Toast.LENGTH_SHORT).show();
            return;
        }
        if (cursor != null) cursor.close();

        // Insert new user
        android.content.ContentValues values = new android.content.ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("password", password);

        long result = db.insert(AppConstants.TABLE_USERS, null, values);

        if (result != -1) {
            Toast.makeText(this, R.string.signup_success, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Signup failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}

