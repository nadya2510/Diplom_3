package stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertTrue;

//
//Раздел «Конструктор»
//
@RunWith(Parameterized.class)
public class HomePageTest extends BaseStellarburgers{
    //текст раздела
    private Integer valueIndex ;
    private String valueText ;

    public HomePageTest(Integer valueIndex, String valueText)  {
        this.valueIndex = valueIndex;
        this.valueText = valueText;
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
        startStellarburger(false);

        //С первого раздела загружается страница, для начала перейдем в раздел "Соусы"
        if (valueIndex == 0){
            objHomePage.clickRazdel("Соусы");
        }
        //Нажимаем на раздел
        objHomePage.clickRazdel(valueText);
        //Сравниваем результат
        boolean result = objHomePage.waitForRazdelVisibility(valueText);
        assertTrue("Ожидается раздел: " + valueText, true );
        driver.quit();
    }


}
