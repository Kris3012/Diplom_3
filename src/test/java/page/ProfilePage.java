package page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class ProfilePage {

    private final SelenideElement logoutButton = $x("//button[text()='Выход']");
    private final SelenideElement constructorButton = $x("//p[text()='Конструктор']");
    private final SelenideElement profileHeading = $x("//a[contains(text(),'Профиль')]");
    private final SelenideElement logoLink = $x("//div[contains(@class,'AppHeader_header__logo')]/a");


    @Step("Нажимаем кнопку 'Выход'")
    public MainPage clickLogoutButton() {
        logoutButton.shouldBe(visible).click();
        return new MainPage();
    }

    @Step("Переходим в 'Конструктор'")
    public MainPage clickConstructorButton() {
        constructorButton.shouldBe(visible).click();
        return new MainPage();
    }

    @Step("Проверяем, что мы в личном кабинете")
    public boolean isInProfile() {
        return profileHeading.shouldBe(visible).isDisplayed();
    }

    @Step("Кликаем по логотипу Stellar Burgers")
    public MainPage clickStellarBurgersLogo() {
        logoLink.shouldBe(visible).click();
        return new MainPage();
    }
}
