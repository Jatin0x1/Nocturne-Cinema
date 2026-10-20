# Nocturne — Movie Ticket Booking Mini Project

A beginner-friendly Java web application built with Servlets, JSP, HTML, CSS and vanilla JavaScript. Movie data and demo seat occupancy are held in Java collections; bookings are stored in the visitor's HTTP session and disappear when the session expires or Tomcat restarts.

## Stage 1 design

- **Context path:** `/MovieTicketBooking`
- **Routes:** `/home`, `/movies`, `/movie?id=...`, `/book` (POST), `/bookings`
- **Flow:** Home → Movies → Details and seats → POST booking → confirmation → My Bookings
- **Layers:** models (`Movie`, `Booking`), service (`BookingService`, `MovieCatalog`), servlet controllers, JSP views.
- `web` is the web application root. JSP files and `WEB-INF/web.xml` are directly inside it. Java source is compiled into `web/WEB-INF/classes`.

## Requirements

- JDK 11 or newer (Java 25 is fine if available)
- Apache Tomcat 9
- VS Code with Extension Pack for Java (optional but useful)
- Tomcat 9 `servlet-api.jar`, normally in `<TOMCAT_HOME>/lib`

No database, JDBC, Maven, Gradle, npm, external movie API, or API key is used. Movie posters are stored in `web/images`; no remote movie service is used. Local image failures show a designed gradient fallback. Poster source pages and attribution notes are in `web/images/POSTER_SOURCES.md`. Ticket prices are illustrative demo prices, and movie selections represent a sample collection rather than verified current cinema availability.

## Compile and deploy from VS Code terminal

Open `C:\Users\Jatin\Documents\MovieTicketBooking` in VS Code. In PowerShell, set the paths below to your installation. The servlet API is supplied by Tomcat and is only needed on the compilation classpath; do not copy it into `WEB-INF/lib`.

```powershell
$project = 'C:\Users\Jatin\Documents\MovieTicketBooking'
$tomcat = 'C:\apache-tomcat-9.0.122'
$env:CATALINA_HOME = $tomcat
$env:CATALINA_BASE = $tomcat
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-25.0.2'
$env:JRE_HOME = $env:JAVA_HOME
Set-Location $project
$classes = Join-Path (Get-Location) 'web\WEB-INF\classes'
New-Item -ItemType Directory -Force $classes | Out-Null
$sources = Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -cp "$tomcat\lib\servlet-api.jar" -d $classes $sources
```

Deploy the **contents** of `web` as the application root. The Tomcat installation already had a `webapps\MovieTicketBooking` directory containing the source tree (`src` and a nested `web` folder). The following commands preserve that directory under Tomcat's `source-archive` folder, then create a clean app root. Do not put the source project folder or a nested `web` directory in the deployed app.

```powershell
$app = Join-Path $tomcat 'webapps\MovieTicketBooking'
if (Test-Path $app) {
  $archiveRoot = Join-Path $tomcat 'source-archive'
  New-Item -ItemType Directory -Force $archiveRoot | Out-Null
  $archive = Join-Path $archiveRoot "MovieTicketBooking-source-$(Get-Date -Format 'yyyyMMdd-HHmmss')"
  Move-Item -LiteralPath $app -Destination $archive
}
New-Item -ItemType Directory -Force $app | Out-Null
Copy-Item -Recurse -Force .\web\* $app
$tomcatProcess = Get-CimInstance Win32_Process | Where-Object {
  $_.CommandLine -like '*org.apache.catalina.startup.Bootstrap*' -and $_.CommandLine -like "*$tomcat*"
}
if (-not $tomcatProcess) {
  & "$tomcat\bin\startup.bat"
}
```

Visit `http://localhost:8080/MovieTicketBooking/`. For iterative work, compile into the deployed app's `WEB-INF/classes` or copy updated files from `web`, then reload the application in Tomcat Manager or restart Tomcat. In VS Code, use **Terminal → New Terminal** for the commands. Ensure `JAVA_HOME` points to the JDK, not a JRE.

## Features

- Sixteen real Indian and international films with locally bundled posters, search, genre/language filters, and sorting
- Date and showtime validation, seat selection, server-side pricing, and shared in-memory seat occupancy across sessions
- Session booking history and printable confirmation
- Responsive layout, keyboard-aware forms, reduced-motion support, and image fallback styling

## Demo limitations

This is an educational demonstration, not a production ticketing service. Booking state is in memory/session and may reset. It does not contact cinemas, accept payments, or reserve real seats. Demo ticket prices are illustrative INR amounts. Indian age labels use CBFC U/A; US films use their MPA rating labels (PG, PG-13, R). Poster artwork remains the property of its respective rights holders; source links are listed in `web/images/POSTER_SOURCES.md`.

## Test checklist

1. Open the home page and follow Explore Movies.
2. Search by title, choose genre/language and sort by title or price; verify the empty state with an unmatched query.
3. Open a movie; choose a future date and offered showtime, select multiple seats, and check the visible amount.
4. Submit without seats and verify validation; then book seats and confirm title/date/time/seats/reference/total.
5. Print the confirmation, then open My Bookings and confirm the booking appears in this session.
6. Submit a tampered seat ID or invalid movie ID and confirm a friendly error.
7. Open a separate session/private window and confirm its booking list is separate.

## Viva quick guide

- **Where is data stored?** The sample catalog and live seat occupancy are Java collections in the application service; each visitor's booking history is an `ArrayList` held in `HttpSession`.
- **Why validate price on the server?** Browser values can be changed; the server recalculates from the movie price and accepted seat IDs.
- **What does a servlet do?** It reads an HTTP request, validates or prepares data, then forwards to a JSP or redirects.
- **Why POST for booking?** It changes booking state, so the form submits with POST; after success the server redirects to the confirmation view.
- **What does `HttpSession` provide?** A session-scoped place to associate booking history with one visitor across requests.
- **What happens on restart?** In-memory data is reset; no permanent storage is claimed.
- **Why `javax.servlet`?** Tomcat 9 uses the Java EE `javax.servlet` namespace.
- **Where does `web.xml` belong?** At `web/WEB-INF/web.xml`, because `web` is deployed as the application root.

## Troubleshooting

- `javax.servlet` missing during compilation: verify the Tomcat 9 `lib\servlet-api.jar` path.
- 404: confirm deployment directory is `webapps\MovieTicketBooking` and use the same context path.
- Java changes not visible: recompile to the deployed `WEB-INF/classes` and reload the web application.



