package user;

import config.DataProvider;
import org.apache.maven.surefire.shared.lang3.RandomStringUtils;

public class User{
    String email;
    String password;
    String name;

    public User(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User getRandomUser() {
        return new User(
                RandomStringUtils.randomAlphanumeric(10) + "@yandex.ru",
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    public static User getUserWithoutEmail() {
        return new User(
                DataProvider.getEmptyField(),
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    public static User getUserWithoutPassword() {
        return new User(

                RandomStringUtils.randomAlphanumeric(10) + "@yandex.ru",
                DataProvider.getEmptyField(),
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    public static User getUserWithoutName() {
        return new User(
                RandomStringUtils.randomAlphanumeric(10) + "@yandex.ru",
                RandomStringUtils.randomAlphanumeric(10),
                DataProvider.getEmptyField()
        );
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
