package userTests.positiveTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.constantsAPI.EndPoints;
import org.example.generators.generateUserData;
import org.example.models.user.CreateUser;
import org.example.models.user.DeleteUser;
import org.example.models.user.UpdateUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class UpdateUserTest {
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
    @DisplayName("Обновление имени для авторизованного пользователя")
    public void updateNameAuthorizedUser(){
        String name = generateUserData.generateName() + "лол";
        UpdateUser updatedUser = new UpdateUser(email, password, name);
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", authorizationToken)
                .and()
                .body(updatedUser)
                .when()
                .patch(EndPoints.USER);
        response.then().assertThat()
                .statusCode(200);
        response.then().assertThat().body("success", equalTo(true));


    }

    @Test
    @DisplayName("Обновление почты для авторизованного пользователя")
    public void updateEmailAuthorizedUser(){
        String email = generateUserData.generateEmail() + ".pl";
        UpdateUser updatedUser = new UpdateUser(email, password, name);
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", authorizationToken)
                .and()
                .body(updatedUser)
                .when()
                .patch(EndPoints.USER);
        response.then().assertThat()
                .statusCode(200);
        response.then().assertThat().body("success", equalTo(true));


    }

    @Test
    @DisplayName("Обновление пароля для авторизованного пользователя")
    public void updatePasswordAuthorizedUser(){
        String password = generateUserData.generatePassword() + "@";
        UpdateUser updatedUser = new UpdateUser(email, password, name);
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", authorizationToken)
                .and()
                .body(updatedUser)
                .when()
                .patch(EndPoints.USER);
        response.then().assertThat()
                .statusCode(200);
        response.then().assertThat().body("success", equalTo(true));


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