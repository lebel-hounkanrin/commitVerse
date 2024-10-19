#!/usr/bin/bash

set -e
SCRIPT_DIR=$(dirname "$0")
(
  cd "$SCRIPT_DIR"
  mvn -B package -Ddir=/tmp/minigit-java
)
exec java -jar /tmp/minigit-java/minigit.jar "$@"
