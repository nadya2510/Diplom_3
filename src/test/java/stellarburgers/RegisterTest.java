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

//Регистрация
//Успешная регистрация
//Ошибка для некорректного пароля. Минимальный пароль — шесть символов.
public class RegisterTest {
    private WebDriver driver;
    private HomePage objHomePage;
    private LoginPage objLoginPage;
    private RegisterPage objRegisterPage;
    private RegisterIn jsonLog;
    private Integer random = new Random().nextInt();
    private String username = String.format("Username%d", random);
    private String email = String.format("test-data%d@yandex.ru", random);
    private String password = "password123";
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
        //создай объект класса главной страницы
        objHomePage = new HomePage(driver);
        // «Войти в аккаунт» -«Регистрация»
        objLoginPage = objHomePage.clickLoginAccount();
        objRegisterPage = objLoginPage.clickRegister();

    }

    //Успешная регистрация
    @Test
    @DisplayName("Register check ok")
    @Description("Register user ok")
    public void registerCheck() {
        //Создадим пользователя
        sendRequestRegisterUser(username, email, password, false);
        boolean result = objLoginPage.getHIn();
        assertEquals("Ожидается страница авторизации",true, result);
        jsonLog = new RegisterIn(email, password, username, "");
    }

    //Регистрация пользователя, который уже зарегистрирован;
    @Test
    @DisplayName("Register duplicate")
    @Description("Register duplicate user error")
    public void registerDuplicate() {
        //Создадим пользователя
        sendRequestRegisterUser(username, email, password, false);
        jsonLog = new RegisterIn(email, password, username, "");

        //Повторно регистрируем его же
        objRegisterPage = objLoginPage.clickRegister();
        sendRequestRegisterUser(username, email, password, true);
        boolean result = objRegisterPage.getDuplicate();
        assertEquals("Ожидается сообщение - Такой пользователь уже существует",true, result);
    }

    //Регистрация пользователя, без заполнения полей username;
    @Test
    @DisplayName("Register username empty")
    @Description("Register user where username empty")
    public void registerUsernameEmpty() {
        //Создадим пользователя
        sendRequestRegisterUser("", email, password, true);
        boolean result = objRegisterPage.getRegister();
        assertEquals("Ожидается страница регистрации",true, result);
    }

    //Регистрация пользователя, без заполнения полей email;
    @Test
    @DisplayName("Register email empty")
    @Description("Register user where email empty")
    public void registerEmailEmpty() {
        //Создадим пользователя
        sendRequestRegisterUser(username, "", password, true);
        boolean result = objRegisterPage.getRegister();
        assertEquals("Ожидается страница регистрации",true, result);
    }

    //Регистрация пользователя, без заполнения полей password;
    @Test
    @DisplayName("Register password empty")
    @Description("Register user where password empty")
    public void registerPasswordEmpty() {
        //Создадим пользователя
        sendRequestRegisterUser(username, email, "", true);
        boolean result = objRegisterPage.getRegister();
        assertEquals("Ожидается страница регистрации",true, result);
    }

    //Регистрация пользователя, password менее 6 символов;
    @Test
    @DisplayName("Register password incorrect")
    @Description("Register user where password length is less than six characters")
    public void registerPassword6() {
        //Создадим пользователя
        sendRequestRegisterUser(username, email, "12345", true);
        boolean result = objRegisterPage.getPassword();
        assertEquals("Ожидается сообщение -Некорректный пароль",true, result);
    }

    @Step("Register user")
    public void sendRequestRegisterUser(String username_, String email_ , String password_, Boolean prError){
        objRegisterPage.setValueInName(username_);
        objRegisterPage.setValueInEmail(email_);
        objRegisterPage.setValueInPassword(password_);
        if (prError){
            objRegisterPage = objRegisterPage.clickRegisterError();
        } else {
            objLoginPage = objRegisterPage.clickRegister();
        }

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
