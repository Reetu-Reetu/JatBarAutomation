# JatBarAutomation

Selenium WebDriver tests for the [Jat Mobile Bar and Events website](https://jat-mobile-bar-website.vercel.app/). The project uses Java, TestNG and Maven.

## Requirements

- Java 17
- Maven
- Google Chrome

## Run the tests

From the project root, run:

```bash
mvn clean test
```

Maven prints the test totals and build result in the terminal. Generated test reports are available in `target/surefire-reports/`. The `target/` folder is ignored by Git because it is recreated on each build.

## What is tested

The suite covers homepage loading and title, navigation links, Contact-form visibility and fields, and form validation. Validation cases include empty required fields, invalid email and telephone values, enquiry length boundaries, and privacy consent.

The form-validation tests deliberately leave required fields incomplete. They check error messages **without sending a real enquiry**.

## Project structure

- `src/test/java/com/jatbar/base/` — browser setup and cleanup
- `src/test/java/com/jatbar/pages/` — page elements and actions
- `src/test/java/com/jatbar/tests/` — TestNG test cases
- `pom.xml` — Java version and Maven dependencies

## Dependencies

- Java 17
- Selenium WebDriver 4.19.1
- TestNG 7.10.2