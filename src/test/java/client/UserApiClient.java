package client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserApiClient {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/api";

    @Step("Регистрируем пользователя с email: {email}")
    public String register(String email, String password) {
        Map<String, String> user = new HashMap<>();
        user.put("email", email);
        user.put("password", password);
        user.put("name", "Автотест");

        ValidatableResponse response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/auth/register")
                .then()
                .statusCode(200);

        return extractToken(response.extract().path("accessToken"));
    }

    @Step("Входим в систему с email: {email}")
    public String login(String email, String password) {
        Response response = given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}")
                .when()
                .post("/auth/login");

        if (response.statusCode() == 200) {
            return extractToken(response.jsonPath().getString("accessToken"));
        }
        return null;
    }

    @Step("Удаляем пользователя по токену")
    public void deleteUser(String accessToken) {
        if (accessToken == null) return;

        given()
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .delete("/auth/user")
                .then()
                .statusCode(202);
    }

    @Step("Удаляем пользователя по email и паролю")
    public void deleteUser(String email, String password) {
        String accessToken = login(email, password);
        deleteUser(accessToken);
    }

    private String extractToken(String rawToken) {
        if (rawToken != null && rawToken.startsWith("Bearer ")) {
            return rawToken.substring(7);
        }
        return rawToken;
    }
}
