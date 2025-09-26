#!/bin/sh

staged_files=$(git diff --staged --name-only)

echo "🧹 Formatting code in staged files…"
./gradlew spotlessApply
for file in $staged_files; do
  if test -f "$file"; then
    git add "$file"
  fi
done

exit 0
