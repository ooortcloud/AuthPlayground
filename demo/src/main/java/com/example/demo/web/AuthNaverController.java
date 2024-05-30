package com.example.demo.web;

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
@RequestMapping("/auth/naver")
@Log4j2
public class AuthNaverController {

	@Value("${naver.client.id}")
	private String clientId;
	
	@Value("${naver.redirect.uri}")
	private String redirectUri;
	
	@Value("${naver.state}")
	private String state;
	
	@GetMapping({"/naverLoginSample", "/naverLogin"})
	public void naverLoginSample(Model model) {
		
		log.info("naver");
		
		model.addAttribute("client_id", clientId);
		model.addAttribute("redirect_uri", redirectUri);
		model.addAttribute("state", "abcd");
	}
	

	
	
	
}
