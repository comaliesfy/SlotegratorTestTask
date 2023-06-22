package slotegatorAPI.testHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONValidation {

    //Lazy but working decision, would be better to have json schema for proper validation

    public static boolean findUserResponseIsValid(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.readValue(json, PlayerResult.class);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

}
