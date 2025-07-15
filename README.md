# On This Day — MVI Android App

This Android application displays notable historical events, births, deaths, and holidays for any
given date using
the [Wikimedia On This Day API](https://api.wikimedia.org/wiki/Feed_API/Reference/On_this_day).

It is built entirely in **Jetpack Compose**, follows the **MVI (Model-View-Intent)** architectural
pattern, and includes **offline caching** using **Room**. The app is designed as a clean, modern
example of scalable Android architecture using Kotlin, coroutines, Retrofit, and Compose.

---

## 🎯 Goal

The goal of this project is to:

- Demonstrate a practical implementation of the **Model-View-Intent (MVI)** pattern in a
  Compose-based Android app
- Consume a public REST API in a clean and testable way
- Handle **state management** predictably and robustly
- Support **offline access** using Room for previously viewed dates
- Showcase modern Android tools and design patterns
- Serve as a self-contained reference for building future MVI apps from scratch

---

## 🧭 What is MVI (Model-View-Intent)?

### Overview

**MVI** is a software architecture pattern that focuses on **unidirectional data flow**, *
*immutability**, and a **single source of truth** for UI state. It encourages thinking in terms of *
*state transitions** and **event-driven updates**.

It is particularly useful with reactive frameworks like Jetpack Compose or RxJava, where the UI
reacts to state changes and user events are modeled as pure inputs.

### Key Concepts

| Component  | Purpose                                                                                                                  |
|------------|--------------------------------------------------------------------------------------------------------------------------|
| **Model**  | Holds the application state (typically in a `data class`) and business logic. It is updated via state transitions.       |
| **View**   | Pure UI that displays the current state. It observes changes to the model and renders them declaratively.                |
| **Intent** | Describes a user interaction or event (e.g. clicking a button, navigating a date). Intents trigger updates to the model. |

### Visual Flow:

```
[User Action]
     ↓
[Intent] ──▶ [ViewModel] ──▶ [State] ──▶ [View]
                             ▲          │
                             └──────────┘
```

The View emits **Intents**, which are handled by the ViewModel. The ViewModel processes them (via
API, DB, logic), updates the **State**, which the **View** observes.

This loop is predictable, testable, and avoids shared mutable state.

---

## 🧱 How MVI is used in this app

This app implements a basic MVI architecture using Kotlin + Jetpack Compose + StateFlow.

| Layer      | Implementation                                                                              |
|------------|---------------------------------------------------------------------------------------------|
| **Intent** | `OnThisDayIntent.kt`: all user-driven actions like navigating dates or selecting an event   |
| **Model**  | `OnThisDayViewModel.kt`: handles logic, fetches data, manages state with `MutableStateFlow` |
| **State**  | `OnThisDayState.kt`: a full snapshot of the UI’s data, error, and selection states          |
| **View**   | `OnThisDayScreen.kt`: stateless UI, observes `StateFlow` and triggers intents               |

### Example:

1. User clicks "Next Day" → emits `Intent.NextDate`
2. ViewModel handles this by updating the selected date and loading events
3. ViewModel updates `State` → triggers recomposition in Compose
4. UI displays the new day's data

---

## 💾 Offline Caching with Room

- **Entity:** `OnThisDayEntity.kt` stores one day's response as JSON
- **DAO:** `OnThisDayDao.kt` queries and inserts entries by date
- **Database:** `AppDatabase.kt` provides the Room setup
- ViewModel checks local cache before hitting the network

This allows the app to work **without an internet connection** for previously viewed days.

---

## 🛠 Tech Stack

- **Kotlin** for language
- **Jetpack Compose** for UI
- **ViewModel + StateFlow** for MVI state management
- **Retrofit + Gson** for API calls and JSON parsing
- **Room** for offline storage
- **ViewModelProvider.Factory** for ViewModel injection (manual DI)
- **Material 3** for modern design

---

## 🚀 Features

- 📅 Shows events, holidays, births, and deaths for today's date
- ⬅️➡️ Navigate to previous or next days
- 📲 Tap events to see details in a dialog
- 💾 Automatically caches each day locally
- 🔌 Works offline if the day was previously visited
- 🧼 Clean UI using modern Compose APIs

---

## 👨‍💻 Project Structure

```
com.albertocamillo.onthisdaymvi
├── data
│   ├── api        # Retrofit API interface and client
│   ├── db         # Room DB, DAO, Entity, converters
│   └── model      # Kotlin data classes matching API structure
│
├── mvi
│   ├── intent     # All possible user actions
│   ├── state      # Full representation of UI state
│   └── viewmodel  # Business logic, data fetching, state updates
│
├── ui
│   └── screen     # Jetpack Compose views and UI rendering
│
└── MainActivity.kt # Entry point: sets up DB, ViewModel factory, and sets Compose content
```

---

## 🧑‍🏫 When to Use MVI (and When Not To)

### ✅ Good for:

- Complex UIs with multiple data sources
- UIs requiring **predictable behaviour** and **clear data flow**
- Apps where **testability** and **debuggability** are priorities

### ❌ Overkill for:

- Extremely simple apps (e.g. single screen, no data)
- Apps that don't need strict separation of logic

That said, this app uses MVI in a **minimal yet real-world useful way** — perfect for medium-sized
apps.

---

## 📝 License

This project is open source and free to use under the MIT License.

---

## 🧠 Pro Tips for Future You

- Don’t construct ViewModels directly in Composables — use `viewModel(factory = ...)`
- Keep `State` as a single immutable snapshot — avoid scattering state across multiple flows
- Remember: **all user actions = Intents**
- The ViewModel is the only place that updates State
- Always render UI purely from State — no side effects in Composables
