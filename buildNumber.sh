#!/usr/bin/env bash

# Function to display error and exit
die() {
  echo "$1" >&2
  exit 1
}

# Get the root buildNumber.properties file
ROOT_BUILD_FILE="./buildNumber.properties"

# Check if root buildNumber.properties exists
if [ ! -f "$ROOT_BUILD_FILE" ]; then
  die "Error: Root buildNumber.properties file not found"
fi

# Read the current build number from the file
CURRENT_BUILD=$(grep -E "^buildNumber=" "$ROOT_BUILD_FILE" | cut -d= -f2)

# Check if we found a valid build number
if [ -z "$CURRENT_BUILD" ]; then
  die "Error: Could not find buildNumber property in $ROOT_BUILD_FILE"
fi

# Calculate the new build number (increment by 1)
NEW_BUILD=$((CURRENT_BUILD + 1))

echo "Incrementing root build number from $CURRENT_BUILD to $NEW_BUILD"

# Update all buildNumber.properties files to have the new build number
# regardless of their current value
for i in $(find . -name buildNumber.properties); do
  # Use sed with a different approach to replace any value after buildNumber=
  sed -i 's/^buildNumber=.*/buildNumber='"$NEW_BUILD"'/' "$i"
  echo "Updated $i"
done

echo "Build number update complete"
