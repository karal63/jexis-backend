#!/bin/bash

branch=$(git rev-parse --abbrev-ref HEAD)

if [ "$branch" = "HEAD" ]; then
  echo "Detached HEAD state."
  exit 1
fi

git push origin "$branch"