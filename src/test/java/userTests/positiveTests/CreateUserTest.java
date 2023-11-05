package userTests.positiveTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import io.qameta.allure.junit4.DisplayName;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.example.generators.generateUserData;
import org.example.constantsAPI.EndPoints;
import org.example.models.user.CreateUser;
import org.example.models.user.DeleteUser;

public class CreateUserTest {
    public String email = generateUserData.generateEmail();
    public String password = generateUserData.generatePassword();
    public String name = generateUserData.generateName();
    public String authorizationToken;

    @Before
    public void setUp()  {
        RestAssured.baseURI = EndPoints.BASE_URL;
    }

    @Test
    @DisplayName("Создание нового пользователя")
    public void createNewUser(){
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



    @After
    public void DeleteUser()  {
        DeleteUser delete = new DeleteUser(email,password);
        given()
                .header("Authorization", authorizationToken)
                .body(delete)
                .delete(EndPoints.USER);

    }
}