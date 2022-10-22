package user;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserLogInTest {
    User user;
    UserClient userClient;
    boolean isOk;
    String token;


    @Before
    public void setUp(){
        user = User.getRandomUser();
        userClient = new UserClient();
        token = userClient.createUser(user).extract().body().path("accessToken");
    }

    @After
    public void tearDown(){
            userClient.deleteUser(token);
        }

    @DisplayName("Авторизация нового пользователя")
    @Test
    public void checkLogInAlreadyExistUser(){
        isOk = userClient.login(UserCredentials.from(user))
                .statusCode(200)
                .extract().path("success");
        assertTrue(isOk);
    }

    @DisplayName("Авторизация пользователя с неверным логином")
    @Test
    public void checkLogInUserWithWrongEmail(){
        isOk = userClient.login(UserCredentials.getCredentialWithWrongEmail(user))
                .statusCode(401)
                .extract().path("success");
        assertFalse(isOk);
    }

    @DisplayName("Авторизация пользователя с неверным паролем")
    @Test
    public void checkLogInUserWithWrongPassword(){
        isOk = userClient.login(UserCredentials.getCredentialWithWrongPassword(user))
                .statusCode(401)
                .extract().path("success");
        assertFalse(isOk);
    }
}







