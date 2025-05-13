#!/bin/sh

# Define the URL as a variable for easier maintenance
URL="http://localhost:8080/actuator/shutdown"

# Use curl with options for better error handling and feedback
# -s: Silent mode (no progress meter or error messages)
# -S: Show error messages if -s is used
# -f: Fail silently on server errors (e.g., 404, 500)
response=$(curl -sSf -X POST "$URL")

# Check the exit status of the curl command
if [ $? -eq 0 ]; then
    echo "Shutdown request sent successfully."
else
    echo "Failed to send shutdown request."
    exit 1
fi
