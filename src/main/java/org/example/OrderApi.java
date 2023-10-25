package org.example;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderApi {
    final static String CREATE_ORDER_URL = "/api/v1/orders";
    final static String GET_ORDERS_LIST_URL = "/api/v1/orders";
    public static Response create(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color){
        NewOrder newOrder = new NewOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(newOrder)
                .when()
                .post(CREATE_ORDER_URL);
    }
    public static Response getList(int courierId, String nearestStation, int limit, int page){
        Order order = new Order(courierId, nearestStation, limit, page);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .get(GET_ORDERS_LIST_URL);
    }
}
