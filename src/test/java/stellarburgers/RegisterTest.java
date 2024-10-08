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

//Регистрация
//Успешная регистрация
//Ошибка для некорректного пароля. Минимальный пароль — шесть символов.
public class RegisterTest extends BaseStellarburgers{
    @Before
    public void tearup() {

        startStellarburger(false);
        // «Войти в аккаунт» -«Регистрация»
        objLoginPage = objHomePage.clickLoginAccount();
        objRegisterPage = objLoginPage.clickRegister();

        //Данные пользователя
        Integer random = new Random().nextInt();
        username = String.format("Username%d", random);
        email = String.format("test-data%d@yandex.ru", random);
        password = "password123";
    }

    //Успешная регистрация
    @Test
    @DisplayName("Register check ok")
    @Description("Register user ok")
    public void registerCheck() {
        //Создадим пользователя
        sendRequestRegisterUser(username, email, password, false);
        boolean result = objLoginPage.getHIn();
        assertTrue("Ожидается страница авторизации", result);
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
        assertTrue("Ожидается сообщение - Такой пользователь уже существует", result);
    }

    //Регистрация пользователя, без заполнения полей username;
    @Test
    @DisplayName("Register username empty")
    @Description("Register user where username empty")
    public void registerUsernameEmpty() {
        //Создадим пользователя
        sendRequestRegisterUser("", email, password, true);
        boolean result = objRegisterPage.getRegister();
        assertTrue("Ожидается страница регистрации", result);
    }

    //Регистрация пользователя, без заполнения полей email;
    @Test
    @DisplayName("Register email empty")
    @Description("Register user where email empty")
    public void registerEmailEmpty() {
        //Создадим пользователя
        sendRequestRegisterUser(username, "", password, true);
        boolean result = objRegisterPage.getRegister();
        assertTrue("Ожидается страница регистрации", result);
    }

    //Регистрация пользователя, без заполнения полей password;
    @Test
    @DisplayName("Register password empty")
    @Description("Register user where password empty")
    public void registerPasswordEmpty() {
        //Создадим пользователя
        sendRequestRegisterUser(username, email, "", true);
        boolean result = objRegisterPage.getRegister();
        assertTrue("Ожидается страница регистрации", result);
    }

    //Регистрация пользователя, password менее 6 символов;
    @Test
    @DisplayName("Register password incorrect")
    @Description("Register user where password length is less than six characters")
    public void registerPassword6() {
        //Создадим пользователя
        sendRequestRegisterUser(username, email, "12345", true);
        boolean result = objRegisterPage.getPassword();
        assertTrue("Ожидается сообщение - Некорректный пароль", result);
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
            apiStellarburgers = new ApiStellarburgers();
            String accessToken =  apiStellarburgers.doLoginRequest(jsonLog);
            apiStellarburgers.doDeleteRequest(accessToken);

        }
    }

}
