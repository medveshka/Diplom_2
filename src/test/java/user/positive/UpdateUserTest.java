package user.positive;

import io.qameta.allure.junit4.DisplayName;

import io.restassured.response.Response;
import org.example.clients.UserClient;

import org.example.generators.GenerateUserData;
import org.example.models.user.CreateUser;

import org.example.models.user.UpdateUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class UpdateUserTest {
    public String email = GenerateUserData.generateEmail();
    public String password = GenerateUserData.generatePassword();
    public String name = GenerateUserData.generateName();
    public String authorizationToken;

    @Before
    public void setUp()  {

        CreateUser createdUser = new CreateUser(email,password, name);
        Response response = UserClient.register(createdUser);
        response.then().assertThat()
                .statusCode(200);
        response.then().assertThat().body("success", equalTo(true));
        authorizationToken = response.then().extract().body().path("accessToken");
    }

    @Test
    @DisplayName("Обновление имени для авторизованного пользователя")
    public void updateNameAuthorizedUser(){
        String name = GenerateUserData.generateName() + "лол";
        UpdateUser updatedUser = new UpdateUser(email, password, name);
        Response response = UserClient.updateUser(authorizationToken, updatedUser);
        response.then().assertThat()
                .statusCode(200);
        response.then().assertThat().body("success", equalTo(true));


    }

    @Test
    @DisplayName("Обновление почты для авторизованного пользователя")
    public void updateEmailAuthorizedUser(){
        String email = GenerateUserData.generateEmail() + ".pl";
        UpdateUser updatedUser = new UpdateUser(email, password, name);
        Response response = UserClient.updateUser(authorizationToken, updatedUser);
        response.then().assertThat()
                .statusCode(200);
        response.then().assertThat().body("success", equalTo(true));


    }

    @Test
    @DisplayName("Обновление пароля для авторизованного пользователя")
    public void updatePasswordAuthorizedUser(){
        String password = GenerateUserData.generatePassword() + "@";
        UpdateUser updatedUser = new UpdateUser(email, password, name);
        Response response = UserClient.updateUser(authorizationToken, updatedUser);
        response.then().assertThat()
                .statusCode(200);
        response.then().assertThat().body("success", equalTo(true));


    }


    @After
    public void deleteUser()  {
        UserClient.deleteUser(authorizationToken);

    }
}