package tests;

import client.UserApiClient;
import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import page.LoginPage;
import page.MainPage;
import page.RegisterPage;
import page.ResetPasswordPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.assertTrue;

public class LoginTest extends BaseTest {

    private final String baseUrl = "https://stellarburgers.nomoreparties.site/";
    private final UserApiClient userApiClient = new UserApiClient();

    private String testEmail;
    private final String testPassword = "123456";
    private String accessToken;

    @Before
    public void setUp() {
        testEmail = "testuser_" + System.currentTimeMillis() + "@mail.ru";
        accessToken = userApiClient.register(testEmail, testPassword);
        open(baseUrl);
    }

    @After
    public void tearDown() {
        closeWebDriver();
        if (accessToken != null) {
            userApiClient.deleteUser(accessToken);
        }
    }

    @Test
    public void loginViaLoginButtonOnMainPage() {
        openLoginFromMainPageAndLogin(testEmail, testPassword);
    }

    @Test
    public void loginViaPersonalAccountButton() {
        openLoginFromPersonalAccountAndLogin(testEmail, testPassword);
    }

    @Test
    public void loginViaRegisterPageLink() {
        openLoginFromRegisterPageAndLogin(testEmail, testPassword);
    }

    @Test
    public void loginViaResetPasswordPageLink() {
        openLoginFromResetPasswordPageAndLogin(testEmail, testPassword);
    }

    @Step("Открываем форму входа с главной страницы и логинимся как пользователь '{email}'")
    private void openLoginFromMainPageAndLogin(String email, String password) {
        MainPage mainPage = new MainPage();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage();
        loginPage.loginAs(email, password);

        verifyLoginSuccessful();
    }

    @Step("Открываем форму входа через кнопку 'Личный кабинет' и логинимся как пользователь '{email}'")
    private void openLoginFromPersonalAccountAndLogin(String email, String password) {
        MainPage mainPage = new MainPage();
        mainPage.clickPersonalAccountButton();

        LoginPage loginPage = new LoginPage();
        loginPage.loginAs(email, password);

        verifyLoginSuccessful();
    }

    @Step("Переходим из страницы регистрации к форме входа и логинимся как пользователь '{email}'")
    private void openLoginFromRegisterPageAndLogin(String email, String password) {
        MainPage mainPage = new MainPage();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage();
        loginPage.clickRegisterLink();

        RegisterPage registerPage = new RegisterPage();
        registerPage.clickLoginLink();

        LoginPage loginPageAgain = new LoginPage();
        loginPageAgain.loginAs(email, password);

        verifyLoginSuccessful();
    }

    @Step("Переходим из страницы восстановления пароля к форме входа и логинимся как пользователь '{email}'")
    private void openLoginFromResetPasswordPageAndLogin(String email, String password) {
        MainPage mainPage = new MainPage();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage();
        loginPage.clickForgotPasswordLink();

        ResetPasswordPage resetPasswordPage = new ResetPasswordPage();
        resetPasswordPage.clickLoginLink();

        LoginPage loginPageAgain = new LoginPage();
        loginPageAgain.loginAs(email, password);

        verifyLoginSuccessful();
    }

    @Step("Проверяем, что после логина открылся конструктор бургера")
    private void verifyLoginSuccessful() {
        $x("//h1[text()='Соберите бургер']").shouldBe(Condition.visible);
        assertTrue("Проверка успешного входа: отображается конструктор", new MainPage().isConstructorVisible());
    }
}
