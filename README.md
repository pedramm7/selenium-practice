# Selenium Practice Project

This project automates UI testing for [Expand Testing](https://practice.expandtesting.com) using Selenium WebDriver with Java 21, JUnit 5, and Gradle. It supports Docker (via docker-compose), uses JSON configuration for test data, and follows the Page Object Model (POM) for a modular test design. Configuration (such as headless mode) can be controlled via environment variables.

## Features

**Core Functional Tests:**
- Login (form fill) on **/login**
- Logout from the secure area (**/secure**)
- Filling inputs (text, radio, checkboxes) on **/inputs** and **/checkboxes**
- Testing textarea, dropdown, and radio inputs on appropriate pages (e.g. **/inputs**, **/dropdown**)
- Static page tests (using available pages like **/login** and **/secure**)
- Multiple static page tests loaded from config
- Complex XPath selection, explicit wait usage, and reading the page title

**Advanced Tests:**
- WebDriver configuration (headless mode via env variable)
- Cookie injection (to simulate bypassing a consent popup)
- Hover actions on **/hovers**
- File upload on **/upload**
- History navigation (back button)
- Test case dependencies (chain actions)
- Simulated email verification (mock email activation)
- Random test data using Faker
- File download simulation (if applicable)
- JavaScriptExecutor interactions

## Tech Stack

- Java 21
- Selenium WebDriver
- JUnit 5
- Gradle
- Docker & docker-compose
- WebDriverManager
- JSON config (with Jackson)
- Faker (for random test data)
- Page Object Model (POM)
- CLI/env variable support

## How to Run
1. Clone the repository.
2. Run `gradle test` to execute tests.
3. Set the environment variable `HEADLESS=true` to run Chrome in headless mode.
4. Use Docker Compose with `docker-compose up -d` if needed.

Happy Testing!
