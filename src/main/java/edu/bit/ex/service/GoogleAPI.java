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
public class GoogleAPI {

	public String getAccessToken(String authorizeCode) {
		String accessToken = "";
		String reqURL = "https://www.googleapis.com/oauth2/v4/token";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("code=" + authorizeCode);
			sb.append("&client_id=817134394903-4f4n4oeq88dg83vgl3t8iso7dei9o1dm.apps.googleusercontent.com");
			sb.append("&client_secret=m1iMy1Iju3PS0UulWvE2iqG1");
			sb.append("&redirect_uri=http://localhost:8090/google/callback");
			sb.append("&grant_type=authorization_code");
			
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
	    String reqURL = "https://www.googleapis.com/userinfo/v2/me";
	    
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
	        
	        String name = element.getAsJsonObject().get("name").getAsString();
	        String email = element.getAsJsonObject().get("email").getAsString();
	        String picture = element.getAsJsonObject().get("picture").getAsString();
	        
	        userInfo.put("name", name);
	        userInfo.put("email", email);
	        userInfo.put("picture", picture);
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return userInfo;
	}
}
