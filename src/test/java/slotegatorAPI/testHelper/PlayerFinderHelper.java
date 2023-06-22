package slotegatorAPI.testHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerFinderHelper {

    // This monster is comparing player's emails from test file with all json data from response of /getAll
    //Throwing false if he could find common emails
    public static boolean checkUsersFromFile(JSONArray jsonArray) throws IOException {
        List<JSONObject> jsonList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonList.add(jsonArray.getJSONObject(i));
        }

        boolean hasCommonPlayers = false;

        for (int i = 0; i < FileHelper.jsonSize(); i++) {

            for (int j = 0; j < jsonList.size(); j++) {

                if (FileHelper.jsonFileData().get(i).get("email").toString().contains(jsonList.get(j).getString("email")) ) {
                    hasCommonPlayers = true;
                    break;
                }
            }
            if (hasCommonPlayers) {
                return false;
        }
    }
        return true;
    }


}
