package stellarburgers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage extends BasePage{
    //поля ввода «Имя»
    private By inputName = By.xpath("*//fieldset[1]/div/div/input");
    //поля ввода «Email»
    private By inputEmail = By.xpath("*//fieldset[2]/div/div/input");
    //поля ввода «Пароль»
    private By inputPassword = By.xpath("*//fieldset[3]/div/div/input");
    //Кнопка "Войти"
    private By buttonLogin = By.xpath(".//*[text()='Войти']");
    //Кнопка "Зарегистрироваться"
    private By buttonRegister = By.xpath(".//*[text()='Зарегистрироваться']");
    //Сообщение "Такой пользователь уже существует"
    private By textDuplicate = By.xpath(".//*[text()='Такой пользователь уже существует']");
    //Сообщение "Такой пользователь уже существует"
    private By textPassword = By.xpath(".//*[text()='Некорректный пароль']");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    // метод для заполнения Имя
    public void setValueInName(String newName) {
        driver.findElement(inputName).clear();
        driver.findElement(inputName).sendKeys(newName);
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
    public LoginPage clickLogin() {
        driver.findElement(buttonLogin).click();
        return new LoginPage(driver);
    }

    // метод для нажатия на кнопку "Зарегистрироваться"
    public LoginPage clickRegister() {
        driver.findElement(buttonRegister).click();
        return new LoginPage(driver);
    }

    // метод для нажатия на кнопку "Зарегистрироваться"
    public RegisterPage clickRegisterError() {
        driver.findElement(buttonRegister).click();
        return new RegisterPage(driver);
    }

    //Ждем загрузку ошибки-Такой пользователь уже существует
    public boolean getDuplicate() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(textDuplicate));
        return driver.findElement(textDuplicate).isDisplayed();
    }

    //Ждем загрузку ошибки-Некорректный пароль
    public boolean getPassword() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(textPassword));
        return driver.findElement(textPassword).isDisplayed();
    }

    //Ждем загрузки
    public boolean getRegister() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(buttonRegister));
        return driver.findElement(buttonRegister).isDisplayed();
    }



}
