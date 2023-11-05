package orderTests.negativeTests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.constantsAPI.EndPoints;
import org.example.generators.generateUserData;
import org.example.models.order.CreateOrder;
import org.example.models.user.CreateUser;
import org.example.models.user.DeleteUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class GetOrderUnauthorizedUserTest {
    public String email = generateUserData.generateEmail();
    public String password = generateUserData.generatePassword();
    public String name = generateUserData.generateName();
    public String authorizationToken;

    @Before
    public void createNewUser(){
        RestAssured.baseURI = EndPoints.BASE_URL;
        CreateUser createdUser = new CreateUser(email,password, name);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(createdUser)
                .when()
                .post(EndPoints.REGISTER);
        authorizationToken = response.then().extract().body().path("accessToken");


        String bun = given()
                .get(EndPoints.INGREDIENTS)
                .then().extract().body().path("data[0]._id");
        String main = given()
                .get(EndPoints.INGREDIENTS)
                .then().extract().body().path("data[1]._id");
        String sauce = given()
                .get(EndPoints.INGREDIENTS)
                .then().extract().body().path("data[4]._id");


        List<String> ingredients = new ArrayList<>();
        ingredients.add(bun);
        ingredients.add(main);
        ingredients.add(sauce);


        CreateOrder order = new CreateOrder(ingredients);
        given()
                .header("Content-type", "application/json")
                .header("Authorization", authorizationToken)
                .and()
                .body(order)
                .when()
                .post(EndPoints.ORDERS);
    }


    @Test
    @DisplayName("Получение заказа")
    public void GetOrderUnauthorizedTest(){

        Response response = given()
                .get(EndPoints.ORDERS);
        response.then().assertThat()
                .statusCode(401);
        response.then().assertThat().body("message", equalTo("You should be authorised"));


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