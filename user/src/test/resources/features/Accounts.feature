Feature: I should be able to perform internal operations on Accounts such as:
  CREATE, GET one, PATCH, DELETE, GET all accounts.

  Scenario Outline: I CREATE a valid account.
    Given I send an account creation with the following fields: "<Username>", "<Email>", "<Password>"
    And the response should contain an Account with the fields: "<Username>", "<Email>", "<Password>"
    Examples:
      | Username     | Email               | Password
      | Username0001 | Email0001@email.net | Password123
      | Username0002 | Email0002@email.net | Passwords1412

  Scenario Outline: I CREATE an account where validation fails.
    Given I send an account creation with the following fields: "<Username>", "<Email>", "<Password>"
    Then The response should only have a subError matching an "<KeyValidationName>"
    Examples:
      | Username      | Email               | Password    | KeyValidationName                    |
      | DUPLICATEUSER | Email0003@email.net | Password123 | ApiValidationError_DuplicateUsername |
      | Username0003  | DUPLICATE@email.net | Password123 | ApiValidationError_DuplicateEmail    |
      | Us            | Email0003@email.net | Password123 | ApiValidationError_TooShortUsername  |
      | Username0003  | Email003            | Password123 | ApiValidationError_InvalidEmail      |
      |               | Email003@email.net  | Password123 | ApiValidationError_NullUsername      |
      |               | Email003@email.net  |             | ApiValidationError_NullPassword      |

  Scenario: I RETRIEVE using its id.

    Given I send an account creation with the following fields: "<Username>", "ValidEmail@gmail.com", "<Password>"
    And the response should contain an Account with the fields: "<Username>", "ValidEmail@gmail.com", "<Password>"
    When I retrieve an account using the last created account id.

  Scenario: I UPDATE an account using its id
    Given I send an account creation with the following fields: "UpdateMe", "UpdateMe@gmail.com", "Password123"
    And the response should contain an Account with the fields: "UpdateMe", "UpdateMe@gmail.com", "Password123"
    Then I update the Username of the last created account to "NewUsername"
    And the response should contain an Account with the fields: "NewUsername", "UpdateMe@gmail.com", "Password123"

#Exception Cases

  Scenario: I RETRIEVE an account using an id that doesnt exist.
    Given I retrieve an account using an id that has not been created
    Then I should receieve a response with the status code "404"