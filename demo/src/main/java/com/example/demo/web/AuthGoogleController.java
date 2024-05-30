package com.example.demo.web;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

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

import com.example.demo.domain.google.GoogleJwtPayLoad;
import com.example.demo.domain.google.GoogleResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/auth/google")
@Log4j2
public class AuthGoogleController {

	@Value("${google.client.id}")
	private String clientId;
	
	@Value("${google.client.secret}")
	private String clientSecret;
	
	@Value("${google.redirect.uri}")
	private String redirectUri;
	
	@Value("${google.state}")
	private String state;
	
	@Value("${google.scope}")
	private String scope;
	
	private String TOKEN_REQUEST_URL = "https://oauth2.googleapis.com/token";
	private String PROFILE_REQUEST_URL = "https://www.googleapis.com/auth/userinfo.profile";
	
	@GetMapping("/googleLogin")
    public void loginUrlGoogle(Model model){
		
        log.info("google");
        
        model.addAttribute("client_id", clientId);
        model.addAttribute("redirect_uri", redirectUri);
        model.addAttribute("response_type", "code");
        model.addAttribute("scope", scope); 
        model.addAttribute("access_type", "offline");
        model.addAttribute("prompt", "consent");
        // model.addAttribute("include_granted_scopes", "true");
        model.addAttribute("state", state);
    }
	


	@GetMapping("/callback")
	public void callback(@RequestParam String code, Model model) throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException {
		
		log.info("callback");
		log.info(code);
		
		// Parameter로 전달할 속성들 추가
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("client_id", clientId);
	    params.add("client_secret", clientSecret);
	    params.add("code", code);
	    params.add("grant_type","authorization_code");
	    params.add("redirect_uri", redirectUri);
	    
	    /// get access token
	    // Http 메시지 생성
	    HttpEntity<MultiValueMap<String, String>> googleTokenRequest = makeTokenRequest(params);
	    
	    // TOKEN_REQUEST_URL로 Http 요청 전송
	    RestTemplate rt = new RestTemplate();
	    ResponseEntity<String> tokenResponse = rt.exchange(
	            TOKEN_REQUEST_URL,
	            HttpMethod.POST,
	            googleTokenRequest,
	            String.class
	    );
	    
	    // ObjectMapper를 통해 GoogleResponse 객체로 매핑
 		ObjectMapper objectMapper = new ObjectMapper();
 		GoogleResponse googleToken = objectMapper.readValue(tokenResponse.getBody(), GoogleResponse.class);
 	    log.info(googleToken);
 	    
 	    String[] chunks = googleToken.getId_token().split("\\.");
 	    
 	    Base64.Decoder decoder = Base64.getUrlDecoder();
 	    String payloadStr = new String(decoder.decode(chunks[1]), "utf-8");
 	    log.info(payloadStr);
 	    
		GoogleJwtPayLoad payload = objectMapper.readValue(payloadStr, GoogleJwtPayLoad.class);
		
		log.info(payload);
		
		model.addAttribute("payload", payload);
	}
	
	private HttpEntity<MultiValueMap<String, String>> makeTokenRequest(MultiValueMap<String, String> params) {
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
	    HttpEntity<MultiValueMap<String, String>> googleTokenRequest = new HttpEntity<>(params, headers);
	    return googleTokenRequest;
	}

}
