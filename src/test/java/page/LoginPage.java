package page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    // Поля ввода
    private final SelenideElement emailField = $("form.Auth_form__3qKeq input[type='text']");
    private final SelenideElement passwordField = $("form.Auth_form__3qKeq input[type='password']");

    // Кнопка "Войти"
    private final SelenideElement loginButton = $("button.button_button__33qZ0.button_button_type_primary__1O7Bx.button_button_size_medium__3zxIa");

    // Ссылки
    private final SelenideElement registerLink = $("a.Auth_link__1fOlj[href='/register']");
    private final SelenideElement forgotPasswordLink = $("a.Auth_link__1fOlj[href='/forgot-password']");

    @Step("Вводим email: {email}")
    public LoginPage enterEmail(String email) {
        emailField.shouldBe(visible).setValue(email);
        return this;
    }

    @Step("Вводим пароль (значение скрыто)")
    public LoginPage enterPassword(String password) {
        passwordField.shouldBe(visible).setValue(password);
        return this;
    }

    @Step("Нажимаем кнопку 'Войти'")
    public LoginPage clickLoginButton() {
        loginButton.shouldBe(enabled);
        try {
            loginButton.click();
        } catch (Exception e) {
            executeJavaScript("arguments[0].click();", loginButton);
        }
        return this;
    }

    @Step("Переходим по ссылке 'Зарегистрироваться'")
    public RegisterPage clickRegisterLink() {
        registerLink.shouldBe(visible).click();
        return new RegisterPage();
    }

    @Step("Переходим по ссылке 'Восстановить пароль'")
    public ResetPasswordPage clickForgotPasswordLink() {
        forgotPasswordLink.shouldBe(visible).click();
        return new ResetPasswordPage();
    }

    @Step("Проверяем, что форма входа отображается")
    public boolean isLoginFormVisible() {
        return loginButton.shouldBe(visible).exists();
    }

    @Step("Логинимся как пользователь: {email}")
    public void loginAs(String email, String password) {
        enterEmail(email)
                .enterPassword(password)
                .clickLoginButton();
    }
}
