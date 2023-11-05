package userTests.negativeTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.constantsAPI.EndPoints;
import org.example.generators.generateUserData;
import org.example.models.user.CreateUser;
import org.example.models.user.DeleteUser;
import org.example.models.user.LogInUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class LogInUserUnauthorizedTest {
    public String email = generateUserData.generateEmail();
    public String password = generateUserData.generatePassword();
    public String name = generateUserData.generateName();
    public String authorizationToken;

    @Before
    public void setUp()  {
        RestAssured.baseURI = EndPoints.BASE_URL;
        CreateUser createdUser = new CreateUser(email,password, name);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(createdUser)
                .when()
                .post(EndPoints.REGISTER);
        response.then().assertThat()
                .statusCode(200);
        response.then().assertThat().body("success", equalTo(true));
        authorizationToken = response.then().extract().body().path("accessToken");

            }

    @Test
    @DisplayName("Логин c неверной почтой")
    public void logInUserWrongLogin(){
        LogInUser LoggedInUser = new LogInUser(generateUserData.generateEmail(),password);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(LoggedInUser)
                .when()
                .post(EndPoints.LOGIN);
        response.then().assertThat()
                .statusCode(401);
        response.then().assertThat().body("message", equalTo("email or password are incorrect"));

    }



    @After
    public void DeleteUser()  {
        DeleteUser delete = new DeleteUser(email,password);
        given()
                .header("Authorization", authorizationToken)
                .body(delete)
                .delete(EndPoints.USER);

    }

}
