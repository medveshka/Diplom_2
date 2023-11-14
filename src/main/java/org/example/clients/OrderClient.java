package org.example.clients;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.constantsAPI.EndPoints;
import org.example.models.order.CreateOrder;
import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {

    @Step("Создание заказа без авторизации")
    public static Response createOrderUnauthorized(CreateOrder createdOrder) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(createdOrder)
                .when()
                .post(EndPoints.ORDERS);
    }

    @Step("Создание заказа с авторизацией")
    public static Response createOrderAuthorized(String accessToken, CreateOrder createdOrder) {
        return given()
                .header("authorization", accessToken)
                .spec(getBaseSpec())
                .and()
                .body(createdOrder)
                .when()
                .post(EndPoints.ORDERS);
    }

    @Step("Получение заказов пользователя")
    public static Response getOrdersByToken(String accessToken) {
        return given()
                .header("authorization", accessToken)
                .spec(getBaseSpec())
                .get(EndPoints.ORDERS);
    }

    @Step("Получение заказов пользователя без авторизации")
    public static Response getOrdersUnauthorized() {
        return given()
                .spec(getBaseSpec())
                .get(EndPoints.ORDERS);
    }

    @Step("Получение ингредиентов")
    public static Response getIngredients() {
        return  given()
                .spec(getBaseSpec())
                .get(EndPoints.INGREDIENTS);

    }
}


