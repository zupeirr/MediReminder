package com.example.medireminder.constants;

public class AppConstants {
    // Database
    public static final String DATABASE_NAME = "MediReminderDB";
    public static final int DATABASE_VERSION = 1;
    
    // Table Names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_MEDICINES = "medicines";
    public static final String TABLE_HISTORY = "history";
    
    // Shared Preferences
    public static final String PREF_NAME = "MediReminderPrefs";
    public static final String PREF_USER_ID = "userId";
    public static final String PREF_USERNAME = "username";
    public static final String PREF_IS_LOGGED_IN = "isLoggedIn";
    
    // Notification
    public static final String NOTIFICATION_CHANNEL_ID = "medicine_reminder_channel";
    public static final String NOTIFICATION_CHANNEL_NAME = "Medicine Reminders";
    public static final int NOTIFICATION_ID_BASE = 1000;
}

