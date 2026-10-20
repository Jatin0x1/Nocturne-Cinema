<%@ page pageEncoding="UTF-8" %>
<%@ include file="/includes/header.jspf" %>
<section class="profile-hero">
  <div class="profile-backdrop" style="background-image:url('<%=ctx%>${movie.posterUrl}')"></div>
  <div class="profile-content reveal">
    <div class="profile-poster"><img src="<%=ctx%>${movie.posterUrl}" alt="${movie.title} movie poster" onerror="this.style.display='none';this.parentNode.classList.add('poster-fallback')"></div>
    <div class="profile-copy">
      <a class="back-link" href="<%=ctx%>/movies">← BACK TO MOVIES</a>
      <div class="eyebrow">NOCTURNE COLLECTION · ${movie.genre}</div>
      <h1>${movie.title}</h1>
      <div class="profile-facts">
        <% com.moviebooking.model.Movie profileMovie=(com.moviebooking.model.Movie)request.getAttribute("movie"); if(profileMovie.getReleaseYear()>0){ %><span>${movie.releaseYear}</span><% } %>
        <span>${movie.durationMinutes} min</span><span>${movie.language}</span><% if(profileMovie.getAgeRating()!=null&&!profileMovie.getAgeRating().trim().isEmpty()){ %><span class="certification">${movie.ageRating}</span><% } %>
      </div>
      <p class="profile-synopsis">${movie.description}</p>
      <div class="profile-credits">
        <% if(profileMovie.getDirector()!=null&&!profileMovie.getDirector().trim().isEmpty()){ %><div><small>DIRECTED BY</small><strong>${movie.director}</strong></div><% } %>
        <% if(profileMovie.getCast()!=null&&!profileMovie.getCast().trim().isEmpty()){ %><div><small>STARRING</small><strong>${movie.cast}</strong></div><% } %>
      </div>
      <div class="profile-actions"><a class="button button-primary" href="<%=ctx%>/movie?id=${movie.id}#booking">Book Tickets <span>↗</span></a><span class="profile-price">₹${movie.ticketPrice}<small> / seat</small></span></div>
    </div>
  </div>
  <div class="profile-side-note">A STORY WORTH THE LATE NIGHT</div>
</section>
<section class="profile-rating-section">
  <% if(profileMovie.getIllustrativeRating()!=null&&!profileMovie.getIllustrativeRating().trim().isEmpty()){ %><div class="profile-rating"><span class="stars" aria-label="Illustrative rating">★★★★<i>★</i></span><div><strong>${movie.illustrativeRating}<small> / 5</small></strong><span>Illustrative Nocturne rating · not sourced from a verified rating provider</span></div></div><p>Ratings shown here are illustrative project content, not audience or critic scores.</p><% }else{ %><div class="profile-rating"><div><strong>Rating unavailable</strong><span>No verified rating is available for this title.</span></div></div><% } %>
</section>
<section class="section recommendations">
  <div class="section-heading"><div><div class="eyebrow">KEEP THE NIGHT GOING</div><h2>More stories to <em>discover.</em></h2></div><a class="text-link" href="<%=ctx%>/movies">All movies ↗</a></div>
  <div class="movie-rail profile-recommendations">
    <% java.util.List<com.moviebooking.model.Movie> recommended=(java.util.List<com.moviebooking.model.Movie>)request.getAttribute("recommendations"); if(recommended!=null) for(com.moviebooking.model.Movie m:recommended){ %>
      <article class="movie-card"><a class="poster-wrap" href="<%=ctx%>/details?id=<%=m.getId()%>" aria-label="View details for <%=m.getTitle()%>"><img src="<%=ctx%><%=m.getPosterUrl()%>" alt="<%=m.getTitle()%> movie poster" onerror="this.style.display='none';this.parentNode.classList.add('poster-fallback')"><span class="poster-shade"></span><span class="poster-play">↗</span></a><div class="movie-info"><div><h3><a href="<%=ctx%>/details?id=<%=m.getId()%>"><%=m.getTitle()%></a></h3><p><%=m.getGenre()%> <span>·</span> <%=m.getDurationMinutes()%> min</p></div></div><a class="text-link" href="<%=ctx%>/details?id=<%=m.getId()%>">View details ↗</a></article>
    <% } %>
  </div>
</section>
<%@ include file="/includes/footer.jspf" %>
