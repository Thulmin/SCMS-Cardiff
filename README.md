# Smart Campus Management System (SCMS)

> Repository note: the runnable Java project is located inside the `Smart_Campus_Management_System/` subdirectory of this repository.

## Project Overview

The **Smart Campus Management System (SCMS)** is a desktop-based Java application developed as an academic prototype for **Cardiff Metropolitan University**. Based on the implementation in this repository, the system is designed to support three core campus operations:

- room and facility management
- room booking and booking conflict control
- maintenance request reporting, tracking, and notifications

The application uses a **Java Swing GUI**, a layered package structure, and multiple **object-oriented design principles and design patterns**. It is intended as a demonstration of advanced Java programming concepts rather than a production-ready enterprise platform.

The system includes role-aware behavior for:

- **Administrators**
- **Staff Members**
- **Students**

At startup, the application loads sample users, rooms, bookings, and maintenance requests so the system can be demonstrated immediately.

## Key Features

- User authentication with seeded demo accounts
- Role-based user model for administrators, staff members, and students
- Room catalogue with room type classification and equipment lists
- Room creation through a factory-based approach
- Room booking with validation for:
  - non-existent rooms
  - inactive rooms
  - past dates
  - invalid time ranges
  - overlapping bookings
- Booking cancellation with status tracking
- Maintenance issue reporting with urgency levels
- Maintenance workflow with pending, assigned, and completed states
- Notification delivery using the Observer pattern
- Dashboard summaries for rooms, bookings, users, and maintenance
- Admin-focused analytics for:
  - most booked rooms
  - maintenance urgency breakdown
- JUnit test coverage for core business rules and exception behavior

## System Users / Roles

| Role | Purpose in the System | Observed Responsibilities in Code |
| --- | --- | --- |
| Administrator | Oversees the system | Can log in, view all bookings and maintenance requests, and is authorized to assign and complete maintenance requests. The room management GUI is also designed to expose room administration actions to administrators. |
| Staff Member | Academic or operational staff | Can authenticate, create room bookings, and report maintenance requests. |
| Student | Student user of campus facilities | Can authenticate, create room bookings, and report maintenance requests. |

## Technologies Used

| Category | Technology |
| --- | --- |
| Programming language | Java 23 |
| GUI framework | Java Swing / AWT |
| Build tool | Apache Maven |
| Testing framework | JUnit 5 |
| Data storage | In-memory Java collections |
| Date and time handling | `java.time` API |
| IDE metadata present | IntelliJ IDEA project files |

## Project Structure

```text
SCMS-Cardiff/
├── README.md
└── Smart_Campus_Management_System/
    ├── pom.xml
    ├── .mvn/
    ├── src/
    │   ├── main/
    │   │   ├── java/com/scms/
    │   │   │   ├── Main.java
    │   │   │   ├── exception/
    │   │   │   ├── facade/
    │   │   │   ├── gui/
    │   │   │   ├── manager/
    │   │   │   ├── model/
    │   │   │   └── pattern/
    │   │   └── resources/
    │   ├── out/production/main/          # IDE-generated compiled classes
    │   └── test/java/com/scms/test/
    ├── target/                           # build output
    └── .idea/
```

### Package Summary

| Package | Purpose |
| --- | --- |
| `com.scms.model` | Domain entities and enums such as users, rooms, bookings, maintenance requests, roles, and statuses |
| `com.scms.manager` | Core business logic and in-memory data management |
| `com.scms.facade` | Simplified application-level access to the subsystems |
| `com.scms.gui` | Swing user interface panels and shared UI styling |
| `com.scms.pattern` | Design-pattern-oriented classes such as the notification service and room factory |
| `com.scms.exception` | Custom checked exceptions for domain and authorization errors |
| `com.scms.test` | JUnit tests for booking, maintenance, notifications, and exception handling |

## Main Modules / Functionalities

### 1. Authentication and Session Handling

- User login is handled through `UserManager.authenticate(...)`.
- The active session user is stored in `UserManager`.
- The GUI begins with a login screen and transitions into the main application frame after successful authentication.
- On login, the current user is subscribed to the notification service.

### 2. Room Management

- Rooms are represented by the `Room` class.
- Each room has:
  - an ID
  - a name
  - a capacity
  - a room type
  - a list of equipment
  - an active/inactive state
- Room creation is centralized through `RoomFactory`.
- The `RoomManager` supports adding, retrieving, updating, activating, and deactivating rooms.

### 3. Booking Management

- Bookings are represented by the `Booking` class.
- The `BookingManager` validates all booking requests before confirming them.
- Booking logic includes:
  - room existence checking
  - room status checking
  - future-date enforcement
  - start/end time validation
  - overlap detection against existing active bookings
- Confirmed and cancelled booking states are supported through `BookingStatus`.

### 4. Maintenance Management

- Maintenance issues are represented by `MaintenanceRequest`.
- Users can submit maintenance reports with a selected urgency level.
- Requests move through the following states:
  - `PENDING`
  - `ASSIGNED`
  - `COMPLETED`
