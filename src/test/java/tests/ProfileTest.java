package tests;

import client.UserApiClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import page.LoginPage;
import page.MainPage;
import page.ProfilePage;

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
        open(baseUrl);
        mainPage.clickLoginButton();
        loginPage.loginAs(testEmail, testPassword);
        mainPage.clickPersonalAccountButton();
        assertTrue(profilePage.isInProfile());
    }

    @Test
    public void testLogoutFromProfile() {
        open(baseUrl);
        mainPage.clickLoginButton();
        loginPage.loginAs(testEmail, testPassword);
        mainPage.clickPersonalAccountButton();
        profilePage.clickLogoutButton();
        assertTrue(loginPage.isLoginFormVisible());
    }

    @Test
    public void testNavigateFromProfileToConstructor() {
        open(baseUrl);
        mainPage.clickLoginButton();
        loginPage.loginAs(testEmail, testPassword);
        mainPage.clickPersonalAccountButton();
        profilePage.clickConstructorButton();
        assertTrue(mainPage.isConstructorVisible());
    }

    @Test
    public void testNavigateFromProfileViaLogo() {
        open(baseUrl);
        mainPage.clickLoginButton();
        loginPage.loginAs(testEmail, testPassword);
        mainPage.clickPersonalAccountButton();
        profilePage.clickStellarBurgersLogo();
        assertTrue(mainPage.isConstructorVisible());
    }
}
