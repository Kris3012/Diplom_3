package tests;

import io.qameta.allure.Step;
import org.junit.Test;
import page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.text;
import static org.junit.Assert.assertTrue;

public class ConstructorTest extends BaseTest {

    private final String baseUrl = "https://stellarburgers.nomoreparties.site/";

    @Test
    public void testTabsSwitching() {
        openMainPage();
        clickAndCheckTab("Булки");
        clickAndCheckTab("Соусы");
        clickAndCheckTab("Начинки");
    }

    @Test
    public void testLogoReturnsToConstructor() {
        openMainPage();
        navigateToAnotherPage();
        clickLogo();
        checkConstructorIsVisible();
    }

    @Step("Открываем главную страницу")
    private void openMainPage() {
        open(baseUrl);
    }

    @Step("Кликаем на вкладку '{tabName}' и проверяем, что она активна")
    private void clickAndCheckTab(String tabName) {
        MainPage mainPage = new MainPage();
        switch (tabName) {
            case "Булки":
                mainPage.clickBunSection();
                break;
            case "Соусы":
                mainPage.clickSauceSection();
                break;
            case "Начинки":
                mainPage.clickFillingSection();
                break;
            default:
                throw new IllegalArgumentException("Неизвестная вкладка: " + tabName);
        }
        assertTabIsActive(mainPage, tabName);
    }

    @Step("Проверяем, что вкладка '{tabName}' активна")
    private void assertTabIsActive(MainPage mainPage, String tabName) {
        mainPage.getCurrentTab()
                .shouldBe(visible)
                .shouldHave(text(tabName));
    }

    @Step("Переходим на другую страницу (клик на кнопку входа)")
    private void navigateToAnotherPage() {
        MainPage mainPage = new MainPage();
        mainPage.clickLoginButton();
    }

    @Step("Кликаем на логотип")
    private void clickLogo() {
        MainPage mainPage = new MainPage();
        mainPage.clickLogo();
    }

    @Step("Проверяем, что отображается конструктор")
    private void checkConstructorIsVisible() {
        MainPage mainPage = new MainPage();
        assertTrue("Должен отображаться конструктор после клика на логотип", mainPage.isConstructorVisible());
    }
}
