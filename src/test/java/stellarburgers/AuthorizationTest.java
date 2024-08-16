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

//вход по кнопке «Войти в аккаунт» на главной,
//вход через кнопку «Личный кабинет»,
//вход через кнопку в форме регистрации,
//вход через кнопку в форме восстановления пароля.
public class AuthorizationTest {
    private WebDriver driver;
    private HomePage objHomePage;
    private LoginPage objLoginPage;
    private AccountProfilePage objAccountProfilePage;
    private ForgotPasswordPage objForgotPasswordPage;
    private RegisterPage obgRegisterPage;
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
        // Cтраница стенда
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
    }

    // Войдем по кнопке «Войти в аккаунт»
    @Test
    @DisplayName("Home - Login")
    @Description("Home go to Login page to click Login")
   // @Step("Click Login button Home Page")
    public void homeLogin() {
        objLoginPage = objHomePage.clickLoginAccount();
        //Заполнить поля и «Войти»
        clickLoginPageLogin(email, password);
        loginCheck();
    }

    // Войдем через кнопку «Личный кабинет»
    @Test
    @DisplayName("Home - Personal account")
    @Description("Home go to Login page to click Personal account")
    //@Step("Click Personal account button Home Page")
    public void homePersonalAccountLogin() {
        //«Личный кабинет» на главной
        objLoginPage = objHomePage.clickPersonalAccountLogout();
        //Заполнить поля и «Войти»
        clickLoginPageLogin(email, password);
        loginCheck();
    }

    // Вход через кнопку в форме восстановления пароля
    @Test
    @DisplayName("Forgot password - Login")
    @Description("Forgot password go to Login page to click Login")
   // @Step("Click Login button Forgot Password Page")
    public void forgotPasswordLogin() {
        //«Войти в аккаунт» на главной
        objLoginPage = objHomePage.clickLoginAccount();
        //«Восстановить пароль» на странице войти
        objForgotPasswordPage = objLoginPage.clickForgotPassword();
        //«Войти» на странице восстановить пароль
        objLoginPage = objForgotPasswordPage.clickLogin();
        //Заполнить поля и «Войти»
        clickLoginPageLogin(email, password);
        loginCheck();
    }

    // Вход через кнопку в форме регистрации
    @Test
    @DisplayName("Register - Login")
    @Description("Register go to Login page to click Login")
  //  @Step("Click Login button in Register Page")
    public void registerPageLogin() {
        //«Войти в аккаунт» на главной
        objLoginPage = objHomePage.clickLoginAccount();
        //«Зарегистрироваться» на странице войти
        obgRegisterPage = objLoginPage.clickRegister();
        //«Войти» на странице регистрации
        objLoginPage = obgRegisterPage.clickLogin();
        //Заполнить поля и «Войти»
        clickLoginPageLogin(email, password);
        loginCheck();
    }


    @Step("Click Login button in Login Page")
    public void clickLoginPageLogin(String email_ , String password_){
        objLoginPage.setValueInEmail(email);
        objLoginPage.setValueInPassword(password);
        //Вернемся на главную c кнопкой "Оформить заказ" если все верно по "Войти"
        objHomePage = objLoginPage.clickLogin();
    }
    @Step("Login check")
    public void loginCheck(){
        boolean result = objHomePage.waitForOrdersVisibility();
        assertEquals("Ожидается «Конструктор» ",true, result);
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
