package test.GPS;



import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class getToken {

	public static String token="";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    try {
			HttpResponse<String> response = Unirest.get("http://mc.test.eeioe.com/getToken?appkey=91952023a7284af32fa62d6ccc169e7d&appsecret=f512da462085f692df7a8cf16204f077").asString();
		    //System.out.println("ddd "+getJsonValue(response.getBody()));
			if(token.equals(""))
			  token =getJsonValue(response.getBody());
			
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    long epoch = System.currentTimeMillis()/1000;
		    HttpResponse<String> responseDeviceList = Unirest.post("http://mc.test.eeioe.com/getDeviceList")
		    	      .header("token", token)
		    	      .header("Content-Type", "application/json")
		    	      .header("timestamp", epoch+"" )
		    	      .body("{\n\t\"appkey\":\"91952023a7284af32fa62d6ccc169e7d\"\n}")
		    	      .asString(); 
		   System.out.println(responseDeviceList.getBody());
	    } catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
public static String getJsonValue(String JsonString) {
	
	//String jsonString = "{\"id\":1, \"name\":\"lzj\", \"cars\":[\"audi\", \"baoma\", \"benci\"]}"; 
	JsonParser parser = new JsonParser(); 
	JsonElement jsonNode = parser.parse(JsonString);
	System.out.println("here1");
	if (jsonNode.isJsonObject()) { 
		JsonObject jsonObject = jsonNode.getAsJsonObject(); 
		System.out.println("here");
	    JsonElement jsonElementName = jsonObject.get("msg"); 
	    String msg = jsonElementName.getAsString(); 
	    System.out.println("msg : " + msg); 
	    
	    JsonObject jsonOjectData = jsonObject.getAsJsonObject("data"); 
	    JsonElement jsonElementToken = jsonOjectData.get("token"); 
	    String token = jsonElementToken.getAsString(); 
	    System.out.println("token : " + token); 
	    JsonString=token;
	
	/*JsonElement jsonElementCars = jsonObject.get("cars"); 
	JsonArray arrays = jsonElementCars.getAsJsonArray(); 
	for(int i=0; i<arrays.size(); i++){ 
		JsonElement jsonElementArray = arrays.get(i); 
		String car = jsonElementArray.getAsString(); 
		System.out.println("car" + i + " : " + car); 
		} */
	}

	return JsonString;
}

}
