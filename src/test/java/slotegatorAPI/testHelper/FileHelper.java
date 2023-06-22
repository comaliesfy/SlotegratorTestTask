package slotegatorAPI.testHelper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class FileHelper {
    static ObjectMapper objectMapper = new ObjectMapper();
    static File file = new File("src/test/resources/players_list.json");

    public static JsonNode jsonFileData() throws IOException {
        JsonNode jsonNode = objectMapper.readTree(file);
        return jsonNode;
    }

    public static int jsonSize() throws IOException {
        return jsonFileData().size();
    }

}
