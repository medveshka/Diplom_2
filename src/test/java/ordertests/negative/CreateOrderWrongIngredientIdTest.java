package ordertests.negative;

import io.qameta.allure.junit4.DisplayName;

import io.restassured.response.Response;
import org.example.clients.OrderClient;
import org.example.clients.UserClient;

import org.example.generators.GenerateUserData;
import org.example.models.order.CreateOrder;
import org.example.models.user.CreateUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.CoreMatchers.equalTo;


public class CreateOrderWrongIngredientIdTest {
    public String email = GenerateUserData.generateEmail();
    public String password = GenerateUserData.generatePassword();
    public String name = GenerateUserData.generateName();
    public String authorizationToken;

    @Before
    public void createNewUser(){

        CreateUser createdUser = new CreateUser(email,password, name);
        Response response = UserClient.register(createdUser);
        response.then().assertThat()
                .statusCode(200);
        response.then().assertThat().body("success", equalTo(true));
        authorizationToken = response.then().extract().body().path("accessToken");
    }



    @Test
    @DisplayName("Создание заказа c неверным id ингредиента")
    public void createOrderWrongIngredientId(){


        List<String> ingredients = new ArrayList<>();
        ingredients.add(GenerateUserData.generateIdNumber());
        CreateOrder order = new CreateOrder(ingredients);
        Response response = OrderClient.createOrderAuthorized(authorizationToken, order);
        response.then().assertThat()
                .statusCode(500);

    }
    @After
    public void deleteUser()  {
        UserClient.deleteUser(authorizationToken);

    }


}