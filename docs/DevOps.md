---
layout: page
title: DevOps guide
---

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Build automation

This project uses Gradle for **build automation and dependency management**. **You are recommended to read [this Gradle Tutorial from the se-edu/guides](https://se-education.org/guides/tutorials/gradle.html)**.


Given below are how to use Gradle for some important project tasks.


* **`clean`**: Deletes the files created during the previous build tasks (e.g. files in the `build` folder).
  This would remove files like `data/*`, `config.json`, `preferences.json`. <br>
  e.g. `./gradlew clean`

* **`shadowJar`**: Uses the ShadowJar plugin to creat a fat JAR file in the `build/lib` folder, *if the current file is outdated*.<br>
  e.g. `./gradlew shadowJar`.

* **`run`**: Builds and runs the application.<br>
  **`runShadow`**: Builds the application as a fat JAR, and then runs it.<br>
  Note that in the interests of developers (likely the only users of a non-compiled DonnaFin codebase), these tasks
  have assertions enabled by default to have the JVM report assertion errors promptly.

* **`checkstyleMain`**: Runs the code style check for the main code base.<br>
  **`checkstyleTest`**: Runs the code style check for the test code base.

* **`test`**: Runs all tests.<
  * `./gradlew test` — Runs all tests
  * `./gradlew clean test` — Cleans the project and runs tests

--------------------------------------------------------------------------------------------------------------------

## Continuous integration (CI)

This project uses GitHub Actions for CI. The project comes with the necessary GitHub Actions configurations files (in the `.github/workflows` folder). No further setting up required.

### Code coverage

**Current Code Cov Report:**

[![Current Code Coverage sun-burst diagram](https://codecov.io/gh/AY2122S1-CS2103T-W16-1/tp/branch/master/graphs/sunburst.svg)](https://codecov.io/gh/AY2122S1-CS2103T-W16-1/tp)


As part of CI, this project uses Codecov to generate coverage reports. Here are the steps to set up CodeCov for a fork of this repository.

1. Sign up with Codecov using your GitHub account [here](https://codecov.io/signup).
1. Once you are inside Codecov web app, add your fork to CodeCov.
1. Get the Markdown code for the Codecov badge provided in `Settings > Badges` and update the `docs/index.md` of your repo with it so that the badge [![codecov](https://codecov.io/gh/AY2122S1-CS2103T-W16-1/tp/branch/master/graph/badge.svg?token=TI96BI7OBN)](https://codecov.io/gh/AY2122S1-CS2103T-W16-1/tp) in that page reflects the coverage of your project.

### Repository-wide checks

In addition to running Gradle checks, CI includes some repository-wide checks. Unlike the Gradle checks which only cover files used in the build process, these repository-wide checks cover all files in the repository. They check for repository rules which are hard to enforce on development machines such as line ending requirements.

These checks are implemented as POSIX shell scripts, and thus can only be run on POSIX-compliant operating systems such as macOS and Linux. To run all checks locally on these operating systems, execute the following in the repository root directory:

`./config/travis/run-checks.sh`

Any warnings or errors will be printed out to the console.

**If adding new checks:**

* Checks are implemented as executable `check-*` scripts within the `.github` directory. The `run-checks.sh` script will automatically pick up and run files named as such. That is, you can add more such files if you need and the CI will do the rest.

* Check scripts should print out errors in the format `SEVERITY:FILENAME:LINE: MESSAGE`
  * SEVERITY is either ERROR or WARN.
  * FILENAME is the path to the file relative to the current directory.
  * LINE is the line of the file where the error occurred and MESSAGE is the message explaining the error.

* Check scripts must exit with a non-zero exit code if any errors occur.

<div markdown="span" class="alert alert-primary">

:bulb: **Easily remove trailing whitespace in markdown files:**
The [Trim Trailing Whitespace github workflow](https://github.com/AY2122S1-CS2103T-W16-1/tp/actions/workflows/TrimTrailingWhitespaces.yml)
can be used to quickly trim all the trailing whitespaces at the end of lines in markdown files. Once set up, simply
hitting the 'Run workflow' button or scheduling this workflow will regularly create a PR (if any changes are needed) to
trim markdown files to specifications.

The set up portion, while short, does require some familiarity with Github tools. First, create a
[Personal Access Token](https://github.com/settings/tokens) (with admin:repo_hook, repo priveleges), and [save it as a
repository secret](https://github.com/AY2122S1-CS2103T-W16-1/tp/settings/secrets/actions) named `'PAT'`. You should only
do this if you trust the users who have write and workflow access to this repository. Read more about what this means
for the security of your profile [here](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token).
</div>

--------------------------------------------------------------------------------------------------------------------

## Making a release

Here are the steps to create a new release.

1. Update the version number in [`MainApp.java`](https://github.com/AY2122S1-CS2103T-W16-1/tp/blob/master/src/main/java/donnafin/MainApp.java).
1. Generate a fat JAR file using Gradle (i.e., `gradlew shadowJar`).
1. Tag the repo with the version number. e.g. `v0.1`
1. [Create a new release using GitHub](https://help.github.com/articles/creating-releases/). Upload the JAR file you created.
