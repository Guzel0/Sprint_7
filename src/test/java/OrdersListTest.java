import com.google.gson.Gson;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.example.NearStations;
import org.example.OrderApi;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class OrdersListTest {
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
        OrderApi.getList(227315, json, 30, 0)
                .then()
                .assertThat()
                .body("orders", notNullValue())
                .and()
                .statusCode(HttpStatus.SC_OK);
    }

}

