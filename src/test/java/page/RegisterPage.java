package page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class RegisterPage {

    // Ссылки и кнопки
    private final SelenideElement registerLink = $x("//a[text()='Зарегистрироваться']");
    private final SelenideElement registerButton = $x("//button[text()='Зарегистрироваться']");
    private final SelenideElement loginLink = $x("//a[text()='Войти']");

    // Поля ввода
    private final SelenideElement nameField = $x("//label[text()='Имя']/following-sibling::input");
    private final SelenideElement emailField = $x("//label[text()='Email']/following-sibling::input");
    private final SelenideElement passwordField = $x("//label[text()='Пароль']/..//input");

    // Ошибки
    private final SelenideElement passwordError = $x("//p[contains(text(),'Некорректный пароль')]");

    @Step("Переходим на страницу регистрации")
    public RegisterPage clickRegisterLink() {
        registerLink.shouldBe(visible).click();
        return this;
    }

    @Step("Вводим имя: {name}")
    public RegisterPage enterName(String name) {
        nameField.shouldBe(visible).setValue(name);
        return this;
    }

    @Step("Вводим email: {email}")
    public RegisterPage enterEmail(String email) {
        emailField.shouldBe(visible).setValue(email);
        return this;
    }

    @Step("Вводим пароль")
    public RegisterPage enterPassword(String password) {
        passwordField.shouldBe(visible).setValue(password);
        return this;
    }

    @Step("Нажимаем кнопку 'Зарегистрироваться'")
    public RegisterPage clickRegisterButton() {
        registerButton.shouldBe(visible).click();
        return this;
    }

    @Step("Переходим по ссылке 'Войти'")
    public LoginPage clickLoginLink() {
        loginLink.shouldBe(visible).click();
        return new LoginPage();
    }

    @Step("Проверяем, что ошибка о некорректном пароле отображается")
    public boolean isPasswordErrorVisible() {
        return passwordError.exists() && passwordError.isDisplayed();
    }

    @Step("Регистрируем пользователя: {name}, {email}")
    public RegisterPage registerNewUser(String name, String email, String password) {
        return enterName(name)
                .enterEmail(email)
                .enterPassword(password)
                .clickRegisterButton();
    }

    @Step("Проверяем, что поле 'Имя' отображается")
    public boolean isNameInputVisible() {
        return nameField.exists() && nameField.isDisplayed();
    }
}
