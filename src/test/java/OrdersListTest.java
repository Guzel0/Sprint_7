import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.NearStations;
import org.example.Order;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OrdersListTest {
    private Response getResponse(Order order){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(order)
                        .when()
                        .get("/api/v1/orders");
        return response;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
    @Test
    public void testForSuccessOrdersList(){
        Gson gson = new Gson();
        String[] nearestStation = {"1", "2"};
        NearStations nearestStations = new NearStations(nearestStation);
        String json = gson.toJson(nearestStations);
        Order order = new Order(227315, json, 30, 0);
        Response response = getResponse(order);
        response.then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }

}

