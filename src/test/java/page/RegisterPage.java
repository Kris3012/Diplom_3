package page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class RegisterPage {

    private final SelenideElement registerLink = $x("//a[text()='Зарегистрироваться']"); // 🆕 добавлено

    private final SelenideElement nameField = $x("//label[text()='Имя']/following-sibling::input");
    private final SelenideElement emailField = $x("//label[text()='Email']/following-sibling::input");
    private final SelenideElement passwordField = $x("//label[text()='Пароль']/..//input");

    private final SelenideElement registerButton = $x("//button[text()='Зарегистрироваться']");
    private final SelenideElement loginButton = $x("//a[text()='Войти']");
    private final SelenideElement passwordError = $x("//p[contains(text(),'Некорректный пароль')]");

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

    @Step("Нажимаем ссылку 'Войти'")
    public LoginPage clickLoginLink() {
        loginButton.shouldBe(visible).click();
        return new LoginPage();
    }

    @Step("Проверяем, что появилась ошибка о некорректном пароле")
    public boolean isPasswordErrorVisible() {
        return passwordError.shouldBe(visible).isDisplayed();
    }

    @Step("Заполняем форму регистрации")
    public RegisterPage register(String name, String email, String password) {
        return enterName(name)
                .enterEmail(email)
                .enterPassword(password)
                .clickRegisterButton();
    }

    @Step("Переход на страницу регистрации")
    public RegisterPage clickRegisterLink() {
        System.out.println("➡️ Кликаем ссылку 'Зарегистрироваться'");
        registerLink.shouldBe(visible).click();
        return new RegisterPage();
    }

    @Step("Проверяем, что поле 'Имя' отображается")
    public boolean isNameInputVisible() {
        return nameField.shouldBe(visible).isDisplayed();
    }
}
