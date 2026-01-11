#!/bin/sh

echo "🧹 Formatting code in staged files…"
./gradlew spotlessApply
git diff --staged --name-only | while IFS= read -r file; do
  if test -f "$file"; then
    git add "$file"
  fi
done

exit 0
