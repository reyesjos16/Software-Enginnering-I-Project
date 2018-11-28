package com.chat.secure.reilly.securechat;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import javax.crypto.SecretKey;
import javax.crypto.spec.*;
import javax.crypto.Cipher;
import java.util.*;

public class Encryption
{
    private FriendlyMessage encryptMessage(String x)
    {

    }

    public FriendlyMessage encrypt(String x)
    {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        SecretKey secretKey = SecretKeySpec(key, “AES”);
        byte[] iv = new byte[12]; //NEVER REUSE THIS IV WITH SAME KEY
        secureRandom.nextBytes(iv);
        final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv); //128 bit auth tag length
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
        if (associatedData != null) {
            cipher.updateAAD(associatedData);
        }
        byte[] cipherText = cipher.doFinal(plainText);
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + iv.length + cipherText.length);
        byteBuffer.putInt(iv.length);
        byteBuffer.put(iv);
        byteBuffer.put(cipherText);
        byte[] cipherMessage = byteBuffer.array();
        Arrays.fill(key,(byte) 0); //overwrite the content of key with zeros
    }
}
