package tests;

import client.UserApiClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import page.MainPage;
import page.RegisterPage;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
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
        userApiClient.deleteUser(email, validPassword);
    }

    @Test
    public void testSuccessfulRegistration() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();

        RegisterPage registerPage = loginPage.clickRegisterLink();

        registerPage.registerNewUser(name, email, validPassword);

        loginPage = new LoginPage();
        loginPage.loginAs(email, validPassword);

        mainPage = new MainPage();
        assertTrue("Кнопка 'Оформить заказ' не отображается", mainPage.isOrderButtonVisible());
    }

    @Test
    public void testShortPasswordShowsError() {
        String emailShortPass = "shortpass" + System.currentTimeMillis() + "@example.com";

        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();

        RegisterPage registerPage = loginPage.clickRegisterLink();

        registerPage.registerNewUser(name, emailShortPass, shortPassword);

        assertTrue("Ошибка о коротком пароле не отображается", registerPage.isPasswordErrorVisible());
    }
}