- Only administrators are authorized in code to assign or complete maintenance requests.

### 5. Notifications

- Notifications are stored per user in memory.
- The notification subsystem is implemented with `NotificationService` and the `Observer` interface.
- Users receive notifications when:
  - a booking is confirmed
  - a booking is cancelled
  - a maintenance request is submitted, assigned, or completed

### 6. Dashboard and Analytics

- The dashboard shows summary cards for:
  - total rooms
  - active bookings
  - pending maintenance
  - total users
- The dashboard also lists recent bookings and maintenance requests.
- Administrators additionally see:
  - most booked rooms
  - maintenance urgency breakdown

## Object-Oriented Programming Concepts Used

### Classes and Objects

The project is built around domain classes such as `User`, `Room`, `Booking`, `MaintenanceRequest`, and `Notification`. These classes are instantiated throughout the application to represent real campus entities and transactions.

### Encapsulation

Most domain fields are declared `private` and accessed through getters and setters. This is visible across classes such as:

- `User`
- `Room`
- `Booking`
- `MaintenanceRequest`
- `Notification`

This approach protects internal state and centralizes controlled access to data.

### Inheritance

Inheritance is used in the user hierarchy:

- `User` is an abstract base class
- `Administrator extends User`
- `StaffMember extends User`
- `Student extends User`

This allows shared identity and notification behavior to live in one place while role-specific behavior is defined in subclasses.

### Polymorphism

Polymorphism is used through overridden methods such as `getDashboardWelcome()` in each user subclass. The dashboard can call the same method on a `User` reference and receive role-specific output.

The notification system also uses polymorphic behavior through the `Observer.update(...)` contract.

### Abstraction

`User` is declared as an **abstract class**, which captures common features of all user types without allowing generic user instances that do not belong to a real role.

### Interfaces

The `Observer` interface defines a common notification update contract:

```java
void update(String message);
```

`User` implements this interface, allowing users to participate directly in the notification mechanism.

### Design Patterns Used

| Pattern | Implementation in This Project | Purpose |
| --- | --- | --- |
| Singleton | `UserManager` | Ensures a single shared user/session manager instance |
| Factory | `RoomFactory` | Centralizes room creation and default equipment assignment |
| Facade | `CampusManagementFacade` | Provides a simplified entry point for the GUI layer |
| Observer | `NotificationService`, `Observer`, `User` | Delivers event-based notifications to subscribed users |

## Important Classes and Their Responsibilities

| Class | Responsibility |
| --- | --- |
| `Main` | Application entry point, Swing startup, and sample data loading |
| `CampusManagementFacade` | Coordinates managers and exposes a simple API to the GUI |
| `UserManager` | Stores users, authenticates logins, tracks the current user, and prevents duplicate users |
| `RoomManager` | Maintains room records and room activation status |
| `BookingManager` | Creates, validates, cancels, and analyzes room bookings |
| `MaintenanceManager` | Creates maintenance requests, enforces admin-only assignment/completion, and calculates request statistics |
| `NotificationService` | Manages subscribers and delivers notifications |
| `RoomFactory` | Produces room objects with default equipment based on room type |
| `User` | Abstract base class for all system users and notification recipients |
| `Administrator`, `StaffMember`, `Student` | Role-specific user specializations |
| `MainFrame` | Main application window with login flow and sidebar navigation |
| `DashboardPanel` | Displays summaries, recent activity, and admin analytics |
| `RoomManagementPanel` | Displays room records and room management actions |
| `BookingPanel` | Displays bookings and provides booking/cancellation actions |
| `MaintenancePanel` | Displays maintenance requests and provides reporting/workflow actions |
| `NotificationPanel` | Displays user notifications and read/unread state handling |

## Screens / GUI Overview

The project contains a full Swing-based graphical interface. No screenshots are included in the repository, but the implemented screens are:

| Screen / Panel | Purpose |
| --- | --- |
| Login Screen | Authenticates users and shows demo credentials |
| Main Application Frame | Provides the overall window, sidebar navigation, and sign-out flow |
| Dashboard | Shows welcome text, summary statistics, recent bookings, recent maintenance, and admin analytics |
| Room Management | Shows room data in a table and supports filtering; room administration controls are intended for admins |
| Bookings | Lists bookings, creates new bookings, and cancels selected bookings |
| Maintenance | Lists maintenance requests, supports issue reporting, and includes admin assignment/completion actions |
| Notifications | Displays user notifications, unread indicators, and mark-read actions |

## Data Handling / Storage Method

The system currently uses **in-memory storage only**.

- Users are stored in a `Map<String, User>` inside `UserManager`.
- Rooms are stored in a `Map<String, Room>` inside `RoomManager`.
- Bookings are stored in a `Map<String, Booking>` inside `BookingManager`.
- Maintenance requests are stored in a `Map<String, MaintenanceRequest>` inside `MaintenanceManager`.
- Notifications are stored in a `List<Notification>` inside each `User`.

