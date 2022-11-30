# Group Assignment: Data Structures and Algorithms

## Description

Work in a group setting to enhance our existing Banker application.  Read a JSON file accounts.json Download accounts.json to initialize accounts.  Allow the user to create new accounts with the Swing GUI.  Allow the user to make a withdrawal from accounts, and automatically pick the account that pays the lowest interest rate to deduct the money withdrawn.  Show a report of interest earned.  Host the project in GitHub, with all team members contributing.  Include a README.md in the GitHub repository, which includes a proper UML class diagram.  Write code according to best practices, and do something extra, beyond the minimum requirements stated here.

## Organization

Consider dividing work up by member:

- Administer GitHub Repository.
- Create README.md
- Create class diagram, and integrate into GitHub repository.
- Adjust Swing UI.
- Read JSON file: accounts.json Download accounts.json
- Write Unit Tests.
- Allow withdrawals, by lowest interest rate account first.
- Create a report of interest earned.

## Requirements and Self-Grade Sheet

Submit this self-grade sheet along with your project.

- Have a GitHub repository, with commits from all teammates.
- This repository should have a README.md describes your project. __/10
- Create a class diagram in draw.io and include it in your README.md __/10
- Start with the program that collects accounts.
- Initialize your accounts by reading the provided JSON file, accounts.json Download accounts.json.  Allow the user to create additional accounts with the GUI. __/10
- Do not allow the user to create duplicate accounts, or duplicate account numbers. __/10
  - Add an account number attribute to class Account, and a corresponding field to the GUI.
  - Place all used account numbers in a Set.
  - When the user enters an account, validate that it is not currently in the Set of account numbers.
- Allow the user to withdraw money from accounts.  Start by withdrawing from the account that pays the lowest interest
  - Adjust the GUI to allow withrdawls.  __/10
  - Place accounts in a priority queue, based on rate.
  - Use Comparator/compareTo to compare rates.  __/10
  - Allow partial withdraws from accounts; simply update principle.
- Report total interest earned, across all accounts, for a given period. __/10
  - Iterate over the collection.  Compute interest earned.  Store interest earned from each account in a collection (ArrayList, etc.)  Iterate and sum.
- Test your work. __/10
  - Write unit tests to cover the cases above.
  - Ensure they run with GitHub actions.  
- Code is in good form, and conforms to best practices.  Classes and public methods contain JavaDoc. __/10
- Do something extra, beyond the minimum requirements stated here.  __/10
  - What did you do?  Must list to earn credit.
  - stuff goes here
