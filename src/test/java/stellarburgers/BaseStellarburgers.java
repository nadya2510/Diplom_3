package stellarburgers;

import api.ApiStellarburgers;
import api.RegisterIn;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.Random;

import static api.ApiConst.URL_API;
import static stellarburgers.ConstStellarburgers.YANDEX;

public abstract class BaseStellarburgers {
    protected WebDriver driver;
    protected HomePage objHomePage;
    protected LoginPage objLoginPage;
    protected AccountProfilePage objAccountProfilePage;
    protected ForgotPasswordPage objForgotPasswordPage;
    protected RegisterPage objRegisterPage;

    protected String email;
    protected String password;
    protected String username;
    protected RegisterIn jsonLog;
    protected ApiStellarburgers apiStellarburgers;



    public void startStellarburger(Boolean addUser){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        Config config = Config.initConfig();
        String browserType = config.browserType;
        String pathYandexBrowser = config.pathYandexBrowser;
        String pathYandexDriver = config.pathYandexDriver;
        if (browserType.equalsIgnoreCase(YANDEX)) {
            System.setProperty("webdriver.chrome.driver", pathYandexDriver);
            options.setBinary(pathYandexBrowser);
        }

        driver = new ChromeDriver(options);
        // Открой страницу  стенда
        driver.get(URL_API);
        // создай объект класса главной страницы
        objHomePage = new HomePage(driver);

        if (addUser) {
            //Создадим пользователя
            apiStellarburgers = new ApiStellarburgers();
            Integer random = new Random().nextInt();
            email = String.format("test-data%d@yandex.ru", random);
            password = "password123";
            username = String.format("Username%d", random);
            RegisterIn json  = new RegisterIn(email, password, username);
            String accessToken =  apiStellarburgers.doRegisterRequest(json);
            jsonLog = new RegisterIn(email, password, username, accessToken);
        }
    }


}
