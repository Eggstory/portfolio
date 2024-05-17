package com.spring.portfolio.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;



class jasyptConfigTest {

	@Value("${jasypt.encryptor.password}")
	private String key;

	@Test
	void jasypt() {
		String secret = "1234";

		System.out.printf("%s -> %s", secret, jasyptEncoding(secret));
	}

	public String jasyptEncoding(String value) {
		String key = "key";
		StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
		pbeEnc.setAlgorithm("PBEWithMD5AndDES");
		pbeEnc.setPassword(key);
		return pbeEnc.encrypt(value);
	}

}