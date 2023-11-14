package user.negative;

import io.qameta.allure.junit4.DisplayName;

import io.restassured.response.Response;
import org.example.clients.UserClient;

import org.example.generators.GenerateUserData;
import org.example.models.user.CreateUser;

import org.example.models.user.LogInUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class LogInUserUnauthorizedTest {
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
    @DisplayName("Логин c неверной почтой")
    public void logInUserWrongLogin(){
        LogInUser loggedInUser = new LogInUser(GenerateUserData.generateEmail(),password);
        Response response = UserClient.login(loggedInUser);
        response.then().assertThat()
                .statusCode(401);
        response.then().assertThat().body("message", equalTo("email or password are incorrect"));

    }



    @After
    public void deleteUser()  {
        UserClient.deleteUser(authorizationToken);

    }

}
