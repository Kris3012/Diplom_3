package tests;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import page.MainPage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.assertEquals;

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
        verifyCurrentTabText("Булки");
    }

    @Test
    public void testSaucesTabIsActive() {
        clickTab("Соусы");
        verifyCurrentTabText("Соусы");
    }

    @Test
    public void testFillingsTabIsActive() {
        clickTab("Начинки");
        verifyCurrentTabText("Начинки");
    }

    @Step("Кликаем на вкладку с текстом: {tabName}")
    private void clickTab(String tabName) {
        ElementsCollection tabs = $$("div.tab_tab__1SPyG");
        SelenideElement tab = tabs.findBy(text(tabName)).shouldBe(visible);
        tab.click();  // простой клик без Actions
        tab.shouldHave(cssClass("tab_tab_type_current__2BEPc"));
    }

    @Step("Проверяем, что активная вкладка отображает текст: {expectedText}")
    private void verifyCurrentTabText(String expectedText) {
        mainPage.getCurrentTab()
                .shouldBe(visible)
                .shouldHave(text(expectedText));
        String actualText = mainPage.getCurrentTab().getText();
        assertEquals(expectedText, actualText);
    }
}
