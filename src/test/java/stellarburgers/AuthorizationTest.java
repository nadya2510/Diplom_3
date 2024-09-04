package stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

//вход по кнопке «Войти в аккаунт» на главной,
//вход через кнопку «Личный кабинет»,
//вход через кнопку в форме регистрации,
//вход через кнопку в форме восстановления пароля.
public class AuthorizationTest extends BaseStellarburgers{

    @Before
    public void tearup() {
        startStellarburger(true);
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
    public void registerPageLogin() {
        //«Войти в аккаунт» на главной
        objLoginPage = objHomePage.clickLoginAccount();
        //«Зарегистрироваться» на странице войти
        objRegisterPage = objLoginPage.clickRegister();
        //«Войти» на странице регистрации
        objLoginPage = objRegisterPage.clickLogin();
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
        assertTrue("Ожидается «Конструктор»", result);
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
