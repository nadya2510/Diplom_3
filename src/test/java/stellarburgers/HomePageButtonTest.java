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

//Переход по клику на «Личный кабинет» без авторизации.
//Переход по клику на «Личный кабинет» c авторизацией.
//Переход по клику на «Войти в аккаунт» без авторизации.


public class HomePageButtonTest {
    private WebDriver driver;
    private HomePage objHomePage;
    private LoginPage objLoginPage;
    private AccountProfilePage objAccountProfilePage;
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
        // Cтраница стенда
        driver.get(URL_API);
        // создай объект класса главной страницы
        objHomePage = new HomePage(driver);
    }

    //Переход на страницу авторизации по «Войти в аккаунт»
    @Test
    @DisplayName("Home - Login")
    @Description("Home go to Login page to click Login")
    @Step("Click Login button")
    public void homeLoginAccount() {
        //если нажать на «Войти в аккаунт» без авторизации, попадёшь на страницу авторизации
        objLoginPage = objHomePage.clickLoginAccount();
        boolean result = objLoginPage.getHIn();
        assertEquals("Ожидается страница авторизации через кнопку «Войти в аккаунт» ",true, result);
    }

    //Переход на страницу авторизации по «Личный кабинет»
    @Test
    @DisplayName("Home - Personal account logout")
    @Description("Home go to Login page to click Personal account")
    @Step("Click Personal account button")
    public void homePersonalAccountLogout() {
        //если нажать на «Личный кабинет» без авторизации, попадёшь на страницу авторизации
        objLoginPage = objHomePage.clickPersonalAccountLogout();
        boolean result = objLoginPage.getHIn();
        assertEquals("Ожидается страница авторизации через кнопку «Личный кабинет» ",true, result);
    }

    //Переход в личный кабинет с авторизацией по «Личный кабинет»
    @Test
    @DisplayName("Home - Personal account login")
    @Description("Home go to Personal account page to click Personal account")
    @Step("Click Personal account button")
    public void homePersonalAccountLogin() {
        //Создадим пользователя
        Integer random = new Random().nextInt();
        String email = String.format("test-data%d@yandex.ru", random);
        String password = "password123";
        String username = String.format("Username%d", random);
        RegisterIn json  = new RegisterIn(email, password, username);
        //
        String accessToken =  apiStellarburgers.doRegisterRequest(json);
        jsonLog = new RegisterIn(email, password, username, accessToken);

        //Войдем по кнопке «Войти в аккаунт»
        objLoginPage = objHomePage.clickLoginAccount();
        objLoginPage.setValueInEmail(email);
        objLoginPage.setValueInPassword(password);

        //Вернемся на главную
        objHomePage = objLoginPage.clickLogin();
        objAccountProfilePage = objHomePage.clickPersonalAccountLogin();
        //если нажать на «Личный кабинет» с авторизацией, попадёшь на страницу «Личный кабинет»
        boolean result = objAccountProfilePage.getProfile();
        assertEquals("Ожидается страница «Личный кабинет» ",true, result);
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
