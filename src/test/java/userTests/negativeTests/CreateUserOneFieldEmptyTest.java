package userTests.negativeTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.constantsAPI.EndPoints;
import org.example.generators.generateUserData;
import org.example.models.user.CreateUser;
import org.example.models.user.DeleteUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserOneFieldEmptyTest {
    public String email = generateUserData.generateEmail();
    public String password = "";
    public String name = generateUserData.generateName();
    public String authorizationToken;

    @Before
    public void setUp()  {
        RestAssured.baseURI = EndPoints.BASE_URL;
    }

    @Test
    @DisplayName("Невозможно создать нового пользователя - не заполнено поле логин")
    public void CreateUserEmptyEmail(){
        CreateUser createdUser = new CreateUser(email,password, name);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(createdUser)
                .when()
                .post(EndPoints.REGISTER);
        response.then().assertThat()
                .statusCode(403);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"));
        authorizationToken = response.then().extract().body().path("accessToken");
    }



    @After
    public void DeleteUser()  {
        if (authorizationToken != null) {
        DeleteUser delete = new DeleteUser(email,password);
        given()
                .header("Authorization", authorizationToken)
                .body(delete)
                .delete(EndPoints.USER);

    }
    }
}