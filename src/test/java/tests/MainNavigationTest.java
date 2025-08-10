package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertEquals;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;

public class MainNavigationTest {

    private MainPage mainPage;

    @Before
    public void setUp() {
        open("https://stellarburgers.nomoreparties.site/");
        mainPage = new MainPage();
    }

    @After
    public void tearDown() {
        closeWebDriver();
    }

    @Test
    public void testBunsTabIsActive() {
        assertEquals("Булки", mainPage.getCurrentTabText());
    }

    @Test
    public void testSaucesTabIsActive() {
        mainPage.clickTab("Соусы");
        assertEquals("Соусы", mainPage.getCurrentTabText());
    }

    @Test
    public void testFillingsTabIsActive() {
        mainPage.clickTab("Начинки");
        assertEquals("Начинки", mainPage.getCurrentTabText());
    }
}
