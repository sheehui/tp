---
layout: page
title: Tee Chin's Project Portfolio Page
---

### Project: AddressBook Level 3

DonnaFin.io is a desktop address book application used by financial advisors to keep track of their client information and related tasks.
The user interacts with it using a CLI, and it has a GUI created with JavaFX.
It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: 
    1. **Parser Stategy and Parser Context**
        * **What it does:**
        Allows for switching of `ParserStrategy` when executing a command. This happens
        during the change of tabs and allows some commands to be rejected in certain tabs
        and not rejected in others.
                
        * **Justification:**
        Current problem: Had to find a way to swap between Parsers to reject some commands
        First solution: Have some massive conditional operator to handle it.
        Reason for rejecting: Not feasible to write that much duplicate code.
        
        * **Highlights:**
        Strategy designed was used to work around the issue. We used it to isolate the 
        business logic of a class from the implementation details.  
        
        * **Credits:**
        Employed the Strategy pattern found in [Dive into design patterns](https://refactoring.guru/design-patterns/strategy) 
        
        * **PR:** [ParserContext](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/179)
    
    2. **Controllers for UI, `ClientPanel`:**
    
        * **What it does:**
        Handles the logic when accessing the other tabs etc
        
        * **Justification:**
        The app requires a controller to handle more logic than just the basic 
        javafx framework.
        
        * **PR:** [Controller](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/69)                                                    
        
    3. **Financial information:**
        * **What it does**
        Required field that stores information regarding the client. Namely, assets
        liabilities, policies.
        
        * **Justification**
        Required for our project as it is our key feature.
        
        * **PR:** : [Financial information](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/105)
    
    5. **Add different tabs for the other financial information:**
        * **What it does:**
        Create the actual tabs that the `SwitchTabCommand` can access
        
        * **Justification**
        The user would want to have tabs to organise his information, instead
        of just having one page display everything. Tabs are a very efficient way of doing that.
        
        * **PR:** [Add different tabs](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/86)  
     
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=Bluntsord&tabRepo=AY2122S1-CS2103T-W16-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)


* **Project management**:
    * Directed architecture changes alongside [Bharath](bharathcs.md):
        * Proposed architecture changes for the entire project.
        * Suggested the layered architecture as the best way to allow for better test and briefed each team mate on how
        to make the changes.
        [^layeredArchi]: [_O' Reilly_ Chapter 1. Layered Architecture](https://www.oreilly.com/library/view/software-architecture-patterns/9781491971437/ch01.html)
    * Provide contructive feedback for PRs:
        * Look through multiple PRs and provided further insights and corrections that could be done.
    
* **Enhancements to existing features**:
    1. Notable BugFix:
        *. **Realtime update for notes**
            Previously, our commands would only save after executing another command. 
            * **PR**: [#307](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/307)
            
        *. **Make Attribute table expand when window expands**
            * **PR**: [#304](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/304)
                 
        *. **Add wrap to multiple text box**
            * **PR**: [#288](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/288)
            * **PR**: [#298](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/298)
            
    2. Provided architecture advise:
        * Proposed architecture changes for the entire project along with my other teammate ![Bharath](bharathcs.md)
        [^layeredArchi]: [_O' Reilly_ Chapter 1. Layered Architecture](https://www.oreilly.com/library/view/software-architecture-patterns/9781491971437/ch01.html)
 
    3. **Add home command**:
       * **What it does:**
       Allows the user to return to the home page.
       
       * **Justification:**
       The user needs to be able to navigate between tabs and the home screen, having home
       would be an appropriate command.
       
       * **PR:** [Home command](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/76/files) 
        
* **Documentation**:
  * User Guide:
    1. Create the DonnaFin logo and updated the introduction. [#320](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/320)
    2. Updated the guide for duplicates and near duplicates. [#302](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/302)
  * Developer Guide: 
    1. Update architecture diagram (Pull requests [\#23](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/23))
    2. Created and handled puml diagrams for most of Section 4: Architecture 
    and Implementation part of our dev guide. Notable
    contributions include how `ParserContext` and `ParserStrategy` works, how commands are being execute, and even 
    partitioning the command into the three main categories, Model-level, Client-Level and Ui-browsing level commands.
    After which going into detail about how each work. [#323](https://github.com/AY2122S1-CS2103T-W16-1/tp/pull/323)
    (Disclaimer: [Bharath](bharathcs.md) has made much contributions in this aspect as well)
    
    3. Section 6.2: User stories. Created most of the user stories there are currently seen in the developer's guide.
    
* **Community**:
    

