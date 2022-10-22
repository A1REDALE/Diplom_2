package order;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GetUserOrderTest {
    OrderClient orderClient;
    User user;
    UserClient userClient;
    String token;
    boolean isOk;

    @Before
    public void setUp(){
        userClient = new UserClient();
        orderClient = new OrderClient();
        user = User.getRandomUser();
        token = userClient.createUser(user)
                .extract()
                .path("accessToken");
    }

    @DisplayName("Получение заказов авторизированного пользователя")
    @Test
    public void getOrdersFromUserWithAuthorization(){
        isOk = orderClient.getUserOrders(token)
                .statusCode(200)
                .extract().path("success");
        assertTrue(isOk);
    }

    @DisplayName("Получение заказов не авторизированного пользователя")
    @Test
    public void getOrdersFromUserWithoutAuthorization() {
        isOk = orderClient.getUserOrders("Null")
                .statusCode(401)
                .extract().path("success");
        assertFalse(isOk);
    }
}
