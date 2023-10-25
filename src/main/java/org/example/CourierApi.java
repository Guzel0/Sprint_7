package org.example;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CourierApi {
    final static String CREATE_COURIER_URL = "/api/v1/courier";
    final static String DELETE_COURIER_URL = "/api/v1/courier/";
    final static String LOGIN_COURIER_URL = "/api/v1/courier/login";
    public static Response create(String login, String password, String firstName){
        NewCourier newCourier = new NewCourier(login, password, firstName);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(newCourier)
                .when()
                .post(CREATE_COURIER_URL);
    }
    public static Response login(String login, String password){
        Courier courier = new Courier(login, password);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(LOGIN_COURIER_URL);
    }
    public static Response delete(int id){
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(DELETE_COURIER_URL + id);
    }
}
