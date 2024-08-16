package api;

import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;
import static api.ApiConst.*;

public class ApiStellarburgers {
    // Создание пользователя
    @Step("Send Post add register user request")
    public  String doRegisterRequest(RegisterIn json){
        return   given()
                .header("Content-type", "application/json")
                .body(json)
                .post(URL_REGISTER)
                .thenReturn().then().extract().body().path("accessToken");
    }
    // Авторизация пользователя
    @Step("Send Post login user request")
    public  String doLoginRequest(RegisterIn json){
        return  given()
                .header("Content-type", "application/json")
                .body(json)
                .post(URL_REGISTER_LOG)
                .thenReturn().then().extract().body().path("accessToken");
    }
    // Удаляем пользователя
   @Step("Send Delete user request")
    public void doDeleteRequest(String accessToken ){
        given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .delete(URL_REGISTER_DEL)
                .thenReturn();
    }
}
