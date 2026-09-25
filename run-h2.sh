#!/bin/bash

# H2 Database - Local Testing Script
# Runs Spring Boot with H2 in-memory database
# Useful for quick request testing before Docker container deployment

set -e

echo " Starting E-Commerce API with H2 Database..."
echo ""
echo "Configuration:"
echo "  - Database: H2 (in-memory)"
echo "  - Console URL: http://localhost:8080/h2-console"
echo "  - JDBC URL: jdbc:h2:mem:ecommerce"
echo "  - Username: sa"
echo "  - Password: (empty)"
echo ""
echo "Security defaults for testing:"
echo "  - Default user: user"
echo "  - Default password: 12345"
echo ""
echo "Press Ctrl+C to stop the application"
echo "---"
echo ""

# Run with h2 profile
./gradlew bootRun --args='--spring.profiles.active=h2'
