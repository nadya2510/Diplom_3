package stellarburgers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage{
    //Заголовок "Вход"
    private By hIn = By.xpath(".//h2[text()='Вход']");
    //поля ввода «Email»
    private By inputEmail = By.xpath(".//input[@name='name']");
    //поля ввода «Пароль»
    private By inputPassword = By.xpath(".//input[@name='Пароль']");
    //Кнопка "Войти"
    private By buttonLogin = By.xpath(".//*[text()='Войти']");
    //Кнопка "Зарегистрироваться"
    private By buttonRegister = By.xpath(".//a[@href='/register']");
    //Кнопка "Восстановить пароль"
    private By buttonForgotPassword = By.xpath(".//a[@href='/forgot-password']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }
    // метод для заполнения Email
    public boolean getHIn() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(hIn));
        return driver.findElement(hIn).isDisplayed();
    }

    // метод для заполнения Email
    public void setValueInEmail(String newEmail) {
        driver.findElement(inputEmail).clear();
        driver.findElement(inputEmail).sendKeys(newEmail);
    }
    // метод для заполнения Пароль
    public void setValueInPassword(String newPassword) {
        driver.findElement(inputPassword).clear();
        driver.findElement(inputPassword).sendKeys(newPassword);
    }

    // метод для нажатия на кнопку "Войти"
    public HomePage clickLogin() {
        driver.findElement(buttonLogin).click();
        return new HomePage(driver);
    }

    // метод для нажатия на кнопку "Зарегистрироваться"
    public RegisterPage clickRegister() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(buttonRegister));
        driver.findElement(buttonRegister).click();
        return new RegisterPage(driver);
    }

    // метод для нажатия на кнопку "Восстановить пароль"
    public ForgotPasswordPage clickForgotPassword() {
        driver.findElement(buttonForgotPassword).click();
        return new ForgotPasswordPage(driver);
    }



}
