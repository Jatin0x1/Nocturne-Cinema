# Syllabus-to-code mapping

Use the topics below to match against your institution's official syllabus. No unit names are assumed.

| Topic | Source location | Demonstration |
|---|---|---|
| Classes, objects, constructors, encapsulation | `src/com/moviebooking/model/Movie.java`, `Booking.java` | Private fields, constructors, getters and setters |
| Inheritance and overriding | `Movie.java` | `Movie` extends `CatalogItem`; overrides `getDisplayLabel()` |
| Interface and collections | `src/com/moviebooking/service/MovieCatalog.java`, `BookingService.java` | `Catalog` interface; `Map`, `List`, and `ArrayList` |
| Loops, conditionals, strings | `MovieCatalog.java`, `BookingService.java`, `src/com/moviebooking/servlet/MovieServlet.java` | Catalog initialization, validations, filtering, case-insensitive search |
| Exception handling | `BookingService.java`, `BookingServlet.java` | Validation exceptions handled and presented as friendly errors |
| Servlets and HTTP | `src/com/moviebooking/servlet/*.java` | `doGet()`, `doPost()`, request parameters and response status |
| Forwarding and redirecting | `MovieServlet.java`, `BookingServlet.java` | `RequestDispatcher.forward()` and Post/Redirect/Get |
| Sessions | `BookingService.java`, `MyBookingsServlet.java` | Session-scoped booking list |
| Web application lifecycle | `src/com/moviebooking/servlet/AppLifecycleListener.java`, `web/WEB-INF/web.xml` | Creates fresh application-scoped movie catalog and seat inventory on deployment |
| JSP, EL, forms | `web/*.jsp` | `${...}` expressions, HTML forms; no JSTL dependency |
| MVC separation | `src/` and `web/` | Model/service/controller/view responsibilities |
| Deployment descriptor | `web/WEB-INF/web.xml` | Explicit servlet declarations, mappings and welcome page |
