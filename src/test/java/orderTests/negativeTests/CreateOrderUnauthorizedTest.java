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


public class CreateOrderUnauthorizedTest {

    @Before
    public void createNewUser() {
        RestAssured.baseURI = EndPoints.BASE_URL;
    }


    @Test
    @DisplayName("Создание заказа c авторизаций и списком ингредиентов")
    public void createOrderIngredientsAuthorizedUser(){
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
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(EndPoints.ORDERS);
        response.then().assertThat()
                .statusCode(401);
        response.then().assertThat().body("success", equalTo(true));
    }



}