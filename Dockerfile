# Compile against the Servlet API bundled with the matching Tomcat 9 release.
FROM tomcat:9.0.122-jdk21-temurin-noble AS build

WORKDIR /build
COPY src/ ./src/
RUN mkdir -p /compiled \
    && find ./src -type f -name '*.java' -print0 \
       | xargs -0 javac -encoding UTF-8 \
           -classpath "$CATALINA_HOME/lib/servlet-api.jar" \
           -d /compiled

# Keep the runtime image on the same Tomcat release, without a JDK.
FROM tomcat:9.0.122-jre21-temurin-noble AS runtime

ENV PORT=10000
EXPOSE 10000

# Tomcat resolves this property when it reads server.xml. The entrypoint
# supplies it from Render's PORT environment variable at container startup.
RUN sed -i 's/port="8080"/port="${render.http.port}" address="0.0.0.0"/' \
        "$CATALINA_HOME/conf/server.xml" \
    && rm -rf "$CATALINA_HOME/webapps"/* \
    && mkdir -p "$CATALINA_HOME/webapps/ROOT/WEB-INF/classes" \
    && groupadd --system --gid 10001 nocturne \
    && useradd --system --uid 10001 --gid 10001 --home-dir /nonexistent \
        --no-create-home --shell /usr/sbin/nologin nocturne

# web/ is the web application root, so its contents go directly into ROOT.
COPY web/ /usr/local/tomcat/webapps/ROOT/
COPY --from=build /compiled/ /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/
COPY docker-entrypoint.sh /usr/local/bin/nocturne-entrypoint

RUN chmod 755 /usr/local/bin/nocturne-entrypoint \
    && chown -R 10001:10001 "$CATALINA_HOME/logs" "$CATALINA_HOME/temp" \
        "$CATALINA_HOME/work" "$CATALINA_HOME/webapps"

USER 10001:10001
ENTRYPOINT ["/usr/local/bin/nocturne-entrypoint"]
