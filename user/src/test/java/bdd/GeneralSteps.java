// Copyright (c) 2019 Travelex Ltd

package bdd;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.fail;

import bdd.rest.RestClient;
import com.common.models.dtos.AccountDto;
import com.common.models.exceptions.ApiValidationError;
import com.common.models.responses.EntityModificationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class GeneralSteps extends SpringBootBaseIntegrationTest {

    private RestClient restClient;

    private ObjectMapper objectMapper;

    private String userUrl = "http://localhost:13001/user/accounts";

    private Map<String, ApiValidationError> errorNameToErrorResponseMap;

    public GeneralSteps() {
        errorNameToErrorResponseMap = new HashMap<>();
        restClient = new RestClient();
        objectMapper = new ObjectMapper();
        createDuplicateUserAndEmailAccounts();
        ApiValidationError duplicateUsername = ApiValidationError.builder().field("Username").message("That username is already in use.").build();
        errorNameToErrorResponseMap.put("ApiValidationError_DuplicateUsername", duplicateUsername);
        ApiValidationError duplicateEmail = ApiValidationError.builder().field("Email").message("That email is already in use.").build();
        errorNameToErrorResponseMap.put("ApiValidationError_DuplicateEmail", duplicateEmail);
        ApiValidationError nullPassword = ApiValidationError.builder().field("hashedPassword").message("must not be null").build();
        errorNameToErrorResponseMap.put("ApiValidationError_NullPassword", nullPassword);
        ApiValidationError nullEmail = ApiValidationError.builder().field("email").message("must not be null").build();
        errorNameToErrorResponseMap.put("ApiValidationError_NullEmail", nullEmail);
        ApiValidationError nullUsername = ApiValidationError.builder().field("username").message("must not be null").build();
        errorNameToErrorResponseMap.put("ApiValidationError_NullUsername", nullUsername);
        ApiValidationError tooShortUsername = ApiValidationError.builder().field("username").message("size must be between 3 and 15").build();
        errorNameToErrorResponseMap.put("ApiValidationError_TooShortUsername", tooShortUsername);
        ApiValidationError wellFormedEmail = ApiValidationError.builder().field("email").message("must be a well-formed email address").build();
        errorNameToErrorResponseMap.put("ApiValidationError_InvalidEmail", wellFormedEmail);
    }

    @When("I login using correct credentials")
    public void iLoginUsingCorrectCredentials() throws InterruptedException {
    }

    @And("I send an account creation with the following fields: {string}, {string}, {string}")
    public void iSendAnAccountCreationWithTheFollowingFields(String username, String email, String password) {
        AccountDto createAccountRequest = new AccountDto();
        createAccountRequest.setUsername(username);
        createAccountRequest.setEmail(email);
        createAccountRequest.setHashedPassword(password);
        restClient.sendPostRequestToUrl(createAccountRequest, userUrl, String.class);
    }

    @And("the response should contain an Account with the fields: {string}, {string}, {string}")
    public void theResponseShouldContainAnAccountWithTheFields(String username, String email, String password) {
        ResponseEntity responseEntity = (ResponseEntity) restClient.lastResponse;
        EntityModificationResponse response =
                (EntityModificationResponse<AccountDto>) mapResponseEntityBody(responseEntity.getBody(), EntityModificationResponse.class);
        LinkedHashMap<String, Object> accountVariables = (LinkedHashMap<String, Object>) response.getEntity();
        assertThat(accountVariables.get("username").toString()).isEqualToIgnoringCase(username);
        assertThat(accountVariables.get("email").toString()).isEqualToIgnoringCase(email);
        lastCreatedAccountId = (Integer) accountVariables.get("id");
    }

    private Integer lastCreatedAccountId;

    @Then("I should receieve a response with the status code {string}")
    public void iShouldReceieveAResponseWithTheStatusCode(String statusCode) {
        assertThat(restClient.lastStatusCode).isEqualTo(Integer.parseInt(statusCode));
    }

    @When("I retrieve an account using the last created account id.")
    public void iRetrieveAnAccountUsingTheLastCreatedAccountId() {
        ResponseEntity entity = restClient.sendGetRequestToUrl(userUrl + "/" + lastCreatedAccountId.toString(), AccountDto.class);
        AccountDto account = (AccountDto) entity.getBody();
        assertThat(account.getId()).isEqualTo(lastCreatedAccountId);
    }

    @And("The response should only have a subError matching an {string}")
    public void theResponseShouldHaveASubErrorMatchingAn(String subErrorKeyName) {
        ResponseEntity entity = (ResponseEntity) restClient.lastResponse;
        EntityModificationResponse<AccountDto> entityModificationResponse =
                (EntityModificationResponse) mapResponseEntityBody(entity.getBody(), EntityModificationResponse.class);
        ApiValidationError apiValidationError = errorNameToErrorResponseMap.get(subErrorKeyName);
        for (ApiValidationError suberror : entityModificationResponse.getApiSubErrors()) {
            assertThat(suberror.getField().equals(apiValidationError.getField()));
            assertThat(suberror.getMessage().equals(apiValidationError.getMessage()));
        }
    }

    private void createDuplicateUserAndEmailAccounts() {
        iSendAnAccountCreationWithTheFollowingFields("DUPLICATEUSER", "someEmail@email.net", "asdada");
        iSendAnAccountCreationWithTheFollowingFields("SomeUsername", "DUPLICATE@email.net", "asdada");
    }

    private Object mapResponseEntityBody(Object entity, Class classToMap) {
        try {
            return objectMapper.readValue(entity.toString(), classToMap);
        } catch (IOException e) {
            fail("Failed to map a response entity: " + entity + " body to class: " + classToMap);
        }
        return null;
    }

    @Given("I retrieve an account using an id that has not been created")
    public void iRetrieveAnAccountUsingAnIdThatHasNotBeenCreated() {
        restClient.sendGetRequestToUrl(userUrl + "/4214", AccountDto.class);
    }

    @Then("I update the Username of the last created account to {string}")
    public void iUpdateTheUsernameOfTheLastCreatedAccountTo(String username) {
        AccountDto creationRequest = (AccountDto) restClient.lastRequest;
        creationRequest.setUsername(username);
        restClient.sendPostRequestToUrl(creationRequest, userUrl + "/" + lastCreatedAccountId, String.class);
    }
}