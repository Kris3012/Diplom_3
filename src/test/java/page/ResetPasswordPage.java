package page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class ResetPasswordPage {

    private final SelenideElement emailInput = $("input[name='name']");
    private final SelenideElement restoreButton = $x("//button[text()='Восстановить']");
    private final SelenideElement loginLink = $x("//a[text()='Войти']");

    @Step("Вводим email: {email}")
    public ResetPasswordPage enterEmail(String email) {
        emailInput.shouldBe(visible).setValue(email);
        return this;
    }

    @Step("Нажимаем кнопку 'Восстановить'")
    public ResetPasswordPage clickRestoreButton() {
        restoreButton.shouldBe(visible).click();
        return this;
    }

    @Step("Нажимаем ссылку 'Войти'")
    public LoginPage clickLoginLink() {
        loginLink.shouldBe(visible).click();
        return new LoginPage();
    }

    @Step("Восстанавливаем пароль для email: {email}")
    public ResetPasswordPage restorePassword(String email) {
        return enterEmail(email)
                .clickRestoreButton();
    }
}
