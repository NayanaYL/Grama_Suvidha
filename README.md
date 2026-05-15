# Grama-Suvidha Portal (GRAMA)

**Grama-Suvidha Portal** is a digital platform designed to improve transparency and communication between village administrations and their residents.

## 📌 Problem Statement
In many rural areas, residents lack a reliable way to track the progress of local infrastructure projects (like road repairs or water works). This lack of transparency leads to misinformation and a slow feedback loop. Administrative tasks are often handled manually, making it difficult to keep the public updated in real-time.

This project solves this by providing a **Village Digital Notice Board** where every project's status, budget, and progress are visible to everyone, alongside a direct channel for citizen feedback.

## 🚀 Features

### For Residents
*   **Village Digital Notice Board**: A live dashboard showing **Ongoing**, **Completed**, and **Planned** projects.
*   **Detailed Project Tracking**: View specific details for every work item, including budget splits, completion percentages, and timelines.
*   **Bilingual Interface**: Seamlessly switch between **English** and **Kannada** to ensure all community members can use the app.
*   **Feedback & Issue Reporting**: A dedicated section to rate works and report local issues directly to authorities.

### For Administrators
*   **Admin Authentication**: Secure login for authorized personnel.
*   **Project Management**: Add new village projects or update existing ones (change status, edit progress, modify descriptions).
*   **Centralized Data**: A single source of truth for all village infrastructure development.

## 🛠 Tech Stack
*   **Language**: Kotlin
*   **Architecture**: MVVM (Model-View-ViewModel)
*   **Database**: Room Persistence Library (SQLite)
*   **Navigation**: Jetpack Navigation Component
*   **UI Components**: Material Design 3, View Binding
*   **Image Loading**: Coil

## 📸 Screenshots (Placeholders)
| Project List | Project Detail | Admin Dashboard |
| :---: | :---: | :---: |
| ![List View](https://via.placeholder.com/200x400?text=Project+List) | ![Detail View](https://via.placeholder.com/200x400?text=Project+Detail) | ![Admin Dashboard](https://via.placeholder.com/200x400?text=Admin+Dashboard) |

## 📦 Installation & Setup

### Prerequisites
*   **Android Studio** (Ladybug or newer).
*   **JDK 17**.
*   **Android SDK 24+**.

### Steps to Setup
1.  **Clone the Repository**:
    ```bash
    git clone https://github.com/Navya/GRAMA.git
    ```
2.  **Open the Project**:
    Open Android Studio -> **File** -> **Open** -> Select the `GRAMA` folder.
3.  **Sync Gradle**:
    Android Studio will automatically prompt to sync. Click **Sync Now** and wait for it to complete.
4.  **Run the Project**:
    *   Connect your Android device or start an emulator.
    *   Click the **Run** (Green Arrow) button in the top toolbar.
    *   Or use terminal: `./gradlew installDebug`

### Default Admin Credentials
For testing purposes, use the following credentials to access the admin features:
*   **Username**: `admin`
*   **Password**: `admin123`

## 📁 Folder Structure
```text
GRAMA/
├── app/
│   ├── src/main/java/com/gramasuvidha/portal/
│   │   ├── data/           # Room DB, Entities, Repositories
│   │   ├── ui/             # UI Fragments and ViewModels
│   │   └── util/           # Helpers (Localization, etc.)
│   ├── src/main/res/       # Layouts, Strings (EN/KN), Drawables
│   └── build.gradle.kts    # Module config
├── build.gradle.kts        # Project config
└── README.md               # Documentation
```

## 🛠 Future Improvements
*   **Push Notifications**: Notify residents when a new project is planned or a milestone is reached.
*   **GPS Reporting**: Attach geo-locations to reported issues for faster resolution.
*   **Cloud Sync**: Move from local Room storage to a Firebase/Realtime backend.
*   **Multimedia**: Support for residents to upload photos of issues.

## 📄 License
This project is licensed under the MIT License.
