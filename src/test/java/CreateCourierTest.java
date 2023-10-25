import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.example.CourierApi;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class CreateCourierTest {
    Integer id;
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
    @Test
    public void testForUniqueCourier(){
        CourierApi.create("Guzelka", "12345", "Guzel");
        CourierApi.create("Guzelka", "12345", "Guzel")
                .then()
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(HttpStatus.SC_CONFLICT);
        id = CourierApi.login("Guzelka", "12345")
                .then()
                .extract()
                .path("id");
    }
    @Test
    public void testForRequiredPassword(){
        CourierApi.create("Guzelka", null, "Guzel")
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
    @Test
    public void testForRequiredLogin(){
        CourierApi.create(null, "12345", "Guzel")
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
    @Test
    public void testForSuccessCreateCourier(){
        CourierApi.create("Guzelka", "12345", "Guzel")
                .then()
                .assertThat()
                .body("ok", equalTo(true))
                .and()
                .statusCode(HttpStatus.SC_CREATED);
        id = CourierApi.login("Guzelka", "12345")
                .then()
                .extract()
                .path("id");
    }

    @After
    public void deleteCourier() {
        if(id != null) {
            CourierApi.delete(id);
        }
    }
}
