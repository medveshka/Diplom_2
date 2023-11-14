package org.example.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.constantsAPI.EndPoints;
import org.example.models.user.CreateUser;
import org.example.models.user.LogInUser;
import org.example.models.user.UpdateUser;

import static io.restassured.RestAssured.given;

public class UserClient extends BaseClient {


    @Step("Регистрация пользователя")
    public static Response register(CreateUser createUser){
        return given()
                .spec(getBaseSpec())
                .and()
                .body(createUser)
                .when()
                .post(EndPoints.REGISTER);
    }

    @Step("Логин пользователя")
    public static Response login(LogInUser logInUser){
        return given()
                .spec(getBaseSpec())
                .and()
                .body(logInUser)
                .when()
                .post(EndPoints.LOGIN);

    }

    @Step("Получение данных о пользователе")
    public Response getUserData(String authorization) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(authorization)
                .when()
                .get(EndPoints.USER);
    }

    @Step("Обновление данных пользователя")
    public static Response updateUser(String accessToken, UpdateUser updateUser) {
        return given()
                .header("Authorization", accessToken)
                .spec(getBaseSpec())
                .and()
                .body(updateUser)
                .when()
                .patch(EndPoints.USER);
    }


    @Step("Удаление пользователя")
    public static Response deleteUser(String accessToken) {
        return given()
                .header("Authorization", "bearer "+ accessToken)
                .spec(getBaseSpec())
                .delete(EndPoints.USER);
    }
}