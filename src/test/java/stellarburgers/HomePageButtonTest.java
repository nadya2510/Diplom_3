package stellarburgers;

import api.ApiStellarburgers;
import api.RegisterIn;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;
import static org.junit.Assert.assertTrue;

//Переход по клику на «Личный кабинет» без авторизации.
//Переход по клику на «Личный кабинет» c авторизацией.
//Переход по клику на «Войти в аккаунт» без авторизации.


public class HomePageButtonTest extends BaseStellarburgers {

    @Before
    public void tearup() {
        startStellarburger(false);
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
        assertTrue("Ожидается страница авторизации через кнопку «Войти в аккаунт» ", result);
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
        assertTrue("Ожидается страница авторизации через кнопку «Личный кабинет» ", result);
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
        apiStellarburgers = new ApiStellarburgers();
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
        assertTrue("Ожидается страница «Личный кабинет» ", result);
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
