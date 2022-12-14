# Self-Grade Sheet -  Zach Filla (M11078030)

- Effective use of GitHub  10/10
  - Project is properly pushed to GitHub. Feedback branch is not merged.
- Unit Tests Pass 10/10
  - Unit tests pass.  AccountTests.java has NOT been altered.
- Account class created, has compute method 10/10
  - Account class is present, and has compute() method to compute interest earned. The computations are correct.
- Savings class 10/10
  - Savings class is created and extends Account.
- CertificateOfDeposit Class 10/10
  - CertificateOfDeposit class is created, extends Account, and has an attribute and getter/setter for term.
- Checking Class 10/10
  - Checking extends Account.
  - It has a constant named FEE, set to 5.
  - Each time interest is computed, per period, subtract FEE from the balance.
- Banker: promptUser 10/10
  - Class Banker has a method called promptUser. This method uses a JOptionPane to gather information from the user to create an account: account type, balance, rate, and periods. It creates objects that subclass Account based on what the user selected: Checking, Savings, or Certificate of Deposit. It uses a loop to ask the user if he/she wants to create another account. All accounts are stored in an ArrayList of accounts.
- Banker: displayOutput 10/10
  - Class Banker has a displayOutput method that:
  - 1) Iterates over all accounts in the accounts ArrayList.
  - 2) Invokes compute() on each Account object.
  - 3) Displays the ending balance of each Account object.
- Do something extra 10/10
  - Add something to your project that is not already required in the project description/rubrics.  List what you did to get credit: 
    - I added `accountTypeString()` to `Banker.java` and used it to provide more information to the user to show what account's information was being shown. Since users can go through multiple accounts at once, they might get the order in which accounts were added mixed up. This implementation isn't perfect (it doesn't differentiate multiple accounts of the same type) but it should make it easier for users to identify the currently displayed account balance.
- Code is written according to best practices (variable and method naming, exception handling, syntax, format). Each class and public method has JavaDoc.  10/10
