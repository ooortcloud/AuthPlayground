package com.example.demo.web.rest;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.google.GoogleAuthenticatorKey;
import com.example.demo.domain.google.GoogleUserOtpCheck;
import com.example.demo.domain.google.GoogleUserRequest;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/auth/google/rest")
@Log4j2
public class AuthGoogleRestController {

	// generateKey function의 매개변수에 있던 값인데, host 명은 어차피 우리 server 상에서 static하지 않은가 싶어서 metadata처리함
	@Value("${server.host.name}")
	private String hostName;
	
	@ResponseBody
	@PostMapping("/generateKey")
	public GoogleAuthenticatorKey generateKey(@RequestBody GoogleUserRequest googleUserRequest) { 
		
							// [secretSize + numOfScratchCodes * scratchCodeSize]
		byte[] buffer = new byte[10 + 5 * 5]; 
		
		// Filling the buffer with random numbers.
		new Random().nextBytes(buffer); 
		
		// Getting the key and converting it to Base32
		Base32 codec = new Base32(); 
									// (buffer, secretSize)
		byte[] secretKey = Arrays.copyOf(buffer, 10); 
		byte[] bEncodedKey = codec.encode(secretKey); 
		
		// create key
		String encodedKey = new String(bEncodedKey); 

		GoogleAuthenticatorKey key = new GoogleAuthenticatorKey();
		key.setEncodedKey(encodedKey);
		key.setUserName(googleUserRequest.getUserName());
		key.setHostName(hostName);
		/*
		 * 발급된 encodedKey를 가지고 본인의 Google Authenticator app에 추가한다.
		 * Google Authenticator app에서 QR을 통해서도 등록이 가능하다.
		 * QR code 생성 로직은 generateKey.html에서 확인
		 */
		return key; 
	}
	
	@ResponseBody
	@PostMapping("/checkKey")
	public String checkKey(@RequestBody GoogleUserOtpCheck googleUserOtpCheck) throws InvalidKeyException, NoSuchAlgorithmException {
		
		long userCode = Integer.parseInt(googleUserOtpCheck.getUserCode());
		String encodedKey = googleUserOtpCheck.getEncodedKey();
		
		long I = new Date().getTime();
		long II = I / 30000;
		
		boolean result = false;
		
		Base32 codec = new Base32();
		byte[] decodedKey = codec.decode(encodedKey);
		
		// Window is used to check codes generated in the near past.
        // You can use this value to tune how far you're willing to go.
        int window = 3;
        for (int i = -window; i <= window; ++i) {
            long hash = verify_code(decodedKey, II + i);
 
            log.info("hash = " + hash);
			log.info("userCode = " + userCode);
			
            if (hash == userCode) {
                return String.valueOf(true);
            }
        }
		
        return String.valueOf(false);
		/*
		long otpnum = Integer.parseInt(googleUserOtpCheck.getUserCode());
		long wave = new Date().getTime();
		boolean result = false;
		try {
			Base32 codec = new Base32();
			byte[] decodedKey = codec.decode(googleUserOtpCheck.getEncodedKey());
			
			// Window is used to check codes generated in the near past.
	        // You can use this value to tune how far you're willing to go.
			int window = 3;
			for (int i = -window; i <= window; ++i) {
				long hash = verify_code(decodedKey, wave + i);
				
				log.info("hash = " + hash);
				log.info("otpnum = " + otpnum);
				
				if (hash == otpnum) result = true;
			}
		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		log.info(String.valueOf(result));
				
		return String.valueOf(result);
		*/
	}
	
	private static int verify_code(byte[] key, long t)
            throws NoSuchAlgorithmException, InvalidKeyException {
		
        byte[] data = new byte[8];
        long value = t;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
 
        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);
 
        int offset = hash[20 - 1] & 0xF;
 
        // We're using a long because Java hasn't got unsigned int.
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            // We are dealing with signed bytes:
            // we just keep the first byte.
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
 
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;
 
        return (int) truncatedHash;
    }
}
