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


public class CreateOrderNoIngredientsTest {
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
        response.then().assertThat()
                .statusCode(200);
        response.then().assertThat().body("success", equalTo(true));
        authorizationToken = response.then().extract().body().path("accessToken");
    }



    @Test
    @DisplayName("Создание заказа c авторизаций и пустым списком ингредиентов")
    public void createOrderNoIngredientsAuthorizedUser(){


        List<String> ingredients = new ArrayList<>();


        CreateOrder order = new CreateOrder(ingredients);
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", authorizationToken)
                .and()
                .body(order)
                .when()
                .post(EndPoints.ORDERS);
        response.then().assertThat()
                .statusCode(400);
        response.then().assertThat().body("message", equalTo("Ingredient ids must be provided"));
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