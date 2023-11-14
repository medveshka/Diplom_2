package ordertests.negative;

import io.qameta.allure.junit4.DisplayName;

import io.restassured.response.Response;
import org.example.clients.OrderClient;

import org.example.models.order.CreateOrder;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.CoreMatchers.equalTo;


public class CreateOrderUnauthorizedTest {



    @Test
    @DisplayName("Создание заказа c без авторизации и списком ингредиентов")
    public void createOrderIngredientsUnauthorizedUser(){
        Response ingredientsList = OrderClient.getIngredients();


        String bun = ingredientsList
                .then().extract().body().path("data[0]._id");
        String main = ingredientsList
                .then().extract().body().path("data[1]._id");
        String sauce = ingredientsList
                .then().extract().body().path("data[4]._id");


        List<String> ingredients = new ArrayList<>();
        ingredients.add(bun);
        ingredients.add(main);
        ingredients.add(sauce);


        CreateOrder order = new CreateOrder(ingredients);
        Response response = OrderClient.createOrderUnauthorized(order);
        response.then().assertThat()
                .statusCode(401);
        response.then().assertThat().body("success", equalTo(true));
    }



}