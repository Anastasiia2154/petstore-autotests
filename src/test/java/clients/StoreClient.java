package clients;

import config.Logger;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.Order;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.internal.matchers.StringContains.containsString;

public class StoreClient {

    static {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    public void getInventory() {
        Logger.out.info("Sending request to GET inventory");
        given()
                .when()
                .get("/store/inventory")
                .then()
                .log().all()
                .statusCode(200)
                .body("$", notNullValue())
                .body("sold", greaterThanOrEqualTo(0))
                .body("string", greaterThanOrEqualTo(0))
                .body("pending", greaterThanOrEqualTo(0))
                .body("available", greaterThanOrEqualTo(0))
                .body("status", greaterThanOrEqualTo(0));
        Logger.out.info("Inventory received successfully");
    }

    public void getInventoryInvalidRequest() {
        Logger.out.info("Sending POST request to receive (invalid request).");
        given()
                .when()
                .post("/store/inventory")
                .then()
                .log().all()
                .statusCode(405);
        Logger.out.info("Invalid request handled");
    }

    public int createOrder(Order order) {
        Logger.out.info("Sending POST request to create order");
        Response response = given()
                .header("Content-Type", "application/json")
                .body(order)
                .log().all()
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", notNullValue())
                .extract()
                .response();
        int orderId = response.path("id");
        Logger.out.info("Order created successfully with ID:" + orderId);
        return orderId;
    }

    public Response createOrderWithMissingFields(Order order) {
        Logger.out.info("Create order with missing fields");
        return given()
                .header("Content-Type", "application/json")
                .body(order)
                .log().all()
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .statusCode(400)
                .body("message", containsString("missing required fields"))
                .extract()
                .response();
    }

    public Response createOrderWithInvalidData(Order order) {
        Logger.out.info("Create order with invalid data");
        return given()
                .header("Content-Type", "application/json")
                .body(order)
                .log().all()
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .statusCode(400)
                .body("message", containsString("invalid data"))
                .extract()
                .response();
    }

    public Response getOrderById(Object orderId) {
        Logger.out.info("Sending GET request to receive order");
        return given()
                .when()
                .get("/store/order/" + orderId)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response getOrderWithValidation(Object orderId) {
        Response response = getOrderById(orderId);
        int statusCode = response.getStatusCode();
        Logger.out.info("GET order by ID");
        if (statusCode == 404) {
            response.then().body("message", anyOf(
                    containsString("Order not found"),
                    containsString("NumberFormatException")
            ));
            Logger.out.warn("Order not found for ID:" + orderId);
        }
        return response;
    }

    public void deleteOrder(int id) {
        Logger.out.info("Sending DELETE request for order ID: " + id);
        Response response = given()
                .when()
                .delete("/store/order/" + id)
                .then()
                .extract()
                .response();

        if (response.statusCode() == 200) {
            Logger.out.info("Order deleted successfully. Checking if order ID " + id);
            given()
                    .when()
                    .get("/store/order/" + id)
                    .then()
                    .statusCode(404);
            Logger.out.info("Order ID " + id + " confirmed as deleted");
        } else {
            response.then().body("message", containsString("Order Not Found"));
            Logger.out.warn("Failed to delete order ID : " + id + response.path("message"));
        }
    }
}