import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.NewCourier;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateCourierTest {
    private Response getResponse(NewCourier newCourier){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(newCourier)
                        .when()
                        .post("/api/v1/courier");
        return response;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
    @Test
    public void testForUniqueCourier(){
        NewCourier newCourier = new NewCourier("Guzelka", "12345", "Guzel");
        Response response = getResponse(newCourier);
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }
    @Test
    public void testForRequiredPassword(){
        NewCourier newCourier = new NewCourier("Guzelka", null, "Guzel");
        Response response = getResponse(newCourier);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
    @Test
    public void testForRequiredLogin(){
        NewCourier newCourier = new NewCourier(null, "12345", "Guzel");
        Response response = getResponse(newCourier);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
    @Test
    public void testForSuccessCreateCourier(){
        NewCourier newCourier = new NewCourier("Guzelka01234", "12345", "Guzel");
        Response response = getResponse(newCourier);
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }
}
