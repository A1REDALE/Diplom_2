package order;

import io.restassured.response.ValidatableResponse;

import static config.Config.*;
import static io.restassured.RestAssured.given;

public class OrderClient {

    public ValidatableResponse createOrder(Order order, String token) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .baseUri(URL)
                .body(order)
                .post(ORDER)
                .then().log().all();
    }

    public ValidatableResponse getUserOrders(String token) {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .baseUri(URL)
                .get(ORDER)
                .then().log().all()
                .assertThat();
    }
}