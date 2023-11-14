package user.negative;

import io.qameta.allure.junit4.DisplayName;

import io.restassured.response.Response;
import org.example.clients.UserClient;

import org.example.generators.GenerateUserData;
import org.example.models.user.CreateUser;

import org.junit.After;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserOneFieldEmptyTest {
    public String email = GenerateUserData.generateEmail();
    public String password = "";
    public String name = GenerateUserData.generateName();
    public String authorizationToken;


    @Test
    @DisplayName("Невозможно создать нового пользователя - не заполнено поле логин")
    public void CreateUserEmptyEmail(){
        CreateUser createdUser = new CreateUser(email,password, name);
        Response response = UserClient.register(createdUser);
        response.then().assertThat()
                .statusCode(403);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"));
        authorizationToken = response.then().extract().body().path("accessToken");
    }



    @After
    public void deleteUser()  {
        if (authorizationToken != null) {
            UserClient.deleteUser(authorizationToken);

    }
    }
}