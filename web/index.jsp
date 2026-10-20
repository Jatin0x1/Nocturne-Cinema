<%@ page pageEncoding="UTF-8" %>
    <%@ include file="/includes/header.jspf" %>
        <section class="hero">
            <div class="hero-image"></div>
            <div class="hero-content reveal">
                <div class="eyebrow"><span class="live-dot"></span> YOUR PRIVATE SCREENING ROOM</div>
                <h1>Make tonight<br>feel <em>like a movie.</em></h1>
                <p>Find your seat in another world. Hand-picked stories, unforgettable screens, all in one place.</p><a
                    class="button button-primary" href="<%=ctx%>/movies">Explore movies <span>↗</span></a>
                <div class="hero-meta"><span>✦ CURATED FOR YOU</span><span>01 — 16 / FEATURED COLLECTION</span></div>
            </div>
            <div class="hero-orb"></div>
            <div class="hero-side-note">SCROLL TO DISCOVER &nbsp; ↓</div>
        </section>
        <section class="section featured">
            <div class="section-heading">
                <div>
                    <div class="eyebrow">THE FEATURED REEL</div>
                    <h2>Stories worth <em>staying up for.</em></h2>
                </div><a class="text-link" href="<%=ctx%>/movies">View all films <span>↗</span></a>
            </div>
            <div class="movie-rail">
                <% java.util.List<com.moviebooking.model.Movie> featured = (java.util.List<com.moviebooking.model.Movie>
                        )request.getAttribute("featuredMovies"); if (featured != null) for (com.moviebooking.model.Movie
                        m : featured) { %>
                        <article class="movie-card"><a class="poster-wrap" href="<%=ctx%>/details?id=<%=m.getId()%>"
                                aria-label="View details for <%=m.getTitle()%>"><img src="<%=ctx%><%=m.getPosterUrl()%>"
                                    alt="Official poster for <%=m.getTitle()%>"
                                    onerror="this.style.display='none';this.parentNode.classList.add('poster-fallback')"><span
                                    class="poster-shade"></span><span class="poster-index">NO. 0
                                    <%=featured.indexOf(m)+1%></span><span class="poster-play">▶</span></a>
                            <div class="movie-info">
                                <div>
                                    <h3><a href="<%=ctx%>/details?id=<%=m.getId()%>">
                                            <%=m.getTitle()%>
                                        </a></h3>
                                    <p>
                                        <%=m.getGenre()%> <span>·</span>
                                            <%=m.getDurationMinutes()%> min
                                    </p>
                                </div><span class="rating">
                                    <%=m.getAgeRating()%>
                                </span>
                            </div>
                        </article>
                        <% } %>
            </div>
        </section>
        <section class="manifesto">
            <div class="manifesto-stamp">N</div>
            <div>
                <div class="eyebrow">A DIFFERENT KIND OF MOVIE NIGHT</div>
                <h2>Less scrolling.<br><em>More feeling.</em></h2>
            </div>
            <p>From faraway galaxies to stories close to home, find a film that stays with you after the credits. Pick
                your seats, settle in, and let the lights go down.</p>
        </section>
        <section class="stats">
            <div><strong>04</strong><span>DAILY SHOWTIMES</span></div>
            <div><strong>01</strong><span>PERFECT NIGHT OUT</span></div>
        </section>
        <%@ include file="/includes/footer.jspf" %>
