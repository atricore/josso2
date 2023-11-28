# Makefile for building and releasing a Maven project

# Define variables
MVN = mvn

# Default target
all: build

# Build the project
build:
	$(MVN) -o clean install -Dmaven.test.skip=true -P release

# Clean the project
clean:
	$(MVN) -o clean

# Help
help:
	@echo "Available targets:"
	@echo "  all     - Build the project (default target)"
	@echo "  build   - Clean and install the project"
	@echo "  clean   - Clean the project"
	@echo "  help    - Display this help message"

.PHONY: all build clean help