### Implications

- No database is used.
- No file persistence is implemented.
- Data is reset whenever the application restarts.
- The initial state is reconstructed from the sample data loaded in `Main.loadSampleData(...)`.

## Error Handling / Validation

The project includes several custom exceptions and validation rules.

### Custom Exceptions

| Exception | Purpose |
| --- | --- |
| `DuplicateDataException` | Raised when duplicate users or duplicate rooms are added |
| `InvalidBookingException` | Raised for invalid booking attempts and cancellation errors |
| `UnauthorizedActionException` | Raised when a non-administrator attempts restricted maintenance actions |

### Validations Observed in Code

- duplicate user ID detection
- duplicate email detection
- duplicate room ID detection
- login failure handling through `null` return values
- prevention of bookings for inactive or non-existent rooms
- prevention of past-date bookings
- prevention of invalid time ranges
- prevention of overlapping bookings
- prevention of duplicate cancellation
- administrator-only maintenance assignment and completion
- basic GUI validation for empty fields, number parsing, and date parsing

The test suite also documents one known validation gap: a `null` booking date currently leads to a `NullPointerException` instead of a controlled `InvalidBookingException`.

## Required Software / Prerequisites

To run the project as configured in `pom.xml`, the following are recommended:

- **Java Development Kit (JDK) 23**
- **Apache Maven 3.x**
- A desktop environment capable of running Java Swing applications
- An IDE such as **IntelliJ IDEA**, **Eclipse**, or **VS Code** with Java support (optional)

### Important Note

The repository contains a `pom.xml`, but it does **not** currently include Maven wrapper scripts such as `mvnw` or `mvnw.cmd`. Maven therefore needs to be installed separately.

## Setup Instructions

1. Clone or download this repository.
2. Open a terminal in the repository root.
3. Move into the actual Maven project directory:

```bash
cd Smart_Campus_Management_System
```

4. Ensure Java 23 and Maven are available on your machine.

## How to Run the Project

### Option A: Run with Maven

From inside `Smart_Campus_Management_System/`:

```bash
mvn clean package
java -jar target/Smart_Campus_Management_System-1.0-SNAPSHOT.jar
```

### Option B: Compile and Run Manually with `javac`

If Maven is not available, the production source can also be compiled directly because the application itself has no external runtime dependencies beyond the JDK:

```powershell
$files = Get-ChildItem -Recurse -Filter *.java src/main/java | ForEach-Object FullName
javac -d out $files
java -cp out com.scms.Main
```

If you are using a Unix-like shell, adapt the file-discovery command accordingly.

### Running the Test Suite

JUnit tests are defined under `src/test/java/com/scms/test/`. To run them with Maven:

```bash
mvn test
```

## Usage Instructions

When the application starts:

1. Sign in using one of the seeded demo accounts.
2. Use the sidebar to move between dashboard, rooms, bookings, maintenance, and notifications.
3. Create bookings or report maintenance issues through the relevant panels.
4. Review notification updates as actions occur.

### Demo Credentials Shown in the GUI

| Role | Email | Password |
| --- | --- | --- |
| Administrator | `admin@cardiffmet.ac.uk` | `admin123` |
| Staff Member | `j.smith@cardiffmet.ac.uk` | `staff123` |
| Student | `s.jones@cardiffmet.ac.uk` | `student123` |

### Sample Data Loaded at Startup

Based on `Main.loadSampleData(...)`, the application seeds:

- 5 users
- 7 rooms
- 5 example bookings
- 4 maintenance requests

This makes the application immediately usable for demonstration and academic evaluation.

## Limitations

The current codebase appears to be a functional academic prototype, but several limitations are visible from the source:

- Data is stored only in memory and is lost on restart.
- Passwords are stored in plain text in memory, so the security model is intentionally simplified.
- A dedicated GUI screen for full user administration is not implemented, even though backend user-management support exists.
- The maintenance assignment dialog currently lists all users rather than filtering to operational staff only.
- The test suite documents at least one known validation gap for `null` booking dates.
- Some admin-only GUI controls are initialized before login, so that part of the UI may require refinement to appear reliably after authentication.

## Future Improvements

- Add database or file-based persistence
- Add a dedicated user management interface
- Improve role-based access enforcement across all UI actions
- Hash and secure passwords properly
- Add stronger validation for `null` and malformed inputs
- Filter maintenance assignees by appropriate staff roles
- Add reporting, export, and search features
- Include screenshots and user documentation for end users
- Add CI-based automated test execution
- Provide a Maven wrapper for easier setup

## Author / Student Information


- **Student Name:** `Thulmin Jayawardena`
- **Programme:** `MSc in Information Technology`
- **Institution:** `Cardiff Metropolitan University`


## License

No license file is currently included in this repository.

For a public GitHub release, add a suitable `LICENSE` file if your course regulations and ownership rules permit redistribution. For academic submissions, confirm the correct licensing or sharing policy with your module guidelines before choosing a license.
