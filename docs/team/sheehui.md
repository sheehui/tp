---
layout: page
title: Shee Hui's Project Portfolio Page
---

### Project: DonnaFin.io

DonnaFin.io is a desktop application used by financial advisors to keep track of their
client information and related tasks. The '.io' in our name is a reflection of our belief
that you deserve a faster workflow for input and output. If you can type fast,
you can use our CLI-like commands to manage your client information
and view your notes much faster than your typical customer relationship manager apps.

Given below are my contributions to the project.

* **New Feature**:
  * Add Assets Feature [#137](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/137)
    * Implemented an `Asset` class to represent an asset, containing its name, type, value and remarks.
    * Our users will be able to store asset information about their clients.
    * These assets are stored as a `List` of assets in `Person` since one client can have multiple assets.
    * As such, for storage purposes, a new class `JsonAdaptedAsset` was created to store each individual assets.
    * Our JSON file now holds nested JSON objects, one of them is assets.
  * Add Policies Feature [#137](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/137)
    * Implemented a `Policy` class to represent a policy, containing its name, type, insured value, yearly premiums and commissions.
    * Our users will be able to store policy information about their clients.
    * These policies are stored as a `List` of policies in `Person` since one client can have multiple policies.
    * As such, for storage purposes, a new class `JsonAdaptedPolicy` was created to store each individual policy.
    * Our JSON file now holds nested JSON objects, one of them is policies.
  * Add Liabilities Feature [#137](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/137)
    * Implemented an `Liability` class to represent an asset, containing its name, type, value and remarks.
    * Our users will be able to store liability information about their clients.
    * These liabilities are stored as a `List` of policies in `Person` since one client can have multiple liabilities.
    * As such, for storage purposes, a new class `JsonAdaptedLiability` was created to store each individual liability.
    * Our JSON file now holds nested JSON objects, one of them is liabilities.
  * Storage [#137](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/137)
    * As mentioned above, due our decision to create a nested JSON as our data, it differed from AB3 substantially.
    * `JsonAdaptedLiability`, `JsonAdaptedPolicy` and `JsonAdaptedAsset` all have its own `JsonProperty` fields and 
    are stored as a `List` inside `JsonAdaptedPerson`
  
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=sheehui&tabRepo=AY2122S1-CS2103T-W16-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

* **Project management**:


* **Enhancements to existing features**:
  * Testings: 
    * Add `Asset`, `Policy`, `Liability` and `Notes` testings. [#172](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/172), [#193](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/193)
      * ensure null values are not accepted by any field
      * add and ensure regex validation + error message handling
    * Improve test quality in parsers [#346](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/346/files) 
    * Add tests for valid commands in wrong window [#349](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/349)
      * created `ParserStrategyTestUtil` for cleaner test code
      * tested `clientViewParser` command
      * tested error message handling for valid commands in wrong window

  * Bug fixes:
    * Ensure single-word commands cannot be activated when input is more than one word. Add support of upper/lower cases in command word. [#144](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/144)
    * Valid command in wrong window should show an appropriate message as it is not an unknown command [#306](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/306)
    * Fix index larger than MAX_VALUE showing different error message [#303](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/303)

  * Enhancements:
    * Amend `SampleDataUtil` functions to fit with the new `Asset`, `Liability` and `Policy` structure and how it interacts with `Person`.[#137](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/137)
    * Amend `Person` equals function to have the flexibility to compare all its attributes or only compare basic contact information. [#137](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/137)
    * Remove `StatusBarFooter` [#94](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/94)
      * We decided that it was not necessary for our application.
    * Refactor directory from seedu.address to donnafin [#83](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/83)
    * Add css to horizontal tabs (when it existed)  [#97](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/97)
    * Remove traces of AB3 + change person -> client [#41](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/41), [#94](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/94), [#328](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/328), [#293](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/293), [#328](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/328)
    * Remove Edit command and tags [#56](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/56), [#246](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/246)
      * We no longer support tagging as it does not fit our use case
      * AB3 edit command is not relevant to how our edits will be conducted
    * Map escape key to home window (since removed) [#80](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/80)
    * Add function to trigger edit for specific contact fields [#82](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/82)
    * Add fxml files for home and client window switching [#62](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/62)
    * Improve code quality [#328](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/328)
* **Documentation**:
    * User Guide:

    * Developer Guide:


* **Community**:

* **Tools**:

