package com.example.pj2be.config.security;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class KeyManager {
    private SecretKey secretKey;

    @PostConstruct
    public void init() throws Exception{
        this.secretKey = AESEncryption.generateKey();
    }

    public SecretKey getSecretKey(){
        return secretKey;
    }
}
