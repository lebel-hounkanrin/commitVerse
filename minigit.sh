#!/usr/bin/bash

set -e
SCRIPT_DIR=$(dirname "$0")
(
  cd "$SCRIPT_DIR"
  mvn -B package -Ddir=/tmp/commitVerse
)
exec java -jar /tmp/commitVerse/commitVerse.jar "$@"
