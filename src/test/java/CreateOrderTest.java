import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.example.OrderApi;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

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

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
    @Test
    public void testForSuccessCreateOrder(){
        OrderApi.create("Guzel",
                "Karabaeva",
                "Kazan",
                "Prospect",
                "89178976300",
                1,
                "2023-10-20",
                "Hi",
                colors)
                .then()
                .assertThat()
                .body("track", notNullValue())
                .and()
                .statusCode(HttpStatus.SC_CREATED);
    }

}

