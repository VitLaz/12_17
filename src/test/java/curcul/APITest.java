package curcul;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class APITest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    void listUserTest() {
        given()
                .queryParam("page","2")
                .log().uri()
                .when()
                .get("/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total",is(12));
    }

    @Test
    void createUserTest() {
        given()
                .log().uri()
                .body("{\"name\": \"morpheus\",\"job\": \"leader\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    void updateUserTest() {
        given()
                .log().uri()
                .pathParam("user", "2")
                .body("{\"name\": \"morpheus\",\"job\": \"zion resident\"}")
                .contentType(ContentType.JSON)
                .when()
                .put("/api/users/{user}")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    void deleteUserTest() {
        given()
                .log().uri()
                .pathParam("user", "2")
                .when()
                .delete("/api/users/{user}")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }

    @Test
    void loginUnsuccessfulTest() {
        given()
                .log().uri()
                .body("{\"email\": \"peter@klaven\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

}
