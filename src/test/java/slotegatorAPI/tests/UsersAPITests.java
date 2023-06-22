package slotegatorAPI.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Test;
import slotegatorAPI.testHelper.FileHelper;
import slotegatorAPI.testHelper.JSONValidation;
import slotegatorAPI.testHelper.PlayerFinderHelper;
import slotegatorAPI.testHelper.SortingHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class UsersAPITests {

    static final String MAIN_HOST = "https://testslotegrator.com/api/automationTask";
    List<String> list = new ArrayList<>();

    @Test
    public void newWW() throws IOException {
        JSONArray jsonResponseArray = new JSONArray(findAllUsers().asString());

        // Creating of new 12 users from file - all _id will be printed in Console to not to loose id if you wanna check it after manually
        createNewUsers();

        //Requesting first user of created and cheking JSON structure
        Assert.assertTrue(JSONValidation.findUserResponseIsValid(findOneUser()));

        //Request all Player's data and sort them ny name
        JSONArray sortedJsonArray = SortingHelper.sortJsonArray(jsonResponseArray, "name");

        //Simple print for visual checking of sorting
        System.out.println(sortedJsonArray);

        //Deleting all priviously created users
        deleteListOfUsers();

        //Check all users were deleted
        Assert.assertTrue(PlayerFinderHelper.checkUsersFromFile(jsonResponseArray));

    }

    public String getUserToken() {
        Response response = RestAssured.given()
                .auth().basic("test91", "oiwSMUmQo9uuuyc9KITwH")
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"email\": \"comaliesfy@gmail.com\",\n" +
                        "  \"password\": \"t0NxYyOwzh9n\"\n" +
                        "}")
                .post("https://testslotegrator.com/api/tester/login");
        assertEquals(201, response.getStatusCode());

        JsonPath body = response.getBody().jsonPath();
        String token = body.getString("accessToken");
        return token;
    }

    public void createNewUsers() throws IOException {
        for (int i = 0; i < FileHelper.jsonSize(); i++) {
            Response response = RestAssured.given()
                    .header("Authorization", "Bearer " + getUserToken())
                    .contentType(ContentType.JSON)
                    .body(FileHelper.jsonFileData().get(i).toString())
                    .post(MAIN_HOST + "/create");
            assertEquals(201, response.getStatusCode());
            JsonPath body = response.getBody().jsonPath();
            System.out.println(body.getString("_id"));
            list.add(body.getString("_id"));
        }

    }

    public Response findAllUsers() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + getUserToken())
                .contentType(ContentType.JSON)
                .get(MAIN_HOST + "/getAll");
        assertEquals(200, response.getStatusCode());
        return response;
    }


    public String findOneUser() throws IOException {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + getUserToken())
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"email\": "+FileHelper.jsonFileData().get(0).get("email")+"\n" +
                        "}")
                .post(MAIN_HOST + "/getOne");
        assertEquals(201, response.getStatusCode());
        String body = response.getBody().asString();
        return body;

    }

    public void deleteListOfUsers() throws IOException {
        for (int i = 0; i < FileHelper.jsonSize(); i++) {
            Response response = RestAssured.given()
                    .header("Authorization", "Bearer " + getUserToken())
                    .delete(MAIN_HOST + "/deleteOne/" + list.get(i));
            assertEquals(200, response.getStatusCode());
        }
    }
}
