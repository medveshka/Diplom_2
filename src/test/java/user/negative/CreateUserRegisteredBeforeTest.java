package user.negative;

import io.qameta.allure.junit4.DisplayName;

import io.restassured.response.Response;
import org.example.clients.UserClient;

import org.example.generators.GenerateUserData;
import org.example.models.user.CreateUser;

import org.junit.After;

import org.junit.Test;


import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserRegisteredBeforeTest {
    public String email = GenerateUserData.generateEmail();
    public String password = GenerateUserData.generatePassword();
    public String name = GenerateUserData.generateName();
    public String authorizationToken;



    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void CreateUserRegisteredBefore(){
        CreateUser createdUser = new CreateUser(email,password, name);
        Response responseCreateNewUser = UserClient.register(createdUser);


        Response resSecondRegistration = UserClient.register(createdUser);
        authorizationToken = responseCreateNewUser.then().extract().body().path("accessToken");

        resSecondRegistration.then().assertThat()
                .statusCode(403);
        resSecondRegistration.then().assertThat().body("message", equalTo("User already exists"));



    }



    @After
    public void deleteUser() {
        UserClient.deleteUser(authorizationToken);

    }
}