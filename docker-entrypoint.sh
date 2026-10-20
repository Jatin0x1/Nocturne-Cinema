#!/bin/sh
set -eu

# Render provides PORT. Keep a useful default for local Docker runs.
PORT="${PORT:-10000}"
case "$PORT" in
    ''|*[!0-9]*)
        echo "PORT must be a number between 1 and 65535" >&2
        exit 1
        ;;
esac
if [ "$PORT" -lt 1 ] || [ "$PORT" -gt 65535 ]; then
    echo "PORT must be a number between 1 and 65535" >&2
    exit 1
fi

CATALINA_OPTS="${CATALINA_OPTS:-} -Drender.http.port=${PORT}"
export CATALINA_OPTS

exec "$CATALINA_HOME/bin/catalina.sh" run
