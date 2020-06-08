import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

public class JsonTest {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("code","123");
        map.put("name","sdai");
        String aa = "hro";
        System.out.println(JSONObject.toJSON(map));
        System.out.println(map);
        System.out.println(JSONObject.toJSON(map));
    }
}
