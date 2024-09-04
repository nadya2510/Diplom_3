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
    //Текущий раздел
    private By buttonRazdel = By.xpath(".//div[@class='tab_tab__1SPyG tab_tab_type_current__2BEPc pt-4 pr-10 pb-4 pl-10 noselect']/span");

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
        String spanRazdel = String.format(".//span[text()='%s']", razdelName);
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(spanRazdel)));
        // клик по разделу
        driver.findElement(By.xpath(spanRazdel)).click();
    }

    public boolean waitForRazdelVisibility(String razdelName) {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(buttonRazdel));
        return driver.findElement(buttonRazdel).getText().equalsIgnoreCase(razdelName);
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
