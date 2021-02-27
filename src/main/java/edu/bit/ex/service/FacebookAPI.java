package edu.bit.ex.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Service
public class FacebookAPI {

	public String getAccessToken(String authorizeCode) {
		String accessToken = "";
		String reqURL = "https://graph.facebook.com/v2.8/oauth/access_token";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");
			conn.setDoOutput(true);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("client_id=1112431782514901");
			sb.append("&redirect_uri=http://localhost:8090/facebook/callback");
			sb.append("&client_secret=e2cf1220a79e55e73b8ec2d31190beee");
			sb.append("&code=" + authorizeCode);
			
			bw.write(sb.toString());
			bw.flush();

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode: " + responseCode);

			BufferedReader br = new BufferedReader(
					new InputStreamReader(responseCode == 200 ? conn.getInputStream() : conn.getErrorStream()));
			
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			
			System.out.println("result: " + result);

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			accessToken = element.getAsJsonObject().get("access_token").getAsString();

			System.out.println("accessToken: " + accessToken);

			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return accessToken;
	}

	public HashMap<String, Object> getUserInfo(String accessToken) {
		HashMap<String, Object> userInfo = new HashMap<String, Object>();
	    String reqURL = "https://graph.facebook.com/me?access_token=" + accessToken + "&fields=id,name,email,picture";
	    
	    try {
	        URL url = new URL(reqURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        
	        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
	        
	        int responseCode = conn.getResponseCode();
	        System.out.println("responseCode : " + responseCode);
	        
	        BufferedReader br = new BufferedReader(
					new InputStreamReader(responseCode == 200 ? conn.getInputStream() : conn.getErrorStream()));
	        
	        String line = "";
	        String result = "";
	        
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }	
	        System.out.println("response body : " + result);
	        
	        JsonParser parser = new JsonParser();
	        JsonElement element = parser.parse(result);
	        
	        String id = element.getAsJsonObject().get("id").getAsString();
	        String name = element.getAsJsonObject().get("name").getAsString();
	        String email = element.getAsJsonObject().get("email").getAsString();
	        
	        userInfo.put("id", id);
	        userInfo.put("name", name);
	        userInfo.put("email", email);
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return userInfo;
	}
}
