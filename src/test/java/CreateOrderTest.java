import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.NewOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    String[] colors;
    public CreateOrderTest(String[] colors) {
         this.colors = colors;
    }
    @Parameterized.Parameters
    public static Object[][] getColors() {
        String[] color_black = {"BLACK"};
        String[] color_grey = {"GREY"};
        String[] color_all = {"BLACK", "GREY"};
        String[] color_none = {""};
        return new Object[][] {
                {color_black},
                {color_grey},
                {color_all},
                {color_none}
        };
    }

    private Response getResponse(NewOrder newOrder){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(newOrder)
                        .when()
                        .post("/api/v1/orders");
        return response;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
    @Test
    public void testForSuccessCreateOrder(){
        NewOrder newOrder = new NewOrder(
                "Guzel",
                "Karabaeva",
                "Kazan",
                "Prospect",
                "89178976300",
                1,
                "2023-10-20",
                "Hi",
                colors
        );
        Response response = getResponse(newOrder);
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }

}

