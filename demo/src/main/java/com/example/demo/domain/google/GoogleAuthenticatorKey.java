package com.example.demo.domain.google;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleAuthenticatorKey {

	private String encodedKey;
	private String userName;
	private String hostName;
	// private String url;
}
