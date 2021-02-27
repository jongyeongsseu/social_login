package edu.bit.ex.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.bit.ex.service.FacebookAPI;
import edu.bit.ex.service.GoogleAPI;
import edu.bit.ex.service.KakaoAPI;
import edu.bit.ex.service.NaverAPI;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class RestLoginController {

	@Autowired
	private KakaoAPI kakao;

	@Autowired
	private NaverAPI naver;
	
	@Autowired
	private GoogleAPI google;
	
	@Autowired
	private FacebookAPI facebook;
	
	/*****************
	 *   Login Page
	 *****************/

	// Login Page
	@GetMapping("/index")
	public ModelAndView index(ModelAndView mav) throws Exception {
		mav.setViewName("index");

		return mav;
	}
	
	/*****************
	 * Callback Page
	 *****************/

	// Kakao Callback
	@GetMapping("/kakao/callback")
	public ModelAndView kakaoCallback(@RequestParam("code") String code, HttpSession session, ModelAndView mav) throws Exception {
		String accessToken = kakao.getAccessToken(code);
		HashMap<String, Object> userInfo = kakao.getUserInfo(accessToken);

		if (userInfo.get("email") != null) {
			session.setAttribute("account_email", userInfo.get("email"));
			session.setAttribute("nickname", userInfo.get("nickname"));
			session.setAttribute("accessToken", accessToken);
		}

		mav.setViewName("kakao_callback");

		return mav;
	}

	// Naver Callback
	@GetMapping("/naver/callback")
	public ModelAndView naverCallBack(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session, ModelAndView mav) throws Exception {
		String accessToken = naver.getAccessToken(code, state);
		HashMap<String, Object> userInfo = naver.getUserInfo(accessToken);
		
		if (userInfo.get("email") != null) {
			session.setAttribute("email", userInfo.get("email"));
			session.setAttribute("name", userInfo.get("name"));
			session.setAttribute("accessToken", accessToken);
		}
		
		mav.setViewName("naver_callback");

		return mav;
	}
	
	// Google Callback
	@GetMapping("/google/callback")
	public ModelAndView googleCallback(@RequestParam("code") String code, HttpSession session, ModelAndView mav) throws Exception {
		String accessToken = google.getAccessToken(code);
		HashMap<String, Object> userInfo = google.getUserInfo(accessToken);
		
		if (userInfo.get("email") != null) {
			session.setAttribute("email", userInfo.get("email"));
			session.setAttribute("name", userInfo.get("name"));
			session.setAttribute("picture", userInfo.get("picture"));
			session.setAttribute("accessToken", accessToken);
		}
		
		mav.setViewName("google_callback");
		
		return mav;
	}
	
	// Facebook Callback
	@GetMapping("/facebook/callback")
	public ModelAndView facebookCallback(@RequestParam("code") String code, HttpSession session, ModelAndView mav) throws Exception {
		String accessToken = facebook.getAccessToken(code);
		HashMap<String, Object> userInfo = facebook.getUserInfo(accessToken);
		
		if (userInfo.get("email") != null) {
			session.setAttribute("id", userInfo.get("id"));
			session.setAttribute("name", userInfo.get("name"));
			session.setAttribute("email", userInfo.get("email"));
			session.setAttribute("accessToken", accessToken);
		}
		
		mav.setViewName("facebook_callback");
		
		return mav;
	}
}
