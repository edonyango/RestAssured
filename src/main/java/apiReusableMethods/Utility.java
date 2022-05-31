package apiReusableMethods;

import org.json.JSONObject;

public class Utility {
	
	public String graphqlToJson(String payload) {
		JSONObject json = new JSONObject();
		json.put("query", payload);
		return json.toString();
	}

}
