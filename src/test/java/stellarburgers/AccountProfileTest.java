package stellarburgers;

import api.ApiStellarburgers;
import api.RegisterIn;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import static stellarburgers.ConstStellarburgers.*;
import static api.ApiConst.URL_API;

//Переход из личного кабинета в конструктор по клику на «Конструктор»
//Переход из личного кабинета в конструктор по логотипу Stellar Burgers.
//Выход по кнопке «Выйти» в личном кабинете.

public class AccountProfileTest {
    private WebDriver driver;
    private HomePage objHomePage;
    private LoginPage objLoginPage;
    private AccountProfilePage objAccountProfilePage;
    private String email;
    private String password;
    private String username;
    private RegisterIn jsonLog;
    private ApiStellarburgers apiStellarburgers = new ApiStellarburgers();

    @Before
    public void tearup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        String browserType = System.getProperty("browserType");
        if (browserType != null) {
            if (browserType.equalsIgnoreCase(YANDEX)) {
                System.setProperty("webdriver.chrome.driver", PATH_YANDEXDRIVER);
                options.setBinary(PATH_YANDEXBROWSER);
            }
        }

        driver = new ChromeDriver(options);
        //Cтраница стенда
        driver.get(URL_API);
        // создай объект класса главной страницы
        objHomePage = new HomePage(driver);

        //Создадим пользователя
        Integer random = new Random().nextInt();
        email = String.format("test-data%d@yandex.ru", random);
        password = "password123";
        username = String.format("Username%d", random);
        RegisterIn json  = new RegisterIn(email, password, username);
        String accessToken =  apiStellarburgers.doRegisterRequest(json);
        jsonLog = new RegisterIn(email, password, username, accessToken);

        //Войдем в акаунт
        objLoginPage = objHomePage.clickLoginAccount();
        objLoginPage.setValueInEmail(email);
        objLoginPage.setValueInPassword(password);
        //Вернемся на главную и войдем в «Личный кабинет»
        objHomePage = objLoginPage.clickLogin();
        objAccountProfilePage = objHomePage.clickPersonalAccountLogin();
    }

    // Переход из личного кабинета в конструктор по клику на «Конструктор»
    @Test
    @DisplayName("Account profile - Designer")
    @Description("Account profile exit to click designer")
    @Step("Click Designer button")
    public void accountProfileDesigner() {
        //клик на «Конструктор»
        objHomePage = objAccountProfilePage.clickDesigner();
        boolean result = objHomePage.waitForOrdersVisibility();
        assertEquals("Ожидается «Конструктор» ",true, result);
    }

    // Переход из личного кабинета в конструктор по клику на «Stellar Burgers»
    @Test
    @DisplayName("Account profile - StellarBurgers")
    @Description("Account profile exit to click logo StellarBurgers")
    @Step("Click logo StellarBurgers button")
    public void accountProfileStellarBurgers() {
        //клик на «Stellar Burgers»
        objHomePage = objAccountProfilePage.clickStellarBurgers();
        boolean result = objHomePage.waitForOrdersVisibility();
        assertEquals("Ожидается «Конструктор» ",true, result);
    }

    // Выход по кнопке «Выйти» в личном кабинете
    @Test
    @DisplayName("Account profile - Exit")
    @Description("Account profile exit to click Exit")
    @Step("Click Exit button")
    public void accountProfileExit() {
        //клик на «Выйти»
        objLoginPage = objAccountProfilePage.clickExit();
        boolean result = objLoginPage.getHIn();
        assertEquals("Ожидается страница авторизации ",true, result);
    }

    @After
    public void deleteUser() {
        driver.quit();
        if (jsonLog != null) {
            //
            String accessToken =  apiStellarburgers.doLoginRequest(jsonLog);
            apiStellarburgers.doDeleteRequest(accessToken);

        }
    }
}
