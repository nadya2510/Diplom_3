package stellarburgers;


import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

//Переход из личного кабинета в конструктор по клику на «Конструктор»
//Переход из личного кабинета в конструктор по логотипу Stellar Burgers.
//Выход по кнопке «Выйти» в личном кабинете.

public class AccountProfileTest extends BaseStellarburgers{

    @Before
    public void tearup() {
        startStellarburger(true);

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
        assertTrue("Ожидается «Конструктор» ", result);
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
        assertTrue("Ожидается «Конструктор» ", result);
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
        assertTrue("Ожидается страница авторизации ", result);
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
