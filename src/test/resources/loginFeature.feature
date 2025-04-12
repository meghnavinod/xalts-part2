Feature: SignIn functionality for a web application


  Scenario: Successful Sign In with valid credentials
    Given the user is on the Sign In Page
    When the user enters valid email Address,Password correctly
    When  user clicks on the Sign In button
    Then the user has to be redirected to the homepage

  Scenario: UnSuccessful Sign In with Wrong Password
    Given the user is on the Sign In Page
    When the user enters valid email Address but Wrong Password
    When  user clicks on the Sign In button
    Then Pop message is  displayed saying "Incorrect E-Mail or Password"

  Scenario: UnSuccessful Sign In with Invalid email
    Given the user is on the Sign In Page
    When the user enters invalid email Address but Right Password
    Then SignIn Button is Disabled

  Scenario: UnSuccessful Sign In When Password Not Meet Requirements
    Given the user is on the Sign In Page
    When the user enters valid email Address but  Password is invalid
    Then SignIn Button is Disabled for Password

  Scenario: UnSuccessful Sign With SQL Injection Attempt
    Given the user is on the Sign In Page
    When the user enters valid email Address with OR and enters Password
    When  user clicks on the Sign In button
    Then Alert Message shows "undefined"

  Scenario: UnSuccessful Sign In when All the fields Left Blank
    Given the user is on the Sign In Page
    When the user leaves all the fields blank in SignIn Page
    Then SignIn Button is Disabled

  Scenario: UnSuccessful Sign With Not Registered User
    Given the user is on the Sign In Page
    When the user enters valid email Address and Password which is not registered
    When  user clicks on the Sign In button
    Then Pop message displayed saying "User not found"



