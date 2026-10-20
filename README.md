# Nocturne Cinema — Movie Ticket Booking

Nocturne Cinema is a Java Servlet and JSP movie ticket booking demo. It presents a catalogue of Indian and international films, provides movie details and seat selection, and records bookings for the current visitor session.

## Features

- Home page with a featured film collection
- Catalogue of 16 films with local posters, search, genre and language filters, and sorting
- Movie details pages with synopsis, available metadata, and recommended films
- Showtime and seat selection with server-side validation and price calculation
- Booking confirmation, printable ticket, and session-based booking history
- Responsive dark interface with image fallbacks and reduced-motion support

## Technologies

- Java Servlets (`javax.servlet`, Tomcat 9 compatible)
- JSP, HTML5, CSS3, and vanilla JavaScript
- Java Collections Framework for catalogue and in-memory seat data
- Apache Tomcat 9

The project does not use a database, JDBC, Maven, Gradle, npm, external movie API, or API keys.

## Prerequisites

- JDK 11 or newer (Java 25 is supported)
- Apache Tomcat 9
- VS Code (recommended for editing; its Java Extension Pack is optional)
- Tomcat's `lib/servlet-api.jar`, used only on the Java compilation classpath

## Setup and run on Windows

Open the project folder in VS Code, then use its PowerShell terminal. Update the paths below to match your JDK and Tomcat installation:

```powershell
$project = 'C:\Users\Jatin\Documents\MovieTicketBooking'
$tomcat = 'C:\apache-tomcat-9.0.122'
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-25.0.2'
$env:JRE_HOME = $env:JAVA_HOME
Set-Location $project

$classes = Join-Path $project 'web\WEB-INF\classes'
New-Item -ItemType Directory -Force $classes | Out-Null
$sources = Get-ChildItem (Join-Path $project 'src') -Recurse -Filter '*.java' | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -cp "$tomcat\lib\servlet-api.jar" -d $classes $sources

$app = Join-Path $tomcat 'webapps\MovieTicketBooking'
New-Item -ItemType Directory -Force $app | Out-Null
Copy-Item -Path (Join-Path $project 'web\*') -Destination $app -Recurse -Force
```

The contents of `web` are the web application root. Deploy them directly into `webapps\MovieTicketBooking`; do not deploy the project root or add a nested `web` directory. Tomcat supplies the Servlet API JAR; do not copy it into the application.

Start Tomcat from the VS Code terminal if it is not already running:

```powershell
& "$tomcat\bin\startup.bat"
```

Open <http://localhost:8080/MovieTicketBooking/> in a browser. For code changes, compile again and copy the updated `web` contents to the same application directory; reload the application or restart Tomcat if needed.

## Project structure

```text
MovieTicketBooking/
├── src/com/moviebooking/
│   ├── model/                 Movie and booking models
│   ├── service/               Central movie catalogue and booking service
│   └── servlet/               Home, movie, booking, and history controllers
├── web/                       Web application root
│   ├── index.jsp, movies.jsp, movie-profile.jsp, movie-details.jsp
│   ├── my-bookings.jsp, error.jsp
│   ├── css/style.css
│   ├── js/app.js
│   ├── images/                 Local movie posters and poster source notes
│   └── WEB-INF/web.xml        Servlet declarations and URL mappings
├── README.md
└── .gitignore
```

## Limitations

- Catalogue and ticket prices are sample project data; films are not represented as currently playing in cinemas.
- Bookings and seat occupancy are held in memory. Booking history is associated with the visitor's HTTP session and data can reset when the session expires or Tomcat restarts.
- The application does not contact cinemas, reserve real seats, process payments, or store data permanently.
- Movie posters remain the property of their respective rights holders; local image sources are listed in `web/images/POSTER_SOURCES.md`.
