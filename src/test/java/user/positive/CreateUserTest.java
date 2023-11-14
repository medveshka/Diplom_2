package user.positive;


import io.restassured.response.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import io.qameta.allure.junit4.DisplayName;

import org.example.clients.UserClient;
import org.junit.After;
import org.junit.Test;
import org.example.generators.GenerateUserData;
import org.example.models.user.CreateUser;


public class CreateUserTest {
    public String email = GenerateUserData.generateEmail();
    public String password = GenerateUserData.generatePassword();
    public String name = GenerateUserData.generateName();
    public String authorizationToken;



    @Test
    @DisplayName("Создание нового пользователя")
    public void createNewUser(){
        CreateUser createdUser = new CreateUser(email,password, name);
        Response response = UserClient.register(createdUser);
        response.then().assertThat()
                .statusCode(200);
        response.then().assertThat().body("success", equalTo(true));
        authorizationToken = response.then().extract().body().path("accessToken");
    }



    @After
    public void deleteUser()  {

        UserClient.deleteUser(authorizationToken);


    }
}