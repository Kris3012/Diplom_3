package tests;

import org.junit.Test;
import page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class ConstructorTest extends BaseTest {

    private final String baseUrl = "https://stellarburgers.nomoreparties.site/";

    @Test
    public void testTabsSwitching() {
        open(baseUrl);
        MainPage mainPage = new MainPage();

        mainPage.clickTab("Булки");
        assertEquals("Булки", mainPage.getCurrentTabText());

        mainPage.clickTab("Соусы");
        assertEquals("Соусы", mainPage.getCurrentTabText());

        mainPage.clickTab("Начинки");
        assertEquals("Начинки", mainPage.getCurrentTabText());
    }

    @Test
    public void testLogoReturnsToConstructor() {
        open(baseUrl);
        MainPage mainPage = new MainPage();
        mainPage.clickLoginButton();
        mainPage.clickLogo();
        assertTrue("Должен отображаться конструктор после клика на логотип", mainPage.isConstructorVisible());
    }
}
