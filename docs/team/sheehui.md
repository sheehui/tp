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
  * Set up DonnaFin's GitHub team organisation and repository
  * Renaming AB3 to DonnaFin during refactoring [#41](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/41), and catching traces of AB3 when working on other features/work.
  * Documented project README, target user profile and value proposition
  * Shared responsibility to maintain issues
  * Changes AB3 links to DonnaFin in project README and our GitHub Pages

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
    * More storage tests + null tests for `JsonAdaptedLiability`, `JsonAdaptedPolicy` and `JsonAdaptedAsset` [#349](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/349)
      * This is to prevent `NullPointerException` from occurring, which completely breaks the app.
      * Prevent duplicates/ purposeful insertion of null value

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
      * this happens consistently throughout my contributions that are hard to record at times
    * Remove Edit command and tags [#56](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/56), [#246](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/246)
      * We no longer support tagging as it does not fit our use case
      * AB3 edit command is not relevant to how our edits will be conducted
    * Update Project README to match DonnaFin [#30](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/30)
    * Map escape key to home window (since removed) [#80](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/80)
    * Add function to trigger edit for specific contact fields [#82](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/82)
    * Add fxml files for home and client window switching [#62](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/62)
    * Improve code quality [#328](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/328)
      * added loggers as well

* **Documentation**:
    * User Guide [#163](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/163), [#187](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/187), [#240](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/240), [#312](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/312), [#335](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/335), [#342](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/342) :
      * Sectioned our features into global, client window, home window and database
      * Wrote the guide for these features: `add`, `delete`, `view`, `tab`, `edit`, `append`, `remove`, `home` and editing notes
      * Add tips and notes to help the users understand better
      * Wrote the Command Summary and added links to each command for better user experience

    * Developer Guide [#43](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/43), [#28](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/28), [#326](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/326), [#349](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/349):
      * Transferred our initial user stories to our repo
      * Added target user and value proposition
      * update UML description for Ui and Logic [#171](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/171)
        * Added new ClientPanel and explained our `ParserStrategy` and `ParserContext` implementation
      * Explain how storage for `Asset`, `Policy`, `Liability` work using `JsonAdaptedLiability`, `JsonAdaptedPolicy` and `JsonAdaptedAsset`
        * nested JSON objects are stored and implemented differently
      * Add use cases for `Asset`, `Policy`, `Liability` features


* **Community**:
  * Constructive feedback: [#250](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/250), [#148](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/148), [#141](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/141), [#134](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/134), [#126](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/126), [#337](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/337), [#105](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/105), [#341](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/341), [#357](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/357), [#356](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/356)
  * Feedback on quality: [#319](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/319), [#297](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/297), [#320](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/320), [#268](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/268), [#95](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/95)

