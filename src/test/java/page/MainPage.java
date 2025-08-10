package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class MainPage {

    private final SelenideElement loginButton = $x("//button[contains(text(),'Войти в аккаунт')]");
    private final SelenideElement constructorButton = $("p.AppHeader_header__linkText__3q_va.ml-2");
    private final SelenideElement constructorHeader = $("h1");
    private final SelenideElement personalAccountButton = $x("//p[contains(@class,'AppHeader_header__linkText__3q_va') and contains(@class,'ml-2') and (text()='Личный Кабинет')]");
    private final SelenideElement logoButton = $("div.AppHeader_header__logo__2D0X2");
    private final SelenideElement orderButton = $x("//button[text()='Оформить заказ']");
    private final ElementsCollection tabs = $$("div.tab_tab__1SPyG");

    @Step("Кликаем кнопку 'Войти'")
    public LoginPage clickLoginButton() {
        loginButton.shouldBe(visible).click();
        $x("//button[text()='Войти']").shouldBe(visible, Duration.ofSeconds(5));
        return new LoginPage();
    }

    @Step("Кликаем по логотипу Stellar Burgers")
    public MainPage clickLogo() {
        logoButton.shouldBe(visible).click();
        return this;
    }

    @Step("Кликаем кнопку 'Конструктор'")
    public MainPage clickConstructorButton() {
        constructorButton.shouldHave(text("Конструктор")).shouldBe(visible).click();
        return this;
    }

    @Step("Кликаем кнопку 'Личный кабинет'")
    public MainPage clickPersonalAccountButton() {
        personalAccountButton.shouldBe(visible).click();
        return this;
    }

    @Step("Кликаем по вкладке '{tabName}' с JS кликом и прокруткой")
    public MainPage clickTab(String tabName) {
        SelenideElement tab = tabs.findBy(text(tabName)).shouldBe(visible);
        tab.scrollTo();
        tab.shouldBe(visible, enabled);

        executeJavaScript("arguments[0].click();", tab);

        tabs.findBy(cssClass("tab_tab_type_current__2BEPc"))
                .shouldHave(text(tabName))
                .shouldBe(visible, Duration.ofSeconds(5));

        return this;
    }

    @Step("Получаем текст активной вкладки")
    public String getCurrentTabText() {
        return tabs.findBy(cssClass("tab_tab_type_current__2BEPc")).shouldBe(visible).getText();
    }

    @Step("Проверяем, что отображается конструктор")
    public boolean isConstructorVisible() {
        constructorHeader.shouldBe(visible);
        return constructorHeader.getText().contains("Соберите бургер");
    }

    @Step("Проверяем, что отображается форма логина")
    public boolean isLoginPageVisible() {
        return $("form").shouldBe(visible).exists();
    }

    @Step("Проверяем, что кнопка 'Оформить заказ' видна")
    public boolean isOrderButtonVisible() {
        return orderButton.shouldBe(visible, Duration.ofSeconds(6)).isDisplayed();
    }
}
