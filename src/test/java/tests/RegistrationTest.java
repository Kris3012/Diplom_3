package tests;

import client.UserApiClient;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import page.MainPage;
import page.RegisterPage;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.assertTrue;

public class RegistrationTest {

    private final String baseUrl = "https://stellarburgers.nomoreparties.site/";
    private final String name = "Тест";
    private String email;
    private final String shortPassword = "123";
    private final String validPassword = "P@ssw0rd123!";

    private final UserApiClient userApiClient = new UserApiClient();

    @Before
    public void setUp() {
        email = "testuser" + System.currentTimeMillis() + "@example.com";
        open(baseUrl);
    }

    @After
    public void tearDown() {
        // Удаляем пользователя, если он успел зарегистрироваться
        userApiClient.deleteUser(email, validPassword);
        closeWebDriver();
    }

    @Test
    @Step("Проверка успешной регистрации через UI")
    public void testSuccessfulRegistration() {
        openLoginPage();
        goToRegistrationPage();
        registerNewUser(name, email, validPassword);
        loginUser(email, validPassword);
        verifyOrderButtonVisible();
    }

    @Test
    @Step("Проверка ошибки при слишком коротком пароле")
    public void testShortPasswordShowsError() {
        String emailShortPass = "shortpass" + System.currentTimeMillis() + "@example.com";
        openLoginPage();
        goToRegistrationPage();
        registerNewUser(name, emailShortPass, shortPassword);
        verifyPasswordErrorVisible();
        // Нет необходимости удалять пользователя, так как регистрация неуспешна
    }

    @Step("Открываем страницу логина с главной")
    private void openLoginPage() {
        new MainPage().clickLoginButton();
    }

    @Step("Переходим на страницу регистрации из страницы логина")
    private void goToRegistrationPage() {
        new LoginPage().clickRegisterLink();
    }

    @Step("Регистрируем нового пользователя с именем: {name}, email: {email}")
    private void registerNewUser(String name, String email, String password) {
        new RegisterPage()
                .enterName(name)
                .enterEmail(email)
                .enterPassword(password)
                .clickRegisterButton();
    }

    @Step("Входим пользователем с email: {email}")
    private void loginUser(String email, String password) {
        new LoginPage()
                .enterEmail(email)
                .enterPassword(password)
                .clickLoginButton();
    }

    @Step("Проверяем, что кнопка 'Оформить заказ' видна на главной")
    private void verifyOrderButtonVisible() {
        assertTrue("Кнопка 'Оформить заказ' не отображается",
                new MainPage().isOrderButtonVisible());
    }

    @Step("Проверяем, что отображается ошибка о некорректном пароле")
    private void verifyPasswordErrorVisible() {
        assertTrue("Ошибка о коротком пароле не отображается",
                new RegisterPage().isPasswordErrorVisible());
    }
}
