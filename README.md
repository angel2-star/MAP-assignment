#  Valentine's Garage

A comprehensive vehicle management application built with Kotlin, featuring real-time tracking, collaborative task management, and detailed reporting capabilities.

---

##  Overview

Valentine's Garage is a mobile-first application designed for vehicle fleet management and maintenance tracking. It enables users to check in vehicles, capture mileage data, manage collaborative maintenance tasks, and generate comprehensive reports—all in one intuitive platform.

**Key Capabilities:**
- Vehicle check-in and status tracking
- Kilometer/mileage capture with historical data
- Collaborative task assignment and completion ticking
- Real-time reporting and analytics
- User-friendly navigation and responsive UI

---

## Features

### 1. Vehicle Check-In System
Seamlessly check in vehicles with automatic timestamp and status logging. Track vehicle availability and location in real-time.

### 2. Mileage Management
- Capture current odometer readings
- Store historical mileage data
- Track vehicle usage patterns
- Generate mileage reports

### 3. Collaborative Task Management
- Assign maintenance tasks to team members
- Real-time task status updates
- Checkbox-based task completion tracking
- Team visibility on all ongoing work

### 4. Advanced Reporting
- Generate comprehensive vehicle reports
- Analyze usage statistics
- Track maintenance history
- Export data for external processing

---

## Architecture

Valentine's Garage follows a **Layered Architecture** pattern with clear separation of concerns:

```
┌─────────────────────────────────────────────────────────────┐
│                     UI Layer (Presentation)                 │
│  • Activities & Fragments  • View Models  • Navigation      │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                 Business Logic Layer (Domain)               │
│  • Use Cases  • Repositories  • Entities  • Validators      │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                    Data Layer (Backend)                      │
│  • Local Database (Room)  • API Services  • Data Models     │
└─────────────────────────────────────────────────────────────┘
```

### Design Principles

Separation of Concerns
- **UI Layer**: Handles all user interactions and visual presentation
- **Business Logic Layer**: Contains core application logic and rules
- **Data Layer**: Manages all data persistence and retrieval

Modularity
- Feature-based module organization
- Each module has distinct responsibilities
- Easy to test and maintain

No God Objects
- Single Responsibility Principle enforced
- No class exceeds 300 lines
- Clear delegation of responsibilities

Resource Files
- Strings, colors, and dimensions externalized to resources
- Configuration files for environment-specific settings
- Localization support through resource qualifiers

---

## Project Structure

```
ValentinesGarage/
├── src/
│   └── main/
│       ├── kotlin/
│       │   └── com/valentines/garage/
│       │       ├── ui/                    # UI Layer
│       │       │   ├── activities/        # Activities
│       │       │   ├── fragments/         # Fragments
│       │       │   ├── viewmodels/        # View Models
│       │       │   └── adapters/          # RecyclerView Adapters
│       │       ├── domain/                # Business Logic Layer
│       │       │   ├─�� models/            # Domain Entities
│       │       │   ├── repositories/      # Repository Interfaces
│       │       │   ├── usecases/          # Use Cases
│       │       │   └── validators/        # Business Rules
│       │       ├── data/                  # Data Layer
│       │       │   ├── local/             # Room Database
│       │       │   ├── remote/            # API Services
│       │       │   ├── models/            # Data Models
│       │       │   └── repositories/      # Repository Implementations
│       │       └── utils/                 # Utilities & Helpers
│       └── res/
│           ├── values/
│           │   ├── strings.xml            # UI Strings
│           │   ├── colors.xml             # Color Palette
│           │   ├── dimens.xml             # Dimensions
│           │   └── styles.xml             # Theme Styles
│           ├── layouts/                   # XML Layouts
│           ├── drawable/                  # Vector Drawables
│           └── menu/                      # Menu Resources
├── tests/                                 # Unit Tests
├── androidTests/                          # Instrumented Tests
└── build.gradle.kts                       # Build Configuration

```








