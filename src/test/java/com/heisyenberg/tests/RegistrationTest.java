package com.heisyenberg.tests;

import com.github.javafaker.Faker;
import com.heisyenberg.pages.BooksPage;
import com.heisyenberg.pages.LoginPage;
import com.heisyenberg.pages.RegistrationPage;
import com.heisyenberg.utils.DatabaseUtil;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.heisyenberg.data.BooksData.EMPTY_FIELD_ERROR;
import static com.heisyenberg.data.LoginData.ADMIN_EMAIL;
import static com.heisyenberg.data.LoginData.INVALID_EMAIL_ADDRESS_FORMAT;
import static com.heisyenberg.data.RegistrationData.PASSWORD_ERROR_MESSAGE;
import static com.heisyenberg.data.RegistrationData.RESERVED_EMAIL_ERROR_MESSAGE;
import static com.heisyenberg.data.RegistrationData.USERNAME_ERROR_MESSAGE;

@Feature("Registration page")
public class RegistrationTest extends BaseTest {
  private final Faker faker = new Faker();
  private LoginPage loginPage;
  private RegistrationPage registrationPage;

  @BeforeMethod
  public void setUp() {
    loginPage = open(BooksPage.PAGE_URL, BooksPage.class).clickLoginButton();
    registrationPage = loginPage.clickRegistrationLink();
  }

  @Story("Check registration with correct data")
  @Test(description = "Check registration with correct data")
  public void checkRegistrationWithCorrectData() {
    String username = faker.name().firstName();
    String email = faker.internet().emailAddress();
    String password = faker.internet().password();
    registrationPage
        .setUsername(username)
        .setEmail(email)
        .setPassword(password)
        .clickRegistrationSubmitButton();
    loginPage
        .checkOpen()
        .authorize(email, password)
        .checkOpen()
        .checkUserRoleHeader(username)
        .clickLogoutButton();
    DatabaseUtil.execute("DELETE FROM users WHERE email = ?;", email);
  }

  @Story("Check registration with empty username")
  @Test(description = "Check registration with empty username")
  public void checkRegistrationWithEmptyUsername() {
    registrationPage
        .clickRegistrationSubmitButton()
        .checkUsernameValidationMessage(EMPTY_FIELD_ERROR);
  }

  @Story("Check registration with empty email")
  @Test(description = "Check registration with empty email")
  public void checkRegistrationWithEmptyEmail() {
    registrationPage
        .setUsername(faker.name().firstName())
        .clickRegistrationSubmitButton()
        .checkEmailValidationMessage(EMPTY_FIELD_ERROR);
  }

  @Story("Check registration with invalid email")
  @Test(description = "Check registration with invalid email")
  public void checkRegistrationWithInvalidEmail() {
    String email = faker.name().firstName();
    registrationPage
        .setUsername(faker.name().firstName())
        .setEmail(email)
        .clickRegistrationSubmitButton()
        .checkEmailValidationMessage(String.format(INVALID_EMAIL_ADDRESS_FORMAT, email));
  }

  @Story("Check registration with empty password")
  @Test(description = "Check registration with empty password")
  public void checkRegistrationWithEmptyPassword() {
    registrationPage
        .setUsername(faker.name().firstName())
        .setEmail(faker.internet().emailAddress())
        .clickRegistrationSubmitButton()
        .checkPasswordValidationMessage(EMPTY_FIELD_ERROR);
  }

  @Story("Check registration with not long enough username")
  @Test(description = "Check registration with not long enough username")
  public void checkRegistrationWithNotLongEnoughUsername() {
    int desiredUsernameLength = 2;
    registrationPage
        .setUsername(faker.lorem().characters(desiredUsernameLength))
        .setEmail(faker.internet().emailAddress())
        .setPassword(faker.internet().password())
        .clickRegistrationSubmitButton()
        .checkUsernameErrorMessage(USERNAME_ERROR_MESSAGE);
  }

  @Story("Check registration with to long username")
  @Test(description = "Check registration with to long username")
  public void checkRegistrationWithToLongUsername() {
    int desiredUsernameLength = 21;
    registrationPage
        .setUsername(faker.lorem().characters(desiredUsernameLength))
        .setEmail(faker.internet().emailAddress())
        .setPassword(faker.internet().password())
        .clickRegistrationSubmitButton()
        .checkUsernameErrorMessage(USERNAME_ERROR_MESSAGE);
  }

  @Story("Check registration with reserved email")
  @Test(description = "Check registration with reserved email")
  public void checkRegistrationWithReservedEmail() {
    registrationPage
        .setUsername(faker.name().firstName())
        .setEmail(ADMIN_EMAIL)
        .setPassword(faker.internet().password())
        .clickRegistrationSubmitButton()
        .checkEmailErrorMessage(RESERVED_EMAIL_ERROR_MESSAGE);
  }

  @Story("Check registration with not long enough password")
  @Test(description = "Check registration with not long enough password")
  public void checkRegistrationWithNotLongEnoughPassword() {
    int desiredPasswordLength = 2;
    registrationPage
        .setUsername(faker.name().firstName())
        .setEmail(faker.internet().emailAddress())
        .setPassword(faker.lorem().characters(desiredPasswordLength))
        .clickRegistrationSubmitButton()
        .checkPasswordErrorMessage(PASSWORD_ERROR_MESSAGE);
  }

  @Story("Check registration with to long password")
  @Test(description = "Check registration with to long password")
  public void checkRegistrationWithToLongPassword() {
    int desiredPasswordLength = 31;
    registrationPage
        .setUsername(faker.name().firstName())
        .setEmail(faker.internet().emailAddress())
        .setPassword(faker.lorem().characters(desiredPasswordLength))
        .clickRegistrationSubmitButton()
        .checkPasswordErrorMessage(PASSWORD_ERROR_MESSAGE);
  }
}
