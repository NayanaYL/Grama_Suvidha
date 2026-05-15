# Product Requirements Document (PRD): Grama-Suvidha Portal

**Project Name:** Grama-Suvidha Portal  
**Version:** 1.0  
**Internship:** MindMatrix Android App Development using Gen AI  
**Developer:** Nayana YL  

---

## 1. Executive Summary
Grama-Suvidha Portal is a mobile-first digital notice board designed for rural governance. Its primary goal is to provide transparency to village residents regarding local infrastructure projects, budget allocations, and real-time development progress.

## 2. Problem Statement
In many rural areas, residents lack access to reliable information about local government works. This leads to:
*   **Information Asymmetry:** Citizens are unaware of which projects are approved or the status of ongoing work.
*   **Lack of Accountability:** No visible public record of budgets or materials used.
*   **Communication Gap:** No direct channel for citizens to report issues or provide feedback on specific infrastructure projects.

## 3. Goals and Objectives
*   **Digital Transparency:** Create a "Digital Notice Board" accessible to every villager.
*   **Offline Accessibility:** Ensure the app works in areas with poor internet connectivity (Offline-First approach).
*   **Language Inclusivity:** Provide a bilingual interface (English and Kannada) to bridge the language barrier.
*   **Engagement:** Enable a direct feedback loop between citizens and local authorities.

## 4. Target Audience
*   **Village Residents:** Citizens who want to track development in their wards and report issues.
*   **Panchayat Officials:** To showcase work progress and maintain transparency with the public.
*   **Social Auditors:** To verify project details against physical progress on the ground.

## 5. Functional Requirements (User Stories)

| ID | User Story | Feature |
| :--- | :--- | :--- |
| **F1** | As a user, I want to see a list of all village projects. | **Project Dashboard** |
| **F2** | As a user, I want to filter projects by status (Ongoing/Completed). | **Filter System** |
| **F3** | As a user, I want to see a budget breakdown of a specific project. | **Financial Detail View** |
| **F4** | As a user, I want to switch between English and Kannada. | **Localization** |
| **F5** | As a user, I want to report a problem with a specific project. | **Issue Reporting** |
| **F6** | As a user, I want to see "Before and After" images of the work. | **Visual Progress Tracking** |

## 6. Technical Specifications

### **Architecture & Tech Stack**
*   **Pattern:** MVVM (Model-View-ViewModel) for clean separation of concerns.
*   **Language:** Kotlin.
*   **Database:** Room Persistence Library (SQLite) for robust offline storage.
*   **UI Components:** Material Design 3, Data Binding, View Binding.
*   **Navigation:** Jetpack Navigation Component.
*   **Image Handling:** Coil for efficient asynchronous image loading.
*   **Asynchrony:** Kotlin Coroutines and StateFlow for reactive UI updates.

### **Data Model (Core Entities)**
*   **ProjectEntity:** ID, Name, Location, Budget, Status, Progress %, Description, Image URLs, Expected Completion Date.
*   **Feedback:** ProjectID, Rating, Issue Description, Citizen Name, Timestamp.

## 7. Non-Functional Requirements
*   **Offline-First:** The app must function and display project data even when no network is detected.
*   **Performance:** UI must be reactive; status updates and filters should reflect instantly.
*   **Usability:** Large, clear fonts and intuitive icons designed for users with varying levels of digital literacy.

## 8. User Flow
1.  **Launch:** App initializes and seeds the local database if it's the first run.
2.  **Home:** User views the project summary cards and overall statistics.
3.  **Interaction:** User toggles "Ongoing/Completed" tabs to filter projects.
4.  **Details:** User clicks a card to see specific budget details, components used, and progress photos.
5.  **Feedback:** User clicks "Report Issue" or "Give Feedback" to submit data, which is saved to the local database.
6.  **Settings:** User uses the menu to toggle between English and Kannada instantly.

## 9. Generative AI Integration
AI tools were utilized during the development lifecycle for:
*   **Code Optimization:** Generating boilerplate code for Room DAOs and Entities.
*   **Debugging:** Analyzing Logcat stack traces to resolve database schema migration issues.
*   **Content Generation:** Accurate translation of administrative and technical terms into Kannada.
*   **UI Prototyping:** Designing modern Material 3 layouts and icons.

## 10. Future Roadmap
*   **GPS Verification:** Geotagging project locations on a Map view for physical verification.
*   **Cloud Sync:** Syncing local feedback with a central government server when internet is available.
*   **Push Notifications:** Alerting citizens when new projects are approved or completed in their ward.

---
**Prepared by:** Navya  
**Date:** January 2025
