package ordertests.negative;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.clients.OrderClient;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

public class GetOrderUnauthorizedUserTest {

    @Test
    @DisplayName("Получение заказа")
    public void getOrderUnauthorizedTest(){

        Response response = OrderClient.getOrdersUnauthorized();
        response.then().assertThat()
                .statusCode(401);
        response.then().assertThat().body("message", equalTo("You should be authorised"));


    }



}