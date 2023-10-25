import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.example.CourierApi;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class CourierLoginTest {
    Integer id;
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
    @Test
    public void testForSuccessLoginCourier(){
        CourierApi.create("Guzelka", "12345", "Guzel");
        id = CourierApi.login("Guzelka", "12345")
                .then()
                .assertThat()
                .body("id", notNullValue())
                .and()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("id");
    }
    @Test
    public void testForRequiredPasswordCourier(){
        CourierApi.login("Guzelka", "")
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
    @Test
    public void testForRequiredLoginCourier(){
        CourierApi.login("", "12345")
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
    @Test
    public void testForWrongPasswordCourier(){
        CourierApi.create("Guzelka", "12345", "Guzel");
        CourierApi.login("Guzelka", "123456")
                .then()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(HttpStatus.SC_NOT_FOUND);
        id = CourierApi.login("Guzelka", "12345")
                .then()
                .extract()
                .path("id");
    }
    @Test
    public void testForWrongLoginCourier(){
        CourierApi.create("Guzelka", "12345", "Guzel");
        CourierApi.login("Guzelk", "123456")
                .then()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(HttpStatus.SC_NOT_FOUND);
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
