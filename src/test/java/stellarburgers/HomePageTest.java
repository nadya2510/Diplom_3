package stellarburgers;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.Assert.assertEquals;
import static stellarburgers.ConstStellarburgers.*;
import static api.ApiConst.URL_API;

//
//Раздел «Конструктор»
//
@RunWith(Parameterized.class)
public class HomePageTest {
    //текст раздела
    private Integer ValueIndex ;
    private String ValueText ;

    public HomePageTest(Integer ValueIndex, String ValueText)  {
        this.ValueIndex = ValueIndex;
        this.ValueText = ValueText;
    }

    @Parameterized.Parameters
    public static Object[][] getHomePageText() {
        //Тестовые данные
        return new Object[][] {
                {0, "Булки"},
                {1, "Соусы"},
                {2, "Начинки"}
        };
    }
    @Test
    @DisplayName("Home - Razdel designer")
    @Description("Home click razdel designer")
    public void clickRazdelTest() {
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

        WebDriver driver = new ChromeDriver(options);
        // Открой страницу  стенда
        driver.get(URL_API);
        // создай объект класса главной страницы
        HomePage objHomePage = new HomePage(driver);

        //С первого раздела загружается страница, для начала перейдем в раздел "Соусы"
        if (ValueIndex == 0){
            objHomePage.clickRazdel("Соусы");
        }
        //Нажимаем на раздел
        objHomePage.clickRazdel(ValueText);
        //Сравниваем результат
        boolean result = objHomePage.waitForRazdelVisibility(ValueText);
        assertEquals("Ожидается текст: " + ValueText, true, result );
        driver.quit();
    }


}
