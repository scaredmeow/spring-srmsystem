package com.code.srmsystem.service;

public interface EncryptionAndDecryptionService {
    public String encrypt(String strToEncrypt, String secret);

    public String decrypt(String strToDecrypt, String secret);
}
