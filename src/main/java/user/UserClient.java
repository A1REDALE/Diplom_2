package user;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static config.Config.*;
import static io.restassured.RestAssured.given;

public class UserClient {


    protected RequestSpecification getSpec() {
        return given().log().all()
                .header("Content-type", "application/json")
                .baseUri(URL);
    }

    protected RequestSpecification getSpecWithToken(String token){
        return given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(URL);
    }

    public ValidatableResponse createUser(User user) {
        return getSpec()
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all()
                .assertThat();
    }

    public ValidatableResponse login(UserCredentials userCredentials) {
        return getSpec()
                .body(userCredentials)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat();
    }

    public ValidatableResponse updateUserData(User user,String token) {
        return getSpecWithToken(token)
                .body(user)
                .when()
                .patch(USER)
                .then().log().all()
                .assertThat();
    }

    public void deleteUser(String token) {
        given()
                .header("Authorization", token)
                .baseUri(URL)
                .delete(USER).then().log().all();
    }
}

