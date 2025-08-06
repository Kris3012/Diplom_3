package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
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

    public void clickLoginButton() {
        loginButton.shouldBe(visible).click();
        $x("//button[text()='Войти']").shouldBe(visible, Duration.ofSeconds(5));
    }

    public void clickLogo() {
        logoButton.shouldBe(visible).click();
    }

    public void clickConstructorButton() {
        constructorButton.shouldHave(text("Конструктор")).shouldBe(visible).click();
    }

    public void clickPersonalAccountButton() {
        personalAccountButton.shouldBe(visible).click();
    }

    public void clickBunSection() {
        SelenideElement el = tabs.findBy(text("Булки")).shouldBe(visible);
        new Actions(getWebDriver()).moveToElement(el).click().perform();
    }

    public void clickSauceSection() {
        SelenideElement el = tabs.findBy(text("Соусы")).shouldBe(visible);
        new Actions(getWebDriver()).moveToElement(el).click().perform();
    }

    public void clickFillingSection() {
        SelenideElement el = tabs.findBy(text("Начинки")).shouldBe(visible);
        new Actions(getWebDriver()).moveToElement(el).click().perform();
    }

    public SelenideElement getCurrentTab() {
        return $$("div.tab_tab__1SPyG").findBy(cssClass("tab_tab_type_current__2BEPc"));
    }

    public boolean isConstructorVisible() {
        return constructorHeader.shouldBe(visible).getText().contains("Соберите бургер");
    }

    public void waitForConstructor() {
        constructorHeader.shouldHave(text("Соберите бургер")).shouldBe(visible);
    }

    public boolean isLoginPageVisible() {
        return $("form").shouldBe(visible).exists();
    }

    public boolean isOrderButtonVisible() {
        return orderButton.shouldBe(visible, Duration.ofSeconds(6)).isDisplayed();
    }

    public SelenideElement getConstructorHeader() {
        return constructorHeader;
    }
}
