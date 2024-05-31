package com.example.demo.domain.google;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoogleUserOtpCheck {

	private String userCode;
	private String encodedKey;
}
