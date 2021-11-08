---
layout: page
title: Testing guide
---

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Running tests

There are two ways to run tests.

* **Method 1: Using IntelliJ JUnit test runner**
  * To run all tests, right-click on the `src/test/java` folder and choose `Run 'All Tests'`
  * To run a subset of tests, you can right-click on a test package,
    test class, or a test and choose `Run 'ABC'`
* **Method 2: Using Gradle**
  * Open a console and run the command `gradlew clean test` (Mac/Linux: `./gradlew clean test`)

<div markdown="span" class="alert alert-secondary">:link: **Link**: Read [this Gradle Tutorial from the se-edu/guides](https://se-education.org/guides/tutorials/gradle.html) to learn more about using Gradle.
</div>

--------------------------------------------------------------------------------------------------------------------

## Types of tests

This project has three types of tests:

1. *Unit tests* targeting the lowest level methods/classes.<br>
   e.g. `donnafin.commons.StringUtilTest`
1. *Integration tests* that are checking the integration of multiple code units (those code units are assumed to be working).<br>
   e.g. `donnafin.storage.StorageManagerTest`
1. Hybrids of unit and integration tests. These test are checking multiple code units as well as how the are connected together.<br>
   e.g. `donnafin.logic.LogicManagerTest`

### Automated GUI Testing

It is important to note that there are automated headless GUI testing done for a subset of JavaFX controller classes. We
have substantial tests for smaller components like `ResultDisplay`, `PersonListPanel`, `HelpWindow`, etc that even may
include user input and action (see
[`CommandBoxTest.java`](https://github.com/AY2122S1-CS2103T-W16-1/tp/blob/master/src/test/java/donnafin/ui/CommandBoxTest.java)).

Notably, `AttributeTable`, and `ClientPanel` are only tested to ensure that it can be loaded and instantiated. While
additional and more rigorous UI testing is possible, due to the difficulty of integrating them with headless CI actions,
the current developers have elected to only testing up to instantiation point (i.e loading up the UI without
any user interaction).

This already ensures `AttributeTable` and `ClientPanel` code and their corresponding `.fxml` files are valid and can be
used by JavaFX to generate a valid UI. However, additional testing can be done to expand the rigor and cover additional
features.
