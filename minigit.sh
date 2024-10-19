#!/usr/bin/bash

set -e
SCRIPT_DIR=$(dirname "$0")
(
  cd "$SCRIPT_DIR"
  mvn -B package -Ddir=/tmp/minigit
)
exec java -jar /tmp/minigit.jar "$@"
