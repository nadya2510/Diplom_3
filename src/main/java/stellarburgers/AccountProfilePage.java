package stellarburgers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountProfilePage extends BasePage{
    //Заголовок "Профиль"
    private By profile = By.xpath(".//a[@href='/account/profile']");
    //Кнопка "Конструктор"
    private By buttonDesigner = By.xpath(".//*[text()='Конструктор']");
    //Кнопка "Stellar Burgers"
    private By buttonStellarBurgers = By.xpath(".//div[@class='AppHeader_header__logo__2D0X2']");
    //Кнопка "Выход"
    private By buttonExit = By.xpath(".//button[text()='Выход']");

    public AccountProfilePage(WebDriver driver) {
        super(driver);
    }

    // ждем загрузку профиля
    public boolean getProfile() {
        new WebDriverWait(driver,20)
                .until(ExpectedConditions.visibilityOfElementLocated(profile));
        return driver.findElement(profile).isDisplayed();
    }


    // метод для нажатия на кнопку "Конструктор"
    public HomePage clickDesigner() {
        getProfile();
        driver.findElement(buttonDesigner).click();
        return new HomePage(driver);
    }

    // метод для нажатия на "Stellar Burgers"
    public HomePage clickStellarBurgers() {
        getProfile();
        driver.findElement(buttonStellarBurgers).click();
        return new HomePage(driver);
    }

    // метод для нажатия на кнопку "Выход"
    public LoginPage clickExit() {
        getProfile();
        driver.findElement(buttonExit).click();
        return new LoginPage(driver);
    }


}
