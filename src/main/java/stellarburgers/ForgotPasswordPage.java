package stellarburgers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage extends BasePage{
    //поля ввода «Email»
    private By inputEmail = By.xpath(".//input[@name='name']");
    //Кнопка "Войти"
    private By buttonLogin = By.xpath(".//*[text()='Войти']");
    //Кнопка "Восстановить"
    private By buttonRegister = By.xpath(".//*[text()='Восстановить']");

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    // метод для заполнения Email
    public void setValueInEmail(String newEmail) {
        driver.findElement(inputEmail).clear();
        driver.findElement(inputEmail).sendKeys(newEmail);
    }

    // метод для нажатия на кнопку "Войти"
    public LoginPage clickLogin() {
        driver.findElement(buttonLogin).click();
        return new LoginPage(driver);
    }

    // метод для нажатия на кнопку "Восстановить"
    public RegisterPage clickRegister() {
        driver.findElement(buttonRegister).click();
        return new RegisterPage(driver);
    }
}
