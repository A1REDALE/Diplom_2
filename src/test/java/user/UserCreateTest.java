package user;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class UserCreateTest {
    User user;
    UserClient userClient;
    boolean isOk;
    String token;

    @Before
    public void setUp(){
        userClient = new UserClient();
    }

    @After
    public void tearDown(){

        if(isOk){
            token = userClient.login(UserCredentials.from(user)).extract().body().path("accessToken");
            userClient.deleteUser(token);
        }
    }

    @DisplayName("Регистрация нового пользователя")
    @Test
    public void createNewUser(){
        user = User.getRandomUser();
        isOk = userClient.createUser(user)
                .statusCode(200)
                .extract().path("success");
        assertTrue(isOk);
    }

    @DisplayName("Регистрация пользователя, который уже существует")
    @Test
    public  void createUserWhoAlreadyExist(){
        user = User.getRandomUser();
        userClient.createUser(user);
        userClient.createUser(user)
                .statusCode(403)
                .assertThat().body("message", equalTo("User already exists"));
    }

    @DisplayName("Регистрация пользователя без заполнения поля email")
    @Test
    public void  createUserWithoutEmail(){
        user = User.getUserWithoutEmail();
        userClient.createUser(user)
                .statusCode(403)
                .assertThat().body("message", equalTo("Email, password and name are required fields"));

    }

    @DisplayName("Регистрация пользователя без заполнения поля password")
    @Test
    public void  createUserWithoutPassword(){
        user = User.getUserWithoutPassword();
        userClient.createUser(user)
                .statusCode(403)
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @DisplayName("Регистрация пользователя без заполнения поля name")
    @Test
    public void  createUserWithoutName(){
        user = User.getUserWithoutName();
        userClient.createUser(user)
                .statusCode(403)
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }
}
