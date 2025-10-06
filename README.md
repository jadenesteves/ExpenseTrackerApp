# Expense Tracker App

A comprehensive Android mobile application designed for efficient personal expense management. This application provides users with intuitive tools to track, categorize, and analyze their spending patterns over time.

![Expense Tracker Main Screen](https://github.com/user-attachments/assets/090b5ab8-51b3-4ede-9300-1000a177bfd8)

## 📱 Overview

The Expense Tracker App is a practical, functionality-focused Android application developed as part of a handheld ubiquitous computing project in Spring 2024. The application emphasizes core expense management features with a clean, user-friendly interface that prioritizes usability and performance.

## ✨ Features

### Core Functionality
- **30-Day Expense Overview**: Display all expenses from the last 30 days with total amount calculations
- **Expense Management**: Full CRUD operations (Create, Read, Update, Delete) for expense entries
- **Category Management**: Create, view, and manage expense categories for better organization
- **Smart Search**: Quick expense lookup by name or description
- **Real-time Calculations**: Automatic total expense calculations and balance tracking

### User Experience
- Intuitive navigation with fragment-based architecture
- Material Design components for consistent UI/UX
- Responsive layout optimized for various screen sizes
- Local data persistence for offline functionality

![Add Expense Screen](https://github.com/user-attachments/assets/125c2435-8abf-4299-bd4f-d67b83561224)

## 🛠️ Technical Specifications

### Development Environment
- **Language**: Java
- **Platform**: Android (API Level 24+)
- **IDE**: Android Studio
- **Build System**: Gradle with Kotlin DSL
- **Target SDK**: Android 14 (API Level 34)
- **Minimum SDK**: Android 7.0 (API Level 24)

### Architecture & Dependencies
- **Architecture Pattern**: Fragment-based navigation with Adapter pattern
- **UI Framework**: Material Design Components
- **Core Libraries**:
  - `androidx.appcompat:appcompat:1.6.1`
  - `com.google.android.material:material:1.11.0`
  - `androidx.cardview:cardview:1.0.0`
  - `androidx.constraintlayout:constraintlayout:2.1.4`
  - `androidx.legacy:legacy-support-v4:1.0.0`

### Project Structure
```
app/
├── src/main/java/com/example/expensetrackerapp/
│   ├── Adapters/          # RecyclerView adapters
│   ├── ExpenseTracker/    # Core business logic
│   ├── Fragments/         # UI fragments
│   ├── Models/           # Data models
│   └── MainActivity.java # Main activity
├── res/                  # Resources (layouts, drawables, etc.)
└── AndroidManifest.xml   # App configuration
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 24 or higher
- Java Development Kit (JDK) 8 or later

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/jadenesteves/ExpenseTrackerApp.git
   ```

2. Open the project in Android Studio

3. Sync the project with Gradle files

4. Build and run the application on an Android device or emulator

### Building the APK
```bash
./gradlew assembleDebug
```

## 📋 Usage

1. **Adding Expenses**: Use the "+" button to create new expense entries with amount, category, and description
2. **Viewing Expenses**: Browse your recent expenses in the main dashboard
3. **Managing Categories**: Create and organize expense categories for better tracking
4. **Searching**: Use the search functionality to quickly find specific expenses
5. **Analyzing Spending**: Review your 30-day spending summary and total expenses

## 🧪 Testing

The project includes comprehensive testing setup:
- **Unit Tests**: JUnit 4.13.2
- **Instrumentation Tests**: AndroidX Test Framework
- **UI Tests**: Espresso 3.5.1

Run tests using:
```bash
./gradlew test
./gradlew connectedAndroidTest
```

## 📈 Future Enhancements

- [ ] Export expenses to CSV/PDF
- [ ] Monthly and yearly expense reports
- [ ] Budget setting and alerts
- [ ] Data backup and synchronization
- [ ] Enhanced data visualization with charts
- [ ] Multi-currency support

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

## 📄 License

This project is part of an academic coursework for handheld ubiquitous computing. Please refer to your institution's guidelines for usage and distribution.

## 👨‍💻 Author

**Jaden Esteves** - *Initial Development*

---

*Built with ❤️ for practical expense management*
