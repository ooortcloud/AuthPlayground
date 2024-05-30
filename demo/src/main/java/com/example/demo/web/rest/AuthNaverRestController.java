package com.example.demo.web.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.demo.domain.naver.NaverOAuthToken;
import com.example.demo.domain.naver.NaverProfileResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/auth/naver/rest")
@Log4j2
public class AuthNaverRestController {

	@Value("${naver.client.id}")
	private String clientId;
	
	@Value("${naver.client.secret}")
	private String clientSecret;
	
	@Value("${naver.redirect.uri}")
	private String redirectUri;
	
	private String TOKEN_REQUEST_URL = "https://nid.naver.com/oauth2.0/token";
	private String PROFILE_REQUEST_URL = "https://openapi.naver.com/v1/nid/me";
	
	@GetMapping("/callback")
	public void naverCallback(@RequestParam String code, @RequestParam String state, Model model) throws JsonProcessingException {
		
		log.info(code);
		log.info(state);

		
		// Parameter로 전달할 속성들 추가
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type","authorization_code");
	    params.add("client_id", clientId);
	    params.add("client_secret", clientSecret);
	    params.add("code", code);
	    params.add("state", state);

	    /// get access token
		// Http 메시지 생성
	    HttpEntity<MultiValueMap<String, String>> naverTokenRequest = makeTokenRequest(params);

		 // TOKEN_REQUEST_URL로 Http 요청 전송
	    RestTemplate rt = new RestTemplate();
	    ResponseEntity<String> tokenResponse = rt.exchange(
	            TOKEN_REQUEST_URL,
	            HttpMethod.POST,
	            naverTokenRequest,
	            String.class
	    );

	    // ObjectMapper를 통해 NaverOAuthToken 객체로 매핑
		ObjectMapper objectMapper = new ObjectMapper();
		NaverOAuthToken naverToken = objectMapper.readValue(tokenResponse.getBody(), NaverOAuthToken.class);
	    log.info(naverToken);


	    /// get profile
	    HttpEntity<MultiValueMap<String, String>> naverProfileRequest = makeProfileRequest(naverToken);

	    ResponseEntity<String> profileResponse = rt.exchange(
		    PROFILE_REQUEST_URL,
		    HttpMethod.POST,
		    naverProfileRequest,
		    String.class
	    );

	    NaverProfileResponse naverProfileResponse = objectMapper.readValue(profileResponse.getBody(), NaverProfileResponse.class);
	    log.info(naverProfileResponse.getResponse());
	    
	    model.addAttribute("naverProfile", naverProfileResponse.getResponse());
	}

	private HttpEntity<MultiValueMap<String, String>> makeTokenRequest(MultiValueMap<String, String> params) {
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
	    HttpEntity<MultiValueMap<String, String>> naverTokenRequest = new HttpEntity<>(params, headers);
	    return naverTokenRequest;
	}
	
	private HttpEntity<MultiValueMap<String, String>> makeProfileRequest(NaverOAuthToken  naverOAuthToken) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Bearer "+ naverOAuthToken.getAccess_token());
	    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
	    HttpEntity<MultiValueMap<String, String>> naverProfileRequest = new HttpEntity<>(headers);
	    return naverProfileRequest;
    }
}
