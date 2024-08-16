package stellarburgers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;


//Класс главной страницы
public class HomePage  extends BasePage{
    //Кнопка "Личный Кабинет" вверху страницы
    private By buttonPersonalAccount = By.xpath(".//*[text()='Личный Кабинет']");
    //Кнопка "Войти в аккаунт"
    private By buttonLoginAccount = By.xpath(".//*[text()='Войти в аккаунт']");
    //Кнопка "Оформить заказ"
    private By buttonOrders = By.xpath(".//*[text()='Оформить заказ']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // метод для нажатия на кнопку "Личный Кабинет"
    public LoginPage clickPersonalAccountLogout() {
        driver.findElement(buttonPersonalAccount).click();
        return new LoginPage(driver);
    }

    public AccountProfilePage clickPersonalAccountLogin() {
        driver.findElement(buttonPersonalAccount).click();
        return new AccountProfilePage(driver);
    }

    // метод для нажатия на кнопку "Войти в аккаунт"
    public LoginPage clickLoginAccount() {
        driver.findElement(buttonLoginAccount).click();
        return new LoginPage(driver);
    }

    // метод выбора раздела по названию
    public void clickRazdel(String razdelName) {
        // клик по разделу
        driver.findElement(By.xpath(String.format(".//span[text()='%s']", razdelName))).click();

}

    // метод проверки с ожиданием видимости раздела
    public boolean waitForRazdelVisibility(String razdelName) {

        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(".//h2[text()='%s']", razdelName))));
        return driver.findElement(By.xpath(String.format(".//h2[text()='%s']", razdelName))).isDisplayed();
    }

    // метод с ожиданием видимости конструктора
    public boolean waitForOrdersVisibility() {

        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(buttonOrders));
        return driver.findElement(buttonOrders).isDisplayed();
    }

    // метод с ожиданием загрузки главной страници без авторизации
    public boolean waitForLoginAccountVisibility() {

        new WebDriverWait(driver,15)
                .until(ExpectedConditions.visibilityOfElementLocated(buttonLoginAccount));
        return driver.findElement(buttonLoginAccount).isDisplayed();
    }
}
