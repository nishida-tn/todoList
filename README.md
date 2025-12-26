# 📝 TodoList App

![Kotlin](https://img.shields.io/badge/kotlin-2.0.0-blue.svg?logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.7.0-4285F4.svg?logo=android&logoColor=white)
![Hilt](https://img.shields.io/badge/DI-Hilt-orange)
![Firebase](https://img.shields.io/badge/Backend-Firebase-FFCA28?logo=firebase&logoColor=black)

> A modern task management application built with Android's best Engineering practices.

<p align="center">
  <img src="docs/screenshots/home.png" width="200" alt="Home Screen" />
  <img src="docs/screenshots/create_task.png" width="200" alt="Create Task" />
</p>

## 💡 About the Project

This project serves as a case study on implementing **Clean Architecture** and **MVI** in a purely declarative environment using **Jetpack Compose**.

Key engineering concepts demonstrated:
* **Complex State Management:** Unidirectional Data Flow (UDF) with immutable states.
* **Reactive UI:** Leveraging Kotlin Flows and Coroutines for asynchronous updates.
* **Scalability:** Decoupled modules using Hilt for Dependency Injection.
* **Modern Navigation:** Type-safe navigation with Navigation Compose.

## 🛠 Tech Stack & Libraries

* **Language:** [Kotlin](https://kotlinlang.org/) (100%)
* **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material Design 3)
* **Architecture:** Clean Architecture (Presentation, Domain, Data) + MVVM
* **Dependency Injection:** [Hilt](https://dagger.dev/hilt/)
* **Navigation:** [Navigation Compose](https://developer.android.com/guide/navigation/navigation-compose)
* **Concurrency:** [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
* **Backend/Auth:** Firebase Authentication & Firestore
* **Build System:** Gradle Kotlin DSL
* **IDE:** Android Studio Narwhal / Ladybug (2024.2.1+)


## 🛠 Environment & Setup

This project leverages modern Android development tools including **Version Catalogs**, **Kotlin DSL**, and **Jetpack Compose**.

### Prerequisites
* **JDK 17+**: Required to run the AGP (Android Gradle Plugin) 8.x, even though `jvmTarget` is set to 11.
* **Android Studio**: Ladybug / Meerkat (Canary) or newer is required to support **Target SDK 36 (Android 16 Preview)**.
* **Android SDK**: Must have API Level 36 (Preview) installed via SDK Manager.

### 🔑 API Keys & Secrets
The project relies on **Firebase Services**.
1. Place your `google-services.json` inside the `app/` module folder.
2. Ensure the package name in Firebase matches `com.thalesnishida.todo`.

### 📦 Build & Run
Since this project uses Gradle Version Catalogs, simply sync and run:

```bash
# Clean and Build Debug APK
./gradlew clean assembleDebug

# Run Unit Tests
./gradlew testDebugUnitTest

# Run Instrumentation Tests (Requires Emulator/Device)
./gradlew connectedDebugAndroidTest
```

## 🏛 Architecture

The app follows the official [Android App Architecture Guide](https://developer.android.com/jetpack/guide), enforcing strict **Separation of Concerns (SoC)**.

```mermaid
graph TD
    UI[UI Layer (Compose)] --> VM[ViewModel]
    VM --> UC[Use Cases (Domain)]
    UC --> Repo[Repository Interface]
    Repo --> Data[Data Layer (Impl)]
    Data --> Remote[Firebase Data Source]