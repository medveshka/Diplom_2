package userTests.negativeTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.constantsAPI.EndPoints;
import org.example.generators.GenerateUserData;
import org.example.models.user.CreateUser;
import org.example.models.user.DeleteUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserRegisteredBeforeTest {
    public String email = GenerateUserData.generateEmail();
    public String password = GenerateUserData.generatePassword();
    public String name = GenerateUserData.generateName();
    public String authorizationToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = EndPoints.BASE_URL;
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void CreateUserRegisteredBefore(){
        CreateUser createdUser = new CreateUser(email,password, name);
        Response responseCreateNewUser = given()
                .header("Content-type", "application/json")
                .and()
                .body(createdUser)
                .when()
                .post(EndPoints.REGISTER);


        Response resSecondRegistration = given()
                .header("Content-type", "application/json")
                .and()
                .body(createdUser)
                .when()
                .post(EndPoints.REGISTER);
        resSecondRegistration.then().assertThat()
                .statusCode(403);
        resSecondRegistration.then().assertThat().body("message", equalTo("User already exists"));
        authorizationToken = responseCreateNewUser.then().extract().body().path("accessToken");


    }



    @After
    public void DeleteUser() {
        DeleteUser delete = new DeleteUser(email,password);
        given()
                .header("Authorization", authorizationToken)
                .body(delete)
                .delete(EndPoints.USER);

    }
}