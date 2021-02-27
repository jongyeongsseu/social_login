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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class NaverAPI {

	public String getAccessToken(String authorizeCode, String stateToken) {
		String accessToken = "";
		String refreshToken = "";
		String reqURL = "https://nid.naver.com/oauth2.0/token";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=vEUuE2xME49hoGh40XXc");
			sb.append("&client_secret=nopoJ1sC5z");
			sb.append("&redirect_uri=http://localhost:8090/naver/callback");
			sb.append("&code=" + authorizeCode);
			sb.append("&state=" + stateToken);
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
			System.out.println("responseBody: " + result);

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			accessToken = element.getAsJsonObject().get("access_token").getAsString();
			refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

			System.out.println("access_token: " + accessToken);
			System.out.println("refresh_token: " + refreshToken);

			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return accessToken;
	}

	public HashMap<String, Object> getUserInfo(String accessToken) {
		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		String apiURL = "https://openapi.naver.com/v1/nid/me";
		
		try {
			URL url = new URL(apiURL);
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
	        
	        JsonObject response = element.getAsJsonObject().get("response").getAsJsonObject();
	        
	        String name = response.getAsJsonObject().get("name").getAsString();
	        String email = response.getAsJsonObject().get("email").getAsString();
	        
	        userInfo.put("name", name);
	        userInfo.put("email", email);
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return userInfo;
	}
}