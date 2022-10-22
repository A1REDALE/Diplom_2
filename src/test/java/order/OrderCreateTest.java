package order;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrderCreateTest {
    User user;
    UserClient userClient;
    String token;
    OrderClient orderClient;
    boolean isOk;

    @Before
    public void setup() {
        user = User.getRandomUser();
        userClient = new UserClient();
        orderClient = new OrderClient();
        token = userClient.createUser(user)
                .extract()
                .path("accessToken");
    }

    @DisplayName("Создание заказа с ингредиентами и авторизацией")
    @Test
    public void createOrderWithAuthorizationAndIngredients(){
        isOk = orderClient.createOrder(Order.getCorrectOrder(), token)
                .statusCode(200)
                .extract().path("success");
        assertTrue(isOk);

    }

    @DisplayName("Создание заказа без авторизации")
    @Test
    public void createOrderWithoutAuthorization(){
        isOk = orderClient.createOrder(Order.getCorrectOrder(), "Null")
                .statusCode(200)
                .extract().path("success");
        assertTrue(isOk);
    }

    @DisplayName("Создание заказа без ингредиентов")
    @Test
    public void createOrderWithoutIngredients(){
        isOk = orderClient.createOrder(Order.getEmptyOrder(), token)
                .statusCode(400)
                .extract().path("success");
        assertFalse(isOk);
    }

    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Test
    public void createOrderWithWrongHash(){
        orderClient.createOrder(Order.getOrderWithWrongHash(), token)
                .statusCode(500);
    }
}
