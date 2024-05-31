package com.example.demo.domain.google;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleUserOtpCheck {

	private String userCode;
	private String encodedKey;
}
