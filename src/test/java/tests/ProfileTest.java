package tests;

import client.UserApiClient;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import page.MainPage;
import page.ProfilePage;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertTrue;

public class ProfileTest {

    private final String baseUrl = "https://stellarburgers.nomoreparties.site/";
    private final UserApiClient userApiClient = new UserApiClient();

    private String testEmail;
    private final String testPassword = "123456";
    private String accessToken;

    private MainPage mainPage;
    private LoginPage loginPage;
    private ProfilePage profilePage;

    @Before
    public void setUp() {
        testEmail = "testuser_" + System.currentTimeMillis() + "@mail.ru";
        accessToken = userApiClient.register(testEmail, testPassword);

        mainPage = new MainPage();
        loginPage = new LoginPage();
        profilePage = new ProfilePage();
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userApiClient.deleteUser(accessToken);
        }
    }

    @Test
    public void testNavigateToProfile() {
        openMainPageAndLogin();
        clickPersonalAccountButton();
        checkInProfile();
    }

    @Test
    public void testLogoutFromProfile() {
        openMainPageAndLogin();
        clickPersonalAccountButton();
        logoutFromProfile();
        checkReturnedToLogin();
    }

    @Test
    public void testNavigateFromProfileToConstructor() {
        openMainPageAndLogin();
        clickPersonalAccountButton();
        goToConstructorFromProfile();
        checkConstructorVisible();
    }

    @Test
    public void testNavigateFromProfileViaLogo() {
        openMainPageAndLogin();
        clickPersonalAccountButton();
        clickLogoInProfile();
        checkConstructorVisible();
    }

    // ---------- ШАГИ ----------

    @Step("Открываем главную страницу и логинимся под пользователем: {email}")
    private void openMainPageAndLogin() {
        open(baseUrl);
        mainPage.clickLoginButton();
        loginPage.loginAs(testEmail, testPassword);
        mainPage.getConstructorHeader().shouldBe(com.codeborne.selenide.Condition.visible);
    }

    @Step("Кликаем по кнопке 'Личный кабинет'")
    private void clickPersonalAccountButton() {
        mainPage.clickPersonalAccountButton();
    }

    @Step("Проверяем, что мы находимся в личном кабинете")
    private void checkInProfile() {
        assertTrue("Ожидалось, что мы в профиле", profilePage.isInProfile());
    }

    @Step("Выходим из личного кабинета")
    private void logoutFromProfile() {
        profilePage.clickLogoutButton();
    }

    @Step("Проверяем, что отображается форма логина")
    private void checkReturnedToLogin() {
        assertTrue("Ожидалась форма входа", loginPage.isLoginFormVisible());
    }

    @Step("Переходим в 'Конструктор' из личного кабинета")
    private void goToConstructorFromProfile() {
        profilePage.clickConstructorButton();
    }

    @Step("Кликаем по логотипу Stellar Burgers из личного кабинета")
    private void clickLogoInProfile() {
        profilePage.clickStellarBurgersLogo();
    }

    @Step("Проверяем, что отображается 'Конструктор'")
    private void checkConstructorVisible() {
        assertTrue("Ожидался видимый конструктор", mainPage.isConstructorVisible());
    }
}
