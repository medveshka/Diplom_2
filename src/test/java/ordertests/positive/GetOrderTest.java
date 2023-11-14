package ordertests.positive;

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


public class GetOrderTest {
    public String email = GenerateUserData.generateEmail();
    public String password = GenerateUserData.generatePassword();
    public String name = GenerateUserData.generateName();
    public String authorizationToken;

    @Before
    public void createNewUserAndOrder(){

        CreateUser createdUser = new CreateUser(email,password, name);
        Response response = UserClient.register(createdUser);
        authorizationToken = response.then().extract().body().path("accessToken");

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
        OrderClient.createOrderAuthorized(authorizationToken, order);
    }


    @Test
    @DisplayName("Получение заказа")
    public void getOrdersTest(){

        Response response = OrderClient.getOrdersByToken(authorizationToken);
        response.then().assertThat()
                .statusCode(200);
        response.then().assertThat().body("success", equalTo(true));


    }
    @After
    public void deleteUser()  {
        UserClient.deleteUser(authorizationToken);

    }


}