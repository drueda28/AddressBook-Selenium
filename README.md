# AddressBook-Selenium

Automated UI tests and example pages for an Address Book web application using Selenium WebDriver.

This repository contains a simple address book web UI (HTML/CSS/JS) plus Selenium-based test automation (Java) that demonstrates end-to-end testing of common address-book workflows: creating, editing, searching, and deleting contacts.

## Features

- Minimal HTML/CSS/JS front-end for an address book (demo pages).
- Selenium WebDriver automation scripts in Java for functional UI tests.
- Examples showing how to configure WebDriver and run tests locally.

## Tech stack

- Front-end: HTML, CSS, JavaScript
- Tests: Java + Selenium WebDriver
- Test runner: JUnit / TestNG (project-dependent — see your build files)
- Browser drivers: ChromeDriver / GeckoDriver (or WebDriverManager)

## Prerequisites

- Java JDK 8 or newer installed and JAVA_HOME configured.
- A build tool (Maven or Gradle) if the project uses one. If the project doesn’t use a build tool, the tests can be run from an IDE (IntelliJ, Eclipse).
- A browser (Chrome, Firefox) and the matching WebDriver binary (chromedriver/geckodriver) OR add WebDriverManager to your dependencies so drivers are handled automatically.
- Git (to clone the repo).

## Quick start

1. Clone the repository
   git clone https://github.com/drueda28/AddressBook-Selenium.git
   cd AddressBook-Selenium

2. Configure WebDriver
   - Option A: Install a browser driver and add it to PATH (recommended).
     - Download chromedriver (https://chromedriver.chromium.org/downloads) or geckodriver (https://github.com/mozilla/geckodriver/releases) and place it in your PATH.
   - Option B: Use WebDriverManager (add dependency) so the driver is downloaded at runtime.

3. Build & run tests
   - If the project uses Maven:
     mvn test
   - If the project uses Gradle:
     ./gradlew test
   - Or run test classes from your IDE (right-click test class -> Run).

If the project does not contain a build configuration, open the test classes in your IDE and run them directly (ensure dependencies for Selenium and a test framework are available).

## Typical test configuration examples

- Setting ChromeDriver system property (example):
  System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");

- Using WebDriverManager (recommended to avoid manual driver installs):
  WebDriverManager.chromedriver().setup();
  WebDriver driver = new ChromeDriver();

- Example Maven dependencies (pom.xml snippet):
  <dependencies>
    <!-- Selenium -->
    <dependency>
      <groupId>org.seleniumhq.selenium</groupId>
      <artifactId>selenium-java</artifactId>
      <version>4.x.x</version>
    </dependency>
    <!-- JUnit 5 -->
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter</artifactId>
      <version>5.x.x</version>
      <scope>test</scope>
    </dependency>
    <!-- WebDriverManager (optional) -->
    <dependency>
      <groupId>io.github.bonigarcia</groupId>
      <artifactId>webdrivermanager</artifactId>
      <version>5.x.x</version>
    </dependency>
  </dependencies>

Adjust versions to current stable releases.

## Project layout (conventional)

- / (root)
  - index.html, app/ — demo HTML/CSS/JS address book pages
  - /src or /tests — Java Selenium test source code (may be under src/test/java)
  - pom.xml or build.gradle — build configuration (if present)
  - README.md — this file

Note: If your repository structure differs, adapt the commands above to the actual layout.

## Writing and extending tests

- Keep page-specific selectors in Page Object classes to improve maintainability.
- Use explicit waits (WebDriverWait) rather than Thread.sleep().
- Parametrize tests to run against different browsers via system properties or CI matrix.
- Isolate test data — reset application state between tests if possible.

## Running in CI

- Use a matrix build to test across multiple browser versions.
- Install or download browser drivers during the job, or use WebDriverManager in the tests.
- For headless runs, launch browsers with headless options (ChromeOptions or FirefoxOptions).

Example for headless Chrome:
  ChromeOptions options = new ChromeOptions();
  options.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080");
  WebDriver driver = new ChromeDriver(options);

## Troubleshooting

- “Driver not found” — ensure chromedriver/geckodriver is on PATH or WebDriverManager is enabled.
- “Element not found / StaleElementReferenceException” — use waits and re-locate elements after page updates.
- Version mismatch — keep browser and driver versions compatible.

## Contributing

Contributions are welcome. Good contributions include:
- Fixes to existing tests and locators.
- New end-to-end scenarios.
- CI configuration for automated runs.
- Documentation improvements.

Please open issues or submit pull requests with a clear description of the change and how to run the tests locally.

## License

If you want others to use or contribute to this project, add a LICENSE file (for example, MIT). If no LICENSE file exists, the repository’s code is not explicitly licensed.

---

If you’d like, I can:
- Commit this README.md directly to the repository's default branch, or
- Create a branch and open a pull request with the README.

Tell me which you prefer (and which branch name or PR title), and I’ll commit it for you.
