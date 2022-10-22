package user;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDataUpdateTest {
    User user;
    UserClient userClient;
    private String token;
    boolean isOk;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getRandomUser();
        token = userClient.createUser(user).extract().body().path("accessToken");
    }

    @After
    public void tearDown() {
        userClient.deleteUser(token);
    }

    @DisplayName("Изменение полей email и name с авторизацией")
    @Test
    public void checkUpdateUserEmailAndNameWithAuthorization() {
        user.setName("Harry");
        user.setEmail("Potter@MерзкийМагл.сom");
        isOk = userClient.updateUserData(user, token)
                .statusCode(200)
                .extract().path("success");
        assertTrue(isOk);
    }

    @DisplayName("Изменение поля password c авторизацией")
    @Test
    public void checkChangeUserPasswordWithAuthorization() {
        user.setPassword("228");
        isOk = userClient.updateUserData(user, token)
                .statusCode(200)
                .extract().path("success");
        assertTrue(isOk);
        String expected = "228";
        assertEquals(expected, user.getPassword());
    }

    @DisplayName("Изменение данных пользователя без авторизации")
    @Test
    public void checkChangeUserDataWithoutAuthorization() {
        user.setName("Harry");
        user.setEmail("Potter@MерзкийМагл.сom");
        user.setPassword("228");
        isOk = userClient.updateUserData(user, "NULL")
                .statusCode(401)
                .extract().path("success");
        assertFalse(isOk);
    }
}
