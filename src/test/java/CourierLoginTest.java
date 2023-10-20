import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.Courier;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest {
    private Response getResponse(Courier courier){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier/login");
        return response;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
    @Test
    public void testForSuccessLoginCourier(){
        Courier courier = new Courier("Guzelka", "12345");
        Response response = getResponse(courier);
        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }
    @Test
    public void testForRequiredPasswordCourier(){
        Courier courier = new Courier("Guzelka", "");
        Response response = getResponse(courier);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }
    @Test
    public void testForRequiredLoginCourier(){
        Courier courier = new Courier("", "12345");
        Response response = getResponse(courier);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }
    @Test
    public void testForWrongPasswordCourier(){
        Courier courier = new Courier("Guzelka", "123456");
        Response response = getResponse(courier);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
    @Test
    public void testForWrongLoginCourier(){
        Courier courier = new Courier("Guzelk", "12345");
        Response response = getResponse(courier);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

}
