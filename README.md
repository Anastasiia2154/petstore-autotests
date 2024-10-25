
This project is designed to perform automated API testing using Java, TestNG, and Rest Assured. The tests include validation for different endpoints, covering both positive and negative scenarios. The project uses ReportNG for HTML reporting, providing test results overview.

## Content structure:
1. [Project Structure](#project-structure)
2. [Requirements](#requirements)
3. [Installation and Setup](#installation-and-setup)
4. [Configuration](#configuration)
5. [Running Tests](#running-tests)
6. [Viewing Test Reports](#viewing-test-reports)

## Project Structure
project-root
│
├── src
│   └── main
│       └── java                      # Contains the main application code.
│           └── clients                # API client classes for interacting with the Petstore.
│           └── pojo                   # Java Objects (POJOs) representing data models.
│           └── config                 # Configuration classes for logging and other settings.
│
└── src
    └── test
        └── java                       # Contains test code for testing the application.
            └── tests                  # Test classes organized by functionality.
│
└── utils                               # Utility classes for common functionalities.
│
└── resources                           # Resource files used in the project.
    └── testng_xml                     # XML configuration files for TestNG.
        └── tlog4j.properties           # Logging configuration file.
│
└── pom.xml                            # Maven Project Object Model file for dependencies and build settings.

## Requirements

- **Java Development Kit (JDK)**: Version 11 or later.
- **Apache Maven**: Version 3.6+.
- **Git**: Version control to clone the project.

## Installation and Setup
Step 1: Clone the Repository
Clone the project from GitHub:
```bash
git clone your-repo-url
cd project-root

Step 2: Build the Project**
mvn clean install

## Configurations
Log4j Logging
The project uses Log4j for logging, configured in the log4j.properties file. You can customize logging levels and output formats in src/test/resources/log4j.properties.

## ReportNG Configuration
ReportNG is set up in pom.xml to generate HTML reports after each test run.

To enable ReportNG:
Ensure that the pom.xml includes dependencies for ReportNG and its TestNG listener.
Check that testng.xml is correctly configured to use ReportNG as the listener.

## Running Tests
To run the tests, use the following Maven command from the project root directory:
mvn clean test

## Viewing Test Reports
After running the tests, an HTML report is generated in the target/surefire-reports/html directory.

To view the report:

Open the index.html file located in target/surefire-reports/html with your web browser.
Browse the report to view detailed information on each test, including logs, pass/fail status, and request/response data.
