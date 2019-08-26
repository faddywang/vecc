package test.GPS;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
public class SignatureGenerator {

	public static void main(String[] args) {

		String readjson =readJsonFile("D:\\workplace\\GPS\\carinfo0729.json");
		String token="313effd2-86e7-49dd-9075-766f64649adc";
		String envData = getJsonValue(readjson,"envData");
		System.out.println(envData);
		String equipmentData= getJsonValue(readjson,"equipmentData");
		System.out.println(equipmentData);
		String processData= getJsonValue(readjson,"processData");
		System.out.println(processData);
		String resultData = getJsonValue(readjson,"resultData");
		System.out.println(resultData);
		String testData = getJsonValue(readjson,"testData");
		System.out.println(testData);
		String vehicleData= getJsonValue(readjson,"vehicleData");
		System.out.println(vehicleData);
	    String sign = generate(token, envData,
				 equipmentData, processData, resultData, testData, vehicleData);
	    System.out.println("sign:   "+sign);
	}
	
	public static String generate(String token, String envData,
			String equipmentData,String processData,String resultData,String testData,String vehicleData) {
		final List<String> sortData = new ArrayList<>();
		sortData.add(token);
		sortData.add(envData);
		sortData.add(equipmentData);
		sortData.add(processData);
		sortData.add(resultData);
		sortData.add(testData);
		sortData.add(vehicleData);
		Collections.sort(sortData);
		final StringBuilder builder = new StringBuilder();
		for (final String value : sortData) {
			builder.append(value);
		}
		return DigestUtils.sha1Hex(builder.toString());
	}

	
	public static String getJsonequipmentData(String JsonString, String nodedata) {
		
		//String jsonString = "{\"id\":1, \"name\":\"lzj\", \"cars\":[\"audi\", \"baoma\", \"benci\"]}"; 
		JsonParser parser = new JsonParser(); 
		JsonElement jsonNode = parser.parse(JsonString);
		String returnValue=null;
		if (jsonNode.isJsonObject()) { 
			JsonObject jsonObject = jsonNode.getAsJsonObject();    
		    JsonObject jsonOjectresultData = jsonObject.getAsJsonObject("resultData"); 
		    JsonObject jsonOjectfaData= jsonOjectresultData.getAsJsonObject("faData");
		    JsonObject jsonOjectlugdownData= jsonOjectresultData.getAsJsonObject("lugdownData"); 
		    JsonObject jsonOjectsdsData= jsonOjectresultData.getAsJsonObject("sdsData");
		    JsonObject jsonOjectvmassData= jsonOjectresultData.getAsJsonObject("vmassData");
		    JsonObject jsonOjectasmData= jsonOjectresultData.getAsJsonObject("asmData");
		    
		    JsonObject newjsonObject=new JsonObject();
		    newjsonObject.add("faData", jsonOjectfaData);
		    newjsonObject.add("lugdownData", jsonOjectlugdownData);
		    newjsonObject.add("sdsData", jsonOjectsdsData);
		    newjsonObject.add("vmassData", jsonOjectvmassData);
		    newjsonObject.add("asmData", jsonOjectasmData);
		    returnValue=newjsonObject.toString();
		 System.out.println("resultData: "+returnValue);
					}
		return returnValue;
	}
	
	public static String getJsonValue(String JsonString, String nodedata) {
		
		//String jsonString = "{\"id\":1, \"name\":\"lzj\", \"cars\":[\"audi\", \"baoma\", \"benci\"]}"; 
		JsonParser parser = new JsonParser(); 
		JsonElement jsonNode = parser.parse(JsonString);
		String returnValue=null;
		if (jsonNode.isJsonObject()) { 
			JsonObject jsonObject = jsonNode.getAsJsonObject();    
		    JsonObject jsonOjectenvData = jsonObject.getAsJsonObject(nodedata); 
		    returnValue=jsonOjectenvData.toString();
		 
					}
		return returnValue;
	}
	
	public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
	

}
