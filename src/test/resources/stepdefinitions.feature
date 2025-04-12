Feature: SignUp functionality for a web application


  Scenario: Successful Sign Up with valid credentials
    Given the user is on the Sign Up Page
    When the user enters valid email Address,Password and confirm password correctly
    When clicks on the SignUp button
    Then the user should be redirected to the homepage

  Scenario: UnSuccessful Sign Up with existing credentials
    Given the user is on the Sign Up Page
    When the user enters existing email Address,Password and confirm password
    When clicks on the SignUp button
    Then the user should get a pop up saying "Provided E-Mail is already in use"

  Scenario: UnSuccessful Sign Up with Invalid email
    Given the user is on the Sign Up Page
    When the user enters invalid email Address,Password and confirm password
    Then SignUp Button is Disabled

  Scenario: UnSuccessful Sign Up with Incomplete email
    Given the user is on the Sign Up Page
    When the user enters Incomplete email Address,Password and confirm password
    When clicks on the SignUp button
    Then the user should get a pop up says "undefined"

  Scenario: UnSuccessful Sign Up with Password Not meeting Password Criteria
    Given the user is on the Sign Up Page
    When the user enters valid email Address,Invalid Password and confirm password
    Then SignUp Button is Disabled

  Scenario: UnSuccessful Sign Up when Password does not match with confirm password
    Given the user is on the Sign Up Page
    When the user enters Password and  confirm password different
    Then SignUp Button is Disabled for this scenario

  Scenario: UnSuccessful Sign Up when All the fields Left Blank
    Given the user is on the Sign Up Page
    When the user leaves all the fields blank
    Then SignUp Button Responds disabled
