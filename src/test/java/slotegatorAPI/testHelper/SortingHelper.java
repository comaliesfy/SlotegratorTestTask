package slotegatorAPI.testHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SortingHelper {

    public static JSONArray sortJsonArray(JSONArray jsonArray, final String key) {

        Comparator<JSONObject> comparator = new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject obj1, JSONObject obj2) {

                if (Objects.equals(obj1.get(key).toString(), "null") && !Objects.equals(obj2.get(key).toString(), "null")) {
                    return 1;
                } else if (!Objects.equals(obj1.get(key).toString(), "null") && Objects.equals(obj2.get(key).toString(), "null")) {
                    return -1;
                } else if (Objects.equals(obj1.get(key).toString(), "null") && Objects.equals(obj2.get(key).toString(), "null")) {
                    return 0;
                } else {

                    return obj1.getString(key).compareTo(obj2.getString(key));

                }
            }
        };

        List<JSONObject> jsonList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonList.add(jsonArray.getJSONObject(i));
        }

        jsonList.sort(comparator);
        return new JSONArray(jsonList);

    }
}