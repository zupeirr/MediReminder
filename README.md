# MediReminder - Android Medication Reminder App

MediReminder is a comprehensive Android application designed to help users manage their medication schedules effectively. The app provides reminders, tracks medication history, and ensures users never miss a dose.

## 📱 Features

- **User Authentication**: Secure login and signup system
- **Medicine Management**: Add, edit, and delete medications
- **Smart Reminders**: Schedule notifications for medication times
- **History Tracking**: View complete medication history
- **User-Friendly Interface**: Modern Material Design UI
- **Offline Support**: Works without internet connection using SQLite database

## 🏗️ Project Structure

```
MediReminder/
│
├── app/
│ ├── src/
│ │ ├── main/
│ │ │ ├── java/
│ │ │ │ └── com.example.medireminder/
│ │ │ │ ├── activities/          # All activity classes (screens)
│ │ │ │ │ ├── LoginActivity.java
│ │ │ │ │ ├── SignupActivity.java
│ │ │ │ │ ├── DashboardActivity.java
│ │ │ │ │ ├── AddMedicineActivity.java
│ │ │ │ │ └── HistoryActivity.java
│ │ │ │ │
│ │ │ │ ├── adapters/            # RecyclerView adapters
│ │ │ │ │ ├── MedicineAdapter.java
│ │ │ │ │ └── HistoryAdapter.java
│ │ │ │ │
│ │ │ │ ├── models/              # Data models
│ │ │ │ │ ├── User.java
│ │ │ │ │ └── Medicine.java
│ │ │ │ │
│ │ │ │ ├── database/            # Database classes
│ │ │ │ │ ├── DBHelper.java
│ │ │ │ │ └── MedicineDAO.java
│ │ │ │ │
│ │ │ │ ├── utils/               # Helper classes
│ │ │ │ │ ├── NotificationHelper.java
│ │ │ │ │ ├── NotificationReceiver.java
│ │ │ │ │ └── TimeUtils.java
│ │ │ │ │
│ │ │ │ └── constants/           # Constant values
│ │ │ │   └── AppConstants.java
│ │ │ │
│ │ │ ├── res/
│ │ │ │ ├── layout/              # XML layout files
│ │ │ │ │ ├── activity_login.xml
│ │ │ │ │ ├── activity_signup.xml
│ │ │ │ │ ├── activity_dashboard.xml
│ │ │ │ │ ├── activity_add_medicine.xml
│ │ │ │ │ ├── activity_history.xml
│ │ │ │ │ ├── item_medicine.xml
│ │ │ │ │ └── item_history.xml
│ │ │ │ │
│ │ │ │ ├── drawable/            # Images, icons
│ │ │ │ ├── values/              # Strings, colors, styles
│ │ │ │ │ ├── strings.xml
│ │ │ │ │ ├── colors.xml
│ │ │ │ │ └── styles.xml
│ │ │ │ │
│ │ │ │ └── mipmap/              # App icons
│ │ │ │
│ │ │ ├── AndroidManifest.xml    # App configuration
│ │ │ └── build.gradle
│ │ │
│ │ └── test/                     # Unit tests
│ │
│ └── build.gradle
│
└── build.gradle
```

## 🛠️ Technologies Used

- **Language**: Java
- **Platform**: Android (API 24+)
- **Database**: SQLite
- **UI Framework**: Material Design Components
- **Architecture**: MVC (Model-View-Controller)
- **Build Tool**: Gradle

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Android Studio** (Arctic Fox or later recommended)
- **Java Development Kit (JDK)** 8 or higher
- **Android SDK** (API 24 - Android 7.0 or higher)
- **Android Device** or **Emulator** for testing

## 🚀 Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/zupeirr/MediReminder.git
   cd MediReminder
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select `File` → `Open`
   - Navigate to the cloned project directory
   - Click `OK`

3. **Sync Gradle**
   - Android Studio will automatically sync Gradle dependencies
   - If not, click `Sync Now` when prompted

4. **Configure SDK**
   - Ensure Android SDK is properly configured
   - Go to `File` → `Project Structure` → `SDK Location`
   - Verify SDK path is set correctly

## ▶️ How to Run

### Using Android Studio

1. **Connect Device or Start Emulator**
   - Connect an Android device via USB with USB debugging enabled, OR
   - Start an Android Virtual Device (AVD) from the AVD Manager

2. **Run the App**
   - Click the green `Run` button (▶️) in the toolbar, OR
   - Press `Shift + F10` (Windows/Linux) or `Ctrl + R` (Mac)
   - Select your target device/emulator
   - Wait for the app to build and install

### Using Command Line

```bash
# Navigate to project directory
cd MediReminder

# Build the project
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug

# Run on device
adb shell am start -n com.example.medireminder/.activities.LoginActivity
```

## 📖 Usage Guide

### Getting Started

1. **Sign Up**: Create a new account with username, email, and password
2. **Login**: Use your credentials to access the app
3. **Add Medicine**: Click the `+` button to add a new medication
4. **Set Reminders**: Configure medicine name, dosage, frequency, and time
5. **View History**: Check your medication history from the menu

### Key Features

- **Dashboard**: View all your active medications
- **Add/Edit Medicine**: Manage your medication list
- **Notifications**: Receive timely reminders for medication
- **History**: Track when medications were taken

## 🔐 Permissions

The app requires the following permissions:

- `POST_NOTIFICATIONS`: To send medication reminders
- `SCHEDULE_EXACT_ALARM`: To schedule precise medication reminders
- `USE_EXACT_ALARM`: To use exact alarm scheduling
- `WAKE_LOCK`: To wake device for notifications

These permissions are automatically requested when needed.

## 🗄️ Database

The app uses SQLite database to store:
- User accounts
- Medicine information
- Medication history

Database file location: `/data/data/com.example.medireminder/databases/MediReminderDB`

## 🧪 Testing

Run unit tests:
```bash
./gradlew test
```

Run instrumented tests:
```bash
./gradlew connectedAndroidTest
```

## 📝 Code Structure

### Activities
- **LoginActivity**: Handles user authentication
- **SignupActivity**: New user registration
- **DashboardActivity**: Main screen displaying all medicines
- **AddMedicineActivity**: Add or edit medicine details
- **HistoryActivity**: View medication history

### Database
- **DBHelper**: SQLite database helper class
- **MedicineDAO**: Data Access Object for medicine operations

### Models
- **User**: User data model
- **Medicine**: Medicine data model

### Utils
- **NotificationHelper**: Manages medication reminders
- **TimeUtils**: Date and time utility functions
- **AppConstants**: Application-wide constants

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👤 Author

**zupeirr**
- GitHub: [@zupeirr](https://github.com/zupeirr)

## 🙏 Acknowledgments

- Material Design Components for UI components
- Android Developer Documentation
- SQLite for local database storage

## 📞 Support

If you encounter any issues or have questions, please:
- Open an issue on GitHub
- Check existing issues for solutions
- Review the Android documentation

## 🔄 Version History

- **v1.0.0** (Current)
  - Initial release
  - User authentication
  - Medicine management
  - Notification system
  - History tracking

---

**Note**: This app is for educational and personal use. Always consult with healthcare professionals regarding medication schedules.

